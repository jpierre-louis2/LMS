//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'author' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Borrower;

public class BorrowerDAO {
	
	//Returns borrower specified by ID
	public Borrower getBorrower(int card) {
	
		Borrower borrower = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT name, address, phone FROM tbl_borrower WHERE cardNo=?");
			pstmt.setInt(1, card);
			ResultSet rs = pstmt.executeQuery();
			
			//Step 3: Extract data from result set
			if(rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				borrower = new Borrower(card, name, address, phone);
			}
			
			//Step 4: Clean-up environment
			rs.close();
			pstmt.close();
			conn.close();
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			//finally block used to close resources
			try {
				if(pstmt != null)
					pstmt.close();
			}
			catch(SQLException se2) {}
			try {
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
				
		return borrower;
	}
	
	
	
	//Returns a list of all Borrowers
	public ArrayList<Borrower> getBorrowers() {
		
		ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT cardNo, name, address, phone FROM tbl_borrower";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				//Retrieve by column name
				int card = rs.getInt("cardNo");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				Borrower borrower = new Borrower(card, name, address, phone);
				borrowers.add(borrower);
			}
			//Step 4: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			//finally block used to close resources
			try {
				if(stmt != null)
					stmt.close();
			}
			catch(SQLException se2) {}
			try {
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
				
		return borrowers;
	}
	
	
	
	//Adds a borrower to database
	public void addBorrower(Borrower borrower) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("INSERT INTO tbl_borrower (cardNo, name, address, phone) VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, borrower.getCardNumber());
			pstmt.setString(2, borrower.getName());
			pstmt.setString(3, borrower.getAddress());
			pstmt.setString(4, borrower.getPhone());
			pstmt.executeUpdate();
			
			//Step 3: Clean-up environment
			pstmt.close();
			conn.close();
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			//finally block used to close resources
			try {
				if(pstmt != null)
					pstmt.close();
			}
			catch(SQLException se2) {}
			try {
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	
	//Updates borrower's information in database
	public void updateBorrower(Borrower borrower) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute update statement
			pstmt = conn.prepareStatement("UPDATE tbl_borrower SET name=?, address=?, phone=? WHERE cardNo=?");
			pstmt.setString(1, borrower.getName());
			pstmt.setString(2, borrower.getAddress());
			pstmt.setString(3, borrower.getPhone());
			pstmt.setInt(4, borrower.getCardNumber());
			pstmt.executeUpdate();
			
			//Step 3: Clean-up environment
			pstmt.close();
			conn.close();
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			//finally block used to close resources
			try {
				if(pstmt != null)
					pstmt.close();
			}
			catch(SQLException se2) {}
			try {
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	
	//Deletes a borrower specified by ID
	public void deleteBorrower(int card) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("DELETE FROM tbl_borrower WHERE cardNo=?");
			pstmt.setInt(1, card);
			pstmt.executeUpdate();
			
			//Step 3: Clean-up environment
			pstmt.close();
			conn.close();
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			//finally block used to close resources
			try {
				if(pstmt != null)
					pstmt.close();
			}
			catch(SQLException se2) {}
			try {
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
	}
	

}
