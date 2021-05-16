//Author: Jameson Pierre-Louis
//This class implements the Borrower Menu
package com.ss.apr.jb.BLL;
import com.ss.apr.jb.DAL.*;
import com.ss.apr.jb.tables.Branch;
import com.ss.apr.jb.tables.Book;
import com.ss.apr.jb.tables.Borrower;
import java.util.ArrayList;

public class BorrowerSystem {
	
	//UtilityFunctions u = new UtilityFunctions();
	InputFunctions in = new InputFunctions();
	BranchDAL brDAL = new BranchDAL();
	BorrowerDAL borDAL = new BorrowerDAL();
	BookCopiesDAL bcDAL = new BookCopiesDAL();
	BookLoansDAL blDAL = new BookLoansDAL();
	private int cardNumber;
	private int branch;

	
	//Checks to see if the user's card number is correct
	private boolean validateCard(int tempCard) {
		ArrayList<Borrower> borrowers = null;
		try {
			borrowers = borDAL.getBorrowers();
		}catch(Exception e) {
			
		}
		for(Borrower borrower : borrowers) {
			if (tempCard == borrower.getCardNumber()) {
				return true;
			}
		}
		return false;
	}
	//Continuously prompts user for Card Number
	public void setCardNumber() {
		boolean result = false;
		
		while (result == false) {
			System.out.print("\nTo continue please enter your Card Number (0 to Return to Main Menu): ");
			int tempCard = in.getInput();
			if(tempCard == 0) {
				System.out.println("\n");
				return;
			}
			result = validateCard(tempCard);
			if (result == true) {
				this.cardNumber = tempCard;
				borr1();
			}
			else
				System.out.println("Sorry, user does not exist. Please try again!");
		}
	}
	//borr1 Menu
	public void borr1() {
		int input = 0;
		
		while(input != 3) {
			System.out.println("\nBorrower Menu: ");
			System.out.println("1. Check out a book");
			System.out.println("2. Return a book");
			System.out.println("3. Quit to previous");
			input = in.getInput(3, 1);
			if (input != 3) {
				borr2(input);
			}
		}
	}
	//borr2 Menu which prompts user for branch
	public void borr2(int option) {
		int input = 0;
		try {
			ArrayList<Branch> branches = brDAL.getBranches();
			int max = branches.size() + 1; 
			
			if (branches.size() == 0) {
				System.out.println("\nThere are no Library Branches in System!");
				return;
			}
			brDAL.printBranches();
			System.out.println(max + ". Quit to previous");
			input = in.getInput(max, 1);
			if (input != max) {
				this.branch = branches.get(input - 1).getBranchId();
				if (option == 1 && input != max) {
					borrowBook();
				}
				else if (option == 2 && input != max) {
					returnBook();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Lists copies of books, Makes user choose a book, and prints out due date, which is 1 week from today
	public void borrowBook() {
		int input = 0;
		try {
		ArrayList<Book> books = bcDAL.getAvailableBooks(branch);
		int max = books.size() + 1;
		bcDAL.printAvailableBooks(branch);
		
		if (max == 1) {
			System.out.println("None");
			return;
		}
		System.out.println(max + ". Quit to previous");
		input = in.getInput(max, 1);
			if(input != max) {
				Book book = books.get(input-1);
				blDAL.loanBook(book.getId(), branch, cardNumber);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}
	}
	
	//Lists copies of books, Makes user choose a book, implements a return function
	public void returnBook() {
		int input = 0;
		try {
			int max = blDAL.getBorrowedBooks(branch, cardNumber).size() + 1;
			if (max != 1) {
				ArrayList<Book> books = blDAL.getBorrowedBooks(branch, cardNumber);
				blDAL.printLoanedBooks(branch, cardNumber);
				System.out.println(max + ". Quit to previous");
				input = in.getInput(max, 1);
				if(input != max) {
						Book book = books.get(input-1);
						//Delete from Loan table
						try {
						blDAL.returnBook(book.getId(), branch, cardNumber);
						}catch(Exception e) {}
						//Print out Available Books
						blDAL.printAvailableBooks(branch);
				}
			}
			else
				System.out.println("No books currently checked out!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
