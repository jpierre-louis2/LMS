//Author: Jameson Pierre-Louis
//This class implements the Librarian Menu
package com.ss.apr.jb.BLL;
import java.util.ArrayList;
import java.util.Scanner;
import com.ss.apr.jb.DAL.BranchDAL;
import com.ss.apr.jb.DAL.BookDAL;
import com.ss.apr.jb.DAL.BookCopiesDAL;
import com.ss.apr.jb.tables.Branch;
import com.ss.apr.jb.tables.Book;


public class LibrarianSystem {
	
	private int branch;
	BranchDAL brDAL = new BranchDAL();
	BookDAL boDAL = new BookDAL();
	BookCopiesDAL bcDAL = new BookCopiesDAL();
	InputFunctions in = new InputFunctions();
	
	//Lib 1 Menu
	public void lib1() {
		int input = 0;
		while(input != 2) {
			System.out.println("\nLibrarian Menu: ");
			System.out.println("1. Enter Branch you manage");
			System.out.println("2. Quit to previous");
			input = in.getInput(2, 1);
			if(input == 1) {
				lib2();
			}
		}
	}
	//List branches and prompt user to select Branch
	public void lib2() {
		int input = 0;
		try {
			ArrayList<Branch> branches = brDAL.getBranches();
			int max = branches.size() + 1;
			if (branches.size() == 0) {
				System.out.println("\nThere are no Library Branches in System!");
				return;
			}
			while(input != max) {
				brDAL.printBranches();
				System.out.println(max + ". Quit to previous");
				input = in.getInput(max, 1);
				if (input != max) {
					this.branch = branches.get(input - 1).getBranchId();
					lib3();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//Prompt user to update Branch details or Order new copies of a book
	public void lib3() {
		int input = 0;
		while(input != 3) {
		System.out.println("\n1. Update the details of the library");
		System.out.println("2. Add copies of the book to Branch");
		System.out.println("3. Quit to previous");
		input = in.getInput(3, 1);
		
			if(input == 1) {
				updateBranch();
			}
			else if (input == 2) {
				orderBooks();
			}
		}	
	}
	
	//Allows user to update branch details (Name & Address)
	public void updateBranch() {
		Scanner scan = new Scanner(System.in);
		try {
			Branch branch1 = brDAL.getBranch(this.branch);
			System.out.println("\nYou have chosen to update the Branch with BranchId:(" + branch1.getBranchId() + ") and Branch Name: " + branch1.getBranchName());
			System.out.println("Type 'Quit' at anytime to exit\n");
			System.out.print("Please Enter Branch Name: ");
			String name = scan.nextLine();
			if(in.validateQuit(name)) {
				return;
			}
			System.out.print("Please Enter Branch Address: ");
			String address = scan.nextLine();
			if(in.validateQuit(address)) {
				return;
			}
			brDAL.updateBranch(branch, name, address);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//List all available books, prompt for which book, prompt for existing copies and prompt for how many new copies
	public void orderBooks() {
		try {
			boDAL.printBooks();
			ArrayList<Book> books = boDAL.getBooks();
			int max = books.size();
			
			if (books.size() == 0) {
				System.out.println("\nThere are no Books in System!");
				return;
			}
			
			System.out.println("Which book would you like to order?");
			int input = in.getInput(max, 1);
			Book target = books.get(input - 1);
			
			System.out.println("How many would you like to order?(100 Max): ");
			int amount = in.getInput(100, 1);
			String name = brDAL.getBranch(branch).getBranchName();
			
			System.out.println("Ordering " + amount + " Copies of " + target.getTitle() + " for " + name);
			bcDAL.orderBook(target.getId(), branch, amount);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
