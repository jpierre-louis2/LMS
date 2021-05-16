package com.ss.apr.jb.BLL;

public class AdministratorMenu {

	AdministratorSystem admin = new AdministratorSystem();
	InputFunctions in = new InputFunctions();
	
	
	//Admin Book Menu
	public void bookMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Book \n2. Update a Book \n3. Delete a Book \n4. Quit to previous");
		int input = in.getInput(4, 1);
		switch(input) {
			case 1:
				admin.addBook();
				break;
			case 2:
				admin.updateBook();
				break;
			case 3:
				admin.deleteBook();
				break;
		}
	}
	//Admin Author Menu
	public void authorMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Author \n2. Update an Author's information \n3. Delete an Author \n4. Quit to previous");
		int input = in.getInput(4, 1);
		switch(input) {
			case 1:
				admin.addAuthor();
				break;
			case 2:
				admin.updateAuthor();
				break;
			case 3:
				admin.deleteAuthor();
				break;
		}
	}
	//Admin Publisher Menu
	public void publisherMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Publisher \n2. Update a Publisher \n3. Delete a Publisher \n4. Quit to previous");
		int input = in.getInput(4, 1);
		switch(input) {
			case 1:
				admin.addPublisher();
				break;
			case 2:
				admin.updatePublisher();
				break;
			case 3:
				admin.deletePublisher();
				break;
		}
	}
	//Admin Library Branch Menu
	public void branchMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Library Branch \n2. Update an exisiting Library Branch \n3. Delete a Library Branch \n4. Quit to previous");
		int input = in.getInput(4, 1);
		switch(input) {
			case 1:
				admin.addBranch();
				break;
			case 2:
				admin.updateBranch();
				break;
			case 3:
				admin.deleteBranch();
				break;
		}
	}
	//Admin Borrower menu
	public void borrowerMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. Add a new Borrower \n2. Update a Borrower \n3. Delete a Borrower \n4. Quit to previous");
		int input = in.getInput(4, 1);
		switch(input) {
			case 1:
				admin.addBorrower();
				break;
			case 2:
				admin.updateBorrower();
				break;
			case 3:
				admin.deleteBorrower();
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
			input = in.getInput(7, 1);
			
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
				admin.overrideDueDate();
				break;
			}
		}
		System.out.println("\n");
	}
	
	
}
