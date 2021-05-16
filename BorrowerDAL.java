//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BorrowerDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.BorrowerDAO;
import com.ss.apr.jb.tables.Borrower;

public class BorrowerDAL {
	
	Util u = new Util();
	
	//Prints out all Borrowers
	public void printBorrowers() throws SQLException{
		ArrayList<Borrower> borrowers = getBorrowers();
		int count = 1;
		System.out.println("\nBorrowers:");
		for(Borrower borrower : borrowers) {
			System.out.println(count + ". " + borrower.getName() + ", Address: " + borrower.getAddress() + " Phone: " + borrower.getPhone());
			++count;
		}
	}
	//Returns an ArrayList of Borrowers
	public ArrayList<Borrower> getBorrowers() throws SQLException{
		Connection conn = null;
		ArrayList<Borrower> borrowers = null;
		try {
			conn = u.getConnection();
			BorrowerDAO br = new BorrowerDAO(conn);
			borrowers = br.getAllBorrowers();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return borrowers;
	}
	//Returns Borrower specified by Borrower Id
	public Borrower getBorrower(int cardNo) throws SQLException{
		Connection conn = null;
		Borrower borrower = null;
		try {
			conn = u.getConnection();
			BorrowerDAO br = new BorrowerDAO(conn);
			borrower = br.getBorrower(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return borrower;
	}
	//Generates auto-increment ID for adding a new borrower
	private int findNewBorrowerId() throws SQLException{
		ArrayList<Borrower> borrowers = getBorrowers();
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
	public String addBorrower(String name, String address, String phone) throws SQLException{
		Connection conn = null;
		int newId = findNewBorrowerId();
		Borrower borrower= new Borrower(newId, name, address, phone);
		try {
			conn = u.getConnection();
			BorrowerDAO br = new BorrowerDAO(conn);
			br.addBorrower(borrower);
			conn.commit();
			printBorrowers();
			return "Borrower Successfully Added!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Borrower Successfully Added!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Update an existing Borrower's information based on Borrower Id/Card Number
	public String updateBorrower(int bId, String name, String address, String phone) throws SQLException{
		Connection conn = null;
		Borrower borrower = new Borrower(bId, name, address, phone);
		try {
			conn = u.getConnection();
			BorrowerDAO br = new BorrowerDAO(conn);
			br.updateBorrower(borrower);
			conn.commit();
			printBorrowers();
			return "Borrower Successfully Updated!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Borrower Could not be Updated!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Deletes a Borrower from Database based on Borrower Id/Card Number
	public String deleteBorrower(int cardNo) throws SQLException{
		Connection conn = null;
		try {
			conn = u.getConnection();
			BorrowerDAO br = new BorrowerDAO(conn);
			br.deleteBorrower(cardNo);
			conn.commit();
			printBorrowers();
			return "Borrower Successfully Deleted!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Borrower Could not be Deleted!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
