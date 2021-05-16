//Author: Jameson Pierre-Louis
//This class implements the Administrator Menu
package com.ss.apr.jb.BLL;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.ss.apr.jb.DAL.*;
import com.ss.apr.jb.tables.*;

public class AdministratorSystem {
	AuthorDAL autDAL = new AuthorDAL();
	BookDAL boDAL = new BookDAL();
	BookLoansDAL blDAL = new BookLoansDAL();
	BorrowerDAL borDAL = new BorrowerDAL();
	BranchDAL brDAL = new BranchDAL();
	PublisherDAL pubDAL = new PublisherDAL();
	InputFunctions in = new InputFunctions();

	//Adds a new author
	public void addAuthor() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type 'Quit' to exit");
		System.out.println("What is the name of the new Author?");
		String name = scan.nextLine();
		
		try {
			System.out.println(autDAL.addAuthor(name));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//Adds a new book
	public void addBook() {
		Scanner scan = new Scanner(System.in);
		try {
			ArrayList<Publisher> p = pubDAL.getPublishers();
			ArrayList<Author> a = autDAL.getAuthors();
			
			//Accounts for no Authors or Publishers
			if (p.size() == 0) {
				System.out.println("\nError, No Available Publishers!");
				return;
			}
			else if (a.size() == 0) {
				System.out.println("\nError, No Available Authors!");
				return;
			}
			System.out.println("Type 'Quit' to exit");
			System.out.println("What is the Title of the new Book?");
			String title = scan.nextLine();
			if(in.validateQuit(title)) {
				return;
			}
		
			System.out.print("\nWho is the publisher?");
			pubDAL.printPublishers();
			int input = in.getInput(p.size(), 1);
			int pId = p.get(input - 1).getId();
			System.out.print("\nWho is the author");
			autDAL.printAuthors();
			input = in.getInput(a.size(), 1);
			int aId = a.get(input - 1).getId();
			
			System.out.println("\nAdding " + title + " by " + autDAL.getAuthorName(aId) + " from " + pubDAL.getPublisherName(pId) + " Publishing");
			
		
			System.out.println(boDAL.addBook(title, aId, pId));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Adds a new borrower
	public void addBorrower() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type 'Quit' at anytime to exit");
		System.out.print("What is the name of the new Borrower? ");
		String name = scan.nextLine();
		if(in.validateQuit(name)) {
			return;
		}
		System.out.print("What is their Address? ");
		String address = scan.nextLine();
		if(in.validateQuit(address)) {
			return;
		}
		System.out.print("What is their Phone Number? ");
		String phone = scan.nextLine();
		if(in.validateQuit(phone)) {
			return;
		}
		System.out.println("Adding " + name + " from " + address + " Phone Number: " + phone +"\n");
		try {
			System.out.println(borDAL.addBorrower(name, address, phone));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Adds a new branch
	public void addBranch() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type 'Quit' at anytime to exit");
		System.out.print("What is the name of the new Branch? ");
		String name = scan.nextLine();
		if(in.validateQuit(name)) {
			return;
		}
		System.out.print("What is the Address? ");
		String address = scan.nextLine();
		if(in.validateQuit(address)) {
			return;
		}
		System.out.println("Adding " + name + " Branch, located in " + address);
		try {
			System.out.println(brDAL.addBranch(name, address));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Adds a new publisher
	public void addPublisher() {
		System.out.println("Type 'Quit' at anytime to exit");
		Scanner scan = new Scanner(System.in);
		System.out.print("What is the name of the new Publisher? ");
		String name = scan.nextLine();
		if(in.validateQuit(name)) {
			return;
		}
		System.out.print("What is their Address? ");
		String address = scan.nextLine();
		if(in.validateQuit(address)) {
			return;
		}
		System.out.print("What is their Phone Number? ");
		String phone = scan.nextLine();
		if(in.validateQuit(phone)) {
			return;
		}
		System.out.println("Adding " + name + " Publishing, located at " + address + " Phone Number: " + phone +"\n");
		try {
			System.out.println(pubDAL.addPublisher(name, address, phone));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Prompts for information and updates Author
	public void updateAuthor() {
		try {
			ArrayList<Author> a = autDAL.getAuthors();
		
			if (a.size() == 0) {
				System.out.println("\nError, No Available Authors!");
				return;
			}
			
			System.out.print("\nWhich author would you like to update?");
			autDAL.printAuthors();
			int input = in.getInput(a.size(), 1);
			int aId = a.get(input - 1).getId();
			
			Scanner scan = new Scanner(System.in);
			System.out.println("\nWhat is the new name of the Author?");
			String name = scan.nextLine();
			
			System.out.println("\n*Updating*\n");
			System.out.println(autDAL.updateAuthor(aId, name));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Prompts for information and updates book
	public void updateBook() {
		try {
			ArrayList<Publisher> p = pubDAL.getPublishers();
			ArrayList<Author> a = autDAL.getAuthors();
			ArrayList<Book> b = boDAL.getBooks();
			
			//Accounts for no Authors or Publishers
			if (p.size() == 0) {
				System.out.println("\nError, No Available Publishers!");
				return;
			}
			else if (a.size() == 0) {
				System.out.println("\\nError, No Available Authors!");
				return;
			}
			else if (b.size() == 0) {
				System.out.println("\\nError, No Available Books!");
				return;
			}
			
			System.out.println("What Book would you like to update?");
			boDAL.printBooks();
			int input = in.getInput(b.size(), 1);
			int bId = b.get(input - 1).getId();
			Book book = boDAL.getBook(bId);
			System.out.print("\nWho is the publisher?");
			pubDAL.printPublishers();
			input = in.getInput(p.size(), 1);
			int pId = p.get(input - 1).getId();
			System.out.print("\nWho is the author");
			autDAL.printAuthors();
			input = in.getInput(a.size(), 1);
			int aId = a.get(input - 1).getId();
			
			System.out.println("\n*Updating*\n" + book.getTitle() + " by " + autDAL.getAuthorName(aId) + " from " + pubDAL.getPublisherName(pId) + " Publishing\n");
			
			System.out.println(boDAL.updateBook(bId, book.getTitle(), aId, pId));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Prompts for information and updates Borrower
	public void updateBorrower() {
		Scanner scan = new Scanner(System.in);
		try {
			ArrayList<Borrower> b = borDAL.getBorrowers();
			
			if (b.size() == 0) {
				System.out.println("\nError, No Available Borrowers!");
				return;
			}
			
			System.out.print("\nWhich borrower would you like to update? ");
			borDAL.printBorrowers();
			int input = in.getInput(b.size(), 1);
			int bId = b.get(input - 1).getCardNumber();
			
			System.out.print("What is the new name of the Borrower? ");
			String name = scan.nextLine();
			System.out.print("What is their Address? ");
			String address = scan.nextLine();
			System.out.print("What is their Phone Number? ");
			String phone = scan.nextLine();
			
			System.out.println("\n*Updating*\n");
			
			System.out.println(borDAL.updateBorrower(bId, name, address, phone));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//Prompts for information and updates Branch
	public void updateBranch(){
		Scanner scan = new Scanner(System.in);
		try {
			ArrayList<Branch> b = brDAL.getBranches();
			
			if (b.size() == 0) {
				System.out.println("\nError, No Available Borrowers!");
				return;
			}
			
			System.out.println("What Branch would you like to update?");
			brDAL.printBranches();
			int input = in.getInput(b.size(), 1);
			int bId = b.get(input - 1).getBranchId();
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
			brDAL.updateBranch(bId, name, address);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Prompts for information and updates Publisher
	public void updatePublisher() {
		try {
			ArrayList<Publisher> p = pubDAL.getPublishers();
			
			if (p.size() == 0) {
				System.out.println("\nError, No Available Publishers!");
				return;
			}
			
			System.out.print("\nWhich publisher would you like to update? ");
			pubDAL.printPublishers();
			int input = in.getInput(p.size(), 1);
			int pId = p.get(input - 1).getId();
			
			Scanner scan = new Scanner(System.in);
			System.out.print("What is the new name of the Publisher? ");
			String name = scan.nextLine();
			System.out.print("What is their Address? ");
			String address = scan.nextLine();
			System.out.print("What is their Phone Number? ");
			String phone = scan.nextLine();
			
			System.out.println("\n*Updating*\n");
			
			System.out.println(pubDAL.updatePublisher(pId, name, address, phone));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	//Prompts for information and deletes author
	public void deleteAuthor() {
		try {
			ArrayList<Author> a = autDAL.getAuthors();
			
			if (a.size() == 0) {
				System.out.println("\nError, No Available Authors!");
				return;
			}
			
			System.out.println("What Author would you like to Delete?");
			//System.out.println("*Warning, this will Delete all Loans and copies currently belonging to users*");
			autDAL.printAuthors();
			int input = in.getInput(a.size(), 1);
			int aId = a.get(input - 1).getId();
			Author author = autDAL.getAuthor(aId);
			
			System.out.println("\nNow Deleting: " + author.getName());
			System.out.println(autDAL.deleteAuthor(aId));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Prompts for information and deletes book
	public void deleteBook() {
		try {
			ArrayList<Book> b = boDAL.getBooks();
			
			if (b.size() == 0) {
				System.out.println("\nError, No Available Books!");
				return;
			}
			
			System.out.println("What Book would you like to Delete?");
			System.out.println("*Warning, this will Delete all Loans and copies currently belonging to users*");
			boDAL.printBooks();
			int input = in.getInput(b.size(), 1);
			int bId = b.get(input - 1).getId();
			Book book = boDAL.getBook(bId);
			
			System.out.println("\nNow Deleting: " + book.getTitle());
			System.out.println(boDAL.deleteBook(bId));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Prompts for information and deletes borrower
	public void deleteBorrower() {
		try {
			ArrayList<Borrower> b = borDAL.getBorrowers();
			
			if (b.size() == 0) {
				System.out.println("\nError, No Available Borrowers!");
				return;
			}
			
			System.out.println("What Borrower would you like to Delete?");
			//System.out.println("*Warning, this will Delete all loans and copies currently belonging to users*");
			borDAL.printBorrowers();
			int input = in.getInput(b.size(), 1);
			int bId = b.get(input - 1).getCardNumber();
			Borrower borrower = borDAL.getBorrower(bId);
			
			System.out.println("\nNow Deleting: " + borrower.getName());
	
			System.out.println(borDAL.deleteBorrower(bId));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Prompts for information and deletes Branch
	public void deleteBranch(){
		try {
			ArrayList<Branch> b = brDAL.getBranches();
			
			if (b.size() == 0) {
				System.out.println("\nError, No Available Branches!");
				return;
			}
			
			System.out.println("What Branch would you like to Delete?");
			brDAL.printBranches();
			int input = in.getInput(b.size(), 1);
			int bId = b.get(input - 1).getBranchId();
			System.out.println("Now Deleting: " + brDAL.getBranch(bId).getBranchName() + "\n");
			System.out.println(brDAL.deleteBranch(bId));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Prompts for information and deletes publisher
	public void deletePublisher() {
		try {
			ArrayList<Publisher> p = pubDAL.getPublishers();
			
			if (p.size() == 0) {
				System.out.println("\nError, No Available Publishers!");
				return;
			}
			
			System.out.println("What Publisher would you like to Delete?");
			//System.out.println("*Warning, this will Delete all Loans and copies currently belonging to users*");
			pubDAL.printPublishers();
			int input = in.getInput(p.size(), 1);
			int pId = p.get(input - 1).getId();
			Publisher publisher = pubDAL.getPublisher(pId);
			
			System.out.println("\nNow Deleting: " + publisher.getName() + " Publishing");
			System.out.println(pubDAL.deletePublisher(pId));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Overrides the due date for a specific loan
	public void overrideDueDate(){
		Scanner scan = new Scanner(System.in);
		try {
			ArrayList<Borrower> borrowers = borDAL.getBorrowers();
			ArrayList<Branch> branches = brDAL.getBranches();
	
			int borrowerSize = borrowers.size();
			int branchSize = branches.size();
			
			//Accounts for no Authors or Publishers
			if (borrowerSize == 0) {
				System.out.println("\nError, No Available Borrowers!");
				return;
			}
			else if (branchSize == 0) {
				System.out.println("\\nError, No Available Branches!");
				return;
			}
	
			//Gets which User
			System.out.println("\n\nWhich user's Due Date would you like to override?");
			borDAL.printBorrowers();
			int input = in.getInput(borrowerSize, 1);
			Borrower borrower = borrowers.get(input - 1);
			
			//Gets which Library Branch
			System.out.println("Which Library Branch is this for?");
			brDAL.printBranches();
			input = in.getInput(branchSize, 1);
			Branch branch = branches.get(input - 1);
			
			ArrayList<Book> books = blDAL.getBorrowedBooks(branch.getBranchId(), borrower.getCardNumber());
			
			//Checks for if loan exists
			if(books.size() == 0) {
				System.out.println("User currently has no loans for this Library Branch!");
			}
			//Gets the book loan and changes due date details
			else {	
				System.out.println("For which book is this for?");
				blDAL.printLoanedBooks(branch.getBranchId(), borrower.getCardNumber());
				input = in.getInput(books.size(), 1);
				Book book = books.get(input - 1);
				
				BookLoans loan = blDAL.getBookLoan(book.getId(), branch.getBranchId(), borrower.getCardNumber());
				System.out.println("\n" + borrower.getName() + "'s " + branch.getBranchName() + " Loan Details:");
				System.out.println("Book: " + book.getTitle() + "\nCurrent Due Date: " + loan.getDateDue() + " / Checkout Date: " + loan.getDateOut());
				
				
				System.out.println("\nPlease enter a new Due Date");
				System.out.println("Must be in mm/dd/yyyy format");
				System.out.print("New Due Date: ");
				String sDate = scan.nextLine();
				
				
				Date date = null;
				try {
					date = new SimpleDateFormat("MM/dd/yyyy").parse(sDate);
				} catch (Exception e) {
					System.out.println("Error: Incorrect input format!");
				}
				
				
				boolean proper = date.after(loan.getDateOut());
				if (proper == false) {
					System.out.println("Error! Due Date must be after Checkout Date: " +loan.getDateOut());
					System.out.println("Please try again later!");
					return;
				}
				
				loan.setDateDue(date);
	
				blDAL.updateLoan(loan);
				loan = blDAL.getBookLoan(book.getId(), branch.getBranchId(), borrower.getCardNumber());
				System.out.println("\n" + borrower.getName() + "'s " + branch.getBranchName() + "Branch Loan Details:");
				System.out.println("Book: " + book.getTitle() + "\nNew Due Date: " + loan.getDateDue());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
