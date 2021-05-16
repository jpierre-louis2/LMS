//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'author' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower>{
	
	//Supports Database Connection
	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of Borrowers based on the Select Statement sent
	public ArrayList<Borrower> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
		while(rs.next()) {
			//Retrieve by column name
			int card = rs.getInt("cardNo");
			String name = rs.getString("name");
			String address = rs.getString("address");
			String phone = rs.getString("phone");
			Borrower borrower = new Borrower(card, name, address, phone);
			borrowers.add(borrower);
		}
		return borrowers;
	}
	//Returns the Borrower specified by Borrower Id
	public Borrower getBorrower(int card) throws ClassNotFoundException, SQLException{
		ArrayList<Borrower> borrowers = read("SELECT cardNo, name, address, phone FROM tbl_borrower WHERE cardNo=?", new Object[] {card});
		return borrowers.get(0);
	}
	//Returns an ArrayList of all Borrowers
	public ArrayList<Borrower>getAllBorrowers() throws ClassNotFoundException, SQLException{
		ArrayList<Borrower> borrowers = read("SELECT cardNo, name, address, phone FROM tbl_borrower", null);
		return borrowers;
	}
	//Inserts a new Borrower into Borrower table in database
	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_borrower (cardNo, name, address, phone) VALUES (?, ?, ?, ?)", new Object[] {borrower.getCardNumber(), borrower.getName(), borrower.getAddress(), borrower.getPhone()});
	}
	//Updates a Borrower specified by Borrower Id in database
	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_borrower SET name=?, address=?, phone=? WHERE cardNo=?", new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNumber()});
	}
	//Deletes a Borrower specified by Borrower Id in database
	public void deleteBorrower(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_borrower WHERE cardNo=?", new Object[] {id});
	}


}
