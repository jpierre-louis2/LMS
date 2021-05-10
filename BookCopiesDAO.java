//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'book_copies' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ss.apr.jb.tables.BookCopies;


public class BookCopiesDAO {
	
	//Returns the amount of copies for a specific book at a specific branch
	public BookCopies getCopy(int bookId, int branchId) {
		
		BookCopies copies = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection
			//Inputs are Database URL, Username and Password
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 4: Execute a query
			pstmt = conn.prepareStatement("SELECT noOfCopies FROM tbl_book_copies WHERE bookId=? AND branchId=?");
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, branchId);
			//Result set is the output of your Query statement
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//Extract data from result set
				int num = rs.getInt("noOfCopies");
				copies = new BookCopies(bookId, branchId, num);
			}
			//Step 6: Clean-up environment
			pstmt.close();
			rs.close();
			conn.close();
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			//Handle errors for Class.forName
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
		return copies;
	}
	
	
	//Returns a list of all book copies for all books a specified branch
	public ArrayList<BookCopies> getAvailableCopy(int branchId) {
		
		ArrayList<BookCopies> available = new ArrayList<BookCopies>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT bookId, noOfCopies FROM tbl_book_copies WHERE branchId=? AND noOfCopies!=0");
			pstmt.setInt(1, branchId);
			ResultSet rs = pstmt.executeQuery();
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int num = rs.getInt("noOfCopies");
				int bookId = rs.getInt("bookId");
				BookCopies copies = new BookCopies(bookId, branchId, num);
				available.add(copies);
			}
			//Step 4: Clean-up environment
			pstmt.close();
			rs.close();
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
		return available;
	}
	
	
	//Returns a list of all book copies in database
	public ArrayList<BookCopies> getAllCopies() {
		
		ArrayList<BookCopies> copies = new ArrayList<BookCopies>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT bookId, branchId, noOfCopies FROM tbl_book_copies";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {

				int bookId = rs.getInt("bookId");
				int branchId = rs.getInt("branchId");
				int num = rs.getInt("noOfCopies");
				BookCopies copy = new BookCopies(bookId, branchId, num);
				copies.add(copy);
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
				
		return copies;
	}
	
	
	//Adds books to a branch in Database
	public void addCopies(BookCopies copies) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a statement
			pstmt = conn.prepareStatement("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?, ?, ?)");
			pstmt.setInt(3, copies.getCopies());
			pstmt.setInt(1, copies.getBookId());
			pstmt.setInt(2, copies.getBranchId());
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
	
	
	//Updates amount of copies in database
	public void updateCopies(BookCopies copies) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("UPDATE tbl_book_copies SET noOfCopies=? WHERE branchId=? AND bookId=?");
			pstmt.setInt(1, copies.getCopies());
			pstmt.setInt(2, copies.getBranchId());
			pstmt.setInt(3, copies.getBookId());
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
	
	
	
	//Deletes a copy of a book from a branch specified by ID
	public void deleteBookCopies(int branchId, int bookId) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("DELETE FROM tbl_book_copies WHERE branchId=? AND bookId=?");
			pstmt.setInt(1, branchId);
			pstmt.setInt(2, bookId);
			pstmt.executeUpdate();
			
			//Step 3: Clean-up Environment
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
