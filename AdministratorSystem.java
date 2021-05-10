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
	UtilityFunctions u = new UtilityFunctions();

	//Adds a new author
	public void addAuthor() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type 'Quit' to exit");
		System.out.println("What is the name of the new Author?");
		String name = scan.nextLine();
		
		autDAL.addAuthor(name);
		autDAL.printAuthors();
	}
	//Adds a new publisher
	public void addPublisher() {
		System.out.println("Type 'Quit' at anytime to exit");
		Scanner scan = new Scanner(System.in);
		System.out.print("What is the name of the new Publisher? ");
		String name = scan.nextLine();
		if(u.validateQuit(name)) {
			return;
		}
		System.out.print("What is their Address? ");
		String address = scan.nextLine();
		if(u.validateQuit(address)) {
			return;
		}
		System.out.print("What is their Phone Number? ");
		String phone = scan.nextLine();
		if(u.validateQuit(phone)) {
			return;
		}
		System.out.println("Adding " + name + " Publishing, located at " + address + " Phone Number: " + phone +"\n");
		pubDAL.addPublisher(name, address, phone);
		pubDAL.printPublishers();
	}
	//Adds a new borrower
	public void addBorrower() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type 'Quit' at anytime to exit");
		System.out.print("What is the name of the new Borrower? ");
		String name = scan.nextLine();
		if(u.validateQuit(name)) {
			return;
		}
		System.out.print("What is their Address? ");
		String address = scan.nextLine();
		if(u.validateQuit(address)) {
			return;
		}
		System.out.print("What is their Phone Number? ");
		String phone = scan.nextLine();
		if(u.validateQuit(phone)) {
			return;
		}
		System.out.println("Adding " + name + " from " + address + " Phone Number: " + phone +"\n");
		borDAL.addBorrower(name, address, phone);
		borDAL.printBorrowers();
	}
	//Adds a new branch
	public void addBranch() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type 'Quit' at anytime to exit");
		System.out.print("What is the name of the new Branch? ");
		String name = scan.nextLine();
		if(u.validateQuit(name)) {
			return;
		}
		System.out.print("What is the Address? ");
		String address = scan.nextLine();
		if(u.validateQuit(address)) {
			return;
		}
		System.out.println("Adding " + name + " Branch, located in " + address);
		brDAL.addBranch(name, address);
		brDAL.printBranches();
	}
	//Adds a new book
	public void addBook() {
		Scanner scan = new Scanner(System.in);
		ArrayList<Publisher> p = pubDAL.getPublishers();
		ArrayList<Author> a = autDAL.getAuthors();
		
		//Accounts for no Authors or Publishers
		if (p.size() == 0) {
			System.out.println("\nError, No Available Publishers!");
			return;
		}
		else if (a.size() == 0) {
			System.out.println("\\nError, No Available Authors!");
			return;
		}
		System.out.println("Type 'Quit' to exit");
		System.out.println("What is the Title of the new Book?");
		String title = scan.nextLine();
		if(u.validateQuit(title)) {
			return;
		}
		System.out.print("\nWho is the publisher?");
		pubDAL.printPublishers();
		int input = u.getInput(p.size(), 1);
		int pId = p.get(input - 1).getId();
		System.out.print("\nWho is the author");
		autDAL.printAuthors();
		input = u.getInput(a.size(), 1);
		int aId = a.get(input - 1).getId();
		
		System.out.println("\nAdding " + title + " by " + autDAL.getAuthorName(aId) + " from " + pubDAL.getPublisherName(pId) + " Publishing");
		boDAL.addBook(title, aId, pId);
	}
	//Prompts for information and updates book
	public void updateBook() {
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
		int input = u.getInput(b.size(), 1);
		int bId = b.get(input - 1).getId();
		Book book = boDAL.getBook(bId);
		System.out.print("\nWho is the publisher?");
		pubDAL.printPublishers();
		input = u.getInput(p.size(), 1);
		int pId = p.get(input - 1).getId();
		System.out.print("\nWho is the author");
		autDAL.printAuthors();
		input = u.getInput(a.size(), 1);
		int aId = a.get(input - 1).getId();
		
		System.out.println("\n*Updating*\n" + book.getTitle() + " by " + autDAL.getAuthorName(aId) + " from " + pubDAL.getPublisherName(pId) + " Publishing\n");
		boDAL.updateBook(bId, book.getTitle(), aId, pId);
	}
	//Prompts for information and updates Author
	public void updateAuthor() {
		ArrayList<Author> a = autDAL.getAuthors();
	
		if (a.size() == 0) {
			System.out.println("\nError, No Available Authors!");
			return;
		}
		
		System.out.print("\nWhich author would you like to update?");
		autDAL.printAuthors();
		int input = u.getInput(a.size(), 1);
		int aId = a.get(input - 1).getId();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("\nWhat is the new name of the Author?");
		String name = scan.nextLine();
		
		System.out.println("\n*Updating*\n");
		autDAL.updateAuthor(aId, name);
		autDAL.printAuthors();
	}
	//Prompts for information and updates Publisher
	public void updatePublisher() {
		ArrayList<Publisher> p = pubDAL.getPublishers();
		
		if (p.size() == 0) {
			System.out.println("\nError, No Available Publishers!");
			return;
		}
		
		System.out.print("\nWhich publisher would you like to update? ");
		pubDAL.printPublishers();
		int input = u.getInput(p.size(), 1);
		int pId = p.get(input - 1).getId();
		
		Scanner scan = new Scanner(System.in);
		System.out.print("What is the new name of the Publisher? ");
		String name = scan.nextLine();
		System.out.print("What is their Address? ");
		String address = scan.nextLine();
		System.out.print("What is their Phone Number? ");
		String phone = scan.nextLine();
		
		System.out.println("\n*Updating*\n");
		pubDAL.updatePublisher(pId, name, address, phone);
		pubDAL.printPublishers();
	}
	//Prompts for information and updates Borrower
	public void updateBorrower() {
		Scanner scan = new Scanner(System.in);
		ArrayList<Borrower> b = borDAL.getBorrowers();
		
		if (b.size() == 0) {
			System.out.println("\nError, No Available Borrowers!");
			return;
		}
		
		System.out.print("\nWhich borrower would you like to update? ");
		borDAL.printBorrowers();
		int input = u.getInput(b.size(), 1);
		int bId = b.get(input - 1).getCardNumber();
		
		System.out.print("What is the new name of the Borrower? ");
		String name = scan.nextLine();
		System.out.print("What is their Address? ");
		String address = scan.nextLine();
		System.out.print("What is their Phone Number? ");
		String phone = scan.nextLine();
		
		System.out.println("\n*Updating*\n");
		borDAL.updateBorrower(bId, name, address, phone);
		borDAL.printBorrowers();
	}
	//Prompts for information and updates Branch
	public void updateBranch(){
		ArrayList<Branch> b = brDAL.getBranches();
		
		if (b.size() == 0) {
			System.out.println("\nError, No Available Borrowers!");
			return;
		}
		
		System.out.println("What Branch would you like to update?");
		brDAL.printBranches();
		int input = u.getInput(b.size(), 1);
		int bId = b.get(input - 1).getBranchId();
		brDAL.updateBranch(bId);
	}
	//Prompts for information and deletes Branch
	public void deleteBranch(){
		ArrayList<Branch> b = brDAL.getBranches();
		
		if (b.size() == 0) {
			System.out.println("\nError, No Available Branches!");
			return;
		}
		
		System.out.println("What Branch would you like to Delete?");
		brDAL.printBranches();
		int input = u.getInput(b.size(), 1);
		int bId = b.get(input - 1).getBranchId();
		System.out.println("Now Deleting: " + brDAL.getBranch(bId).getBranchName() + "\n");
		brDAL.deleteBranch(bId);
		brDAL.printBranches();
	}
	//Prompts for information and deletes book
	public void deleteBook() {
		ArrayList<Book> b = boDAL.getBooks();
		
		if (b.size() == 0) {
			System.out.println("\nError, No Available Books!");
			return;
		}
		
		System.out.println("What Book would you like to Delete?");
		System.out.println("*Warning, this will Delete all Loans and copies currently belonging to users*");
		boDAL.printBooks();
		int input = u.getInput(b.size(), 1);
		int bId = b.get(input - 1).getId();
		Book book = boDAL.getBook(bId);
		
		System.out.println("\nNow Deleting: " + book.getTitle());
		boDAL.deleteBook(bId);
	}
	//Prompts for information and deletes author
	public void deleteAuthor() {
		ArrayList<Author> a = autDAL.getAuthors();
		
		if (a.size() == 0) {
			System.out.println("\nError, No Available Authors!");
			return;
		}
		
		System.out.println("What Author would you like to Delete?");
		//System.out.println("*Warning, this will Delete all Loans and copies currently belonging to users*");
		autDAL.printAuthors();
		int input = u.getInput(a.size(), 1);
		int aId = a.get(input - 1).getId();
		Author author = autDAL.getAuthor(aId);
		
		System.out.println("\nNow Deleting: " + author.getName());
		autDAL.deleteAuthor(aId);
	}
	//Prompts for information and deletes publisher
	public void deletePublisher() {
		ArrayList<Publisher> p = pubDAL.getPublishers();
		
		if (p.size() == 0) {
			System.out.println("\nError, No Available Publishers!");
			return;
		}
		
		System.out.println("What Publisher would you like to Delete?");
		//System.out.println("*Warning, this will Delete all Loans and copies currently belonging to users*");
		pubDAL.printPublishers();
		int input = u.getInput(p.size(), 1);
		int pId = p.get(input - 1).getId();
		Publisher publisher = pubDAL.getPublisher(pId);
		
		System.out.println("\nNow Deleting: " + publisher.getName() + " Publishing");
		pubDAL.deletePublisher(pId);
	}
	//Prompts for information and deletes borrower
	public void deleteBorrower() {
		ArrayList<Borrower> b = borDAL.getBorrowers();
		
		if (b.size() == 0) {
			System.out.println("\nError, No Available Borrowers!");
			return;
		}
		
		System.out.println("What Borrower would you like to Delete?");
		//System.out.println("*Warning, this will Delete all loans and copies currently belonging to users*");
		borDAL.printBorrowers();
		int input = u.getInput(b.size(), 1);
		int bId = b.get(input - 1).getCardNumber();
		Borrower borrower = borDAL.getBorrower(bId);
		
		System.out.println("\nNow Deleting: " + borrower.getName());
		borDAL.deleteBorrower(bId);
	}
	//Overrides the due date for a specific loan
	public void overrideDueDate(){

		Scanner scan = new Scanner(System.in);
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
		int input = u.getInput(borrowerSize, 1);
		Borrower borrower = borrowers.get(input - 1);
		
		//Gets which Library Branch
		System.out.println("Which Library Branch is this for?");
		brDAL.printBranches();
		input = u.getInput(branchSize, 1);
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
			input = u.getInput(books.size(), 1);
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
		
	}
	
	
	
	//Admin Book Menu
	public void bookMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Book \n2. Update a Book \n3. Delete a Book \n4. Quit to previous");
		int input = u.getInput(4, 1);
		switch(input) {
			case 1:
				addBook();
				break;
			case 2:
				updateBook();
				break;
			case 3:
				deleteBook();
				break;
		}
	}
	//Admin Author Menu
	public void authorMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Author \n2. Update an Author's information \n3. Delete an Author \n4. Quit to previous");
		int input = u.getInput(4, 1);
		switch(input) {
			case 1:
				addAuthor();
				break;
			case 2:
				updateAuthor();
				break;
			case 3:
				deleteAuthor();
				break;
		}
	}
	//Admin Publisher Menu
	public void publisherMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Publisher \n2. Update a Publisher \n3. Delete a Publisher \n4. Quit to previous");
		int input = u.getInput(4, 1);
		switch(input) {
			case 1:
				addPublisher();
				break;
			case 2:
				updatePublisher();
				break;
			case 3:
				deletePublisher();
				break;
		}
	}
	//Admin Library Branch Menu
	public void branchMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Library Branch \n2. Update an exisiting Library Branch \n3. Delete a Library Branch \n4. Quit to previous");
		int input = u.getInput(4, 1);
		switch(input) {
			case 1:
				addBranch();
				break;
			case 2:
				updateBranch();
				break;
			case 3:
				deleteBranch();
				break;
		}
	}
	//Admin Borrower menu
	public void borrowerMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Borrower \n2. Update a Borrower \n3. Delete a Borrower \n4. Quit to previous");
		int input = u.getInput(4, 1);
		switch(input) {
			case 1:
				addBorrower();
				break;
			case 2:
				updateBorrower();
				break;
			case 3:
				deleteBorrower();
				break;
		}
	}
	//Main Menu prompt
	public void mainMenu() {
		int input = 0;
		
		while (input != 7) {
			System.out.println("\nAdmin Menu: ");
			System.out.println("1. Book Menu \n2. Author Menu \n3. Publisher Menu");
			System.out.println("4. Library Branch Menu \n5. Borrower Menu \n6. Override Due Date for Book Loan \n7. Quit to Main Menu");
			input = u.getInput(7, 1);
			
			switch(input) {
			case 1:
				bookMenu();
				break;
			case 2:
				authorMenu();
				break;
			case 3:
				publisherMenu();
				break;
			case 4:
				branchMenu();
				break;
			case 5:
				borrowerMenu();
				break;
			case 6:
				overrideDueDate();
				break;
			}
		}
		System.out.println("\n");
	}
	
}
