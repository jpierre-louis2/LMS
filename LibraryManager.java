package com.ss.apr.jb.BLL;


public class LibraryManager {

	//Pulls up Main Menu prompt
	public static int mainMenu() {
		UtilityFunctions u = new UtilityFunctions();
		System.out.println("Welcome to the GCIT Library Management System. Which category of user are you?");
		System.out.println("\n1. Librarian \n2. Administrator \n3. Borrower");
		System.out.println("(Enter 0 to Quit)");
		
		int input = u.getInput(3, 0);
		return input;
	}
	
	
	public static void main(String[] args) {

		LibrarianSystem l = new LibrarianSystem();
		BorrowerSystem b = new BorrowerSystem();
		AdministratorSystem a = new AdministratorSystem();
		int input = -1;

		
		while (input != 0) {
			input = mainMenu();
			switch(input) {
				case 1:
					l.lib1();
					break;
				case 2:
					a.mainMenu();
					break;
				case 3:
					b.setCardNumber();
					break;
			}
		}
		
		
		System.out.println("\nNow Exiting, Goodbye!");
		
	}

}
