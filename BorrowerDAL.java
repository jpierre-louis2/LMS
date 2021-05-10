//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BorrowerDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.BorrowerDAO;
import com.ss.apr.jb.tables.Borrower;

public class BorrowerDAL {
	
	BorrowerDAO br = new BorrowerDAO();
	
	//Prints out all Borrowers
	public void printBorrowers() {
		ArrayList<Borrower> borrowers = getBorrowers();
		int count = 1;
		System.out.println("\nBorrowers:");
		for(Borrower borrower : borrowers) {
			System.out.println(count + ". " + borrower.getName());
			++count;
		}
	}
	//Returns a list of Borrowers
	public ArrayList<Borrower> getBorrowers(){
		ArrayList<Borrower> borrowers = br.getBorrowers();
		return borrowers;
	}
	//Returns specified Borrower
	public Borrower getBorrower(int cardNo) {
		Borrower borrower = br.getBorrower(cardNo);
		return borrower;
	}
	//Generates auto-increment ID for adding a new borrower
	private int findNewBorrowerId() {
		ArrayList<Borrower> borrowers = br.getBorrowers();
		if (borrowers.size() == 0) {
			return 1;
		}
		int highest = borrowers.get(0).getCardNumber();
		for(Borrower borrower : borrowers) {
			if (borrower.getCardNumber() > highest)
				highest = borrower.getCardNumber();
		}
		
		return highest + 1;
	}
	//Adds a new Borrower to Database
	public void addBorrower(String name, String address, String phone) {
		int newId = findNewBorrowerId();
		Borrower borrower= new Borrower(newId, name, address, phone);
		br.addBorrower(borrower);
	}
	//Update an existing Borrower's information
	public void updateBorrower(int bId, String name, String address, String phone) {
		Borrower borrower = new Borrower(bId, name, address, phone);
		br.updateBorrower(borrower);
	}
	//Deletes a Borrower from Database
	public void deleteBorrower(int cardNo) {
		br.deleteBorrower(cardNo);
	}
}
