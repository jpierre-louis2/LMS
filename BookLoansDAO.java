//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'book_loans' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import com.ss.apr.jb.tables.BookLoans;

public class BookLoansDAO {
	
	//Returns a list of loans belonging to a borrower for a specific library branch
	public ArrayList<BookLoans> getBorrowerLoans(int branchId, int cardNo){
		
		ArrayList<BookLoans> loans = new ArrayList<BookLoans>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT bookId, dateOut, dueDate FROM tbl_book_loans WHERE branchId=? AND cardNo=?");
			pstmt.setInt(1, branchId);
			pstmt.setInt(2, cardNo);
			ResultSet rs = pstmt.executeQuery();
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int book = rs.getInt("bookId");
				Date out = rs.getDate("dateOut");
				Date due = rs.getDate("dueDate");
				BookLoans loan = new BookLoans(book, branchId, cardNo, out, due);
				loans.add(loan);
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
				
		return loans;
	}
	
	
	
	//Returns a loan for a specific book for a specific user, from a specific branch
	public BookLoans getBookLoan(int bookId, int branchId, int cardNo) {
		
		BookLoans loan = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection Inputs are Database URL, Username and Password
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT dateOut, dueDate FROM tbl_book_loans WHERE bookId=? AND branchId=? AND cardNo=?");
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, branchId);
			pstmt.setInt(3, cardNo);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Date out = rs.getDate("dateOut");
				Date due = rs.getDate("dueDate");
				loan = new BookLoans(bookId, branchId, cardNo, out, due);
			}
			//Step 3: Clean-up environment
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
		return loan;
	}
	
	
	//Returns a list of all Books Loans in database
	public ArrayList<BookLoans> getBookLoans() {
		
		ArrayList<BookLoans> loans = new ArrayList<BookLoans>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password/)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT bookId, branchId, cardNo, dateOut, dueDate FROM tbl_book_loans";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int book = rs.getInt("bookId");
				int branch = rs.getInt("branchId");
				int card = rs.getInt("cardNo");
				Date out = rs.getDate("dateOut");
				Date due = rs.getDate("dueDate");
				BookLoans loan = new BookLoans(book, branch, card, out, due);
				loans.add(loan);
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
				
		return loans;
	}
	
	
	//Adds a new loan to Database
	public void addLoan(BookLoans loan) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute statement
			pstmt = conn.prepareStatement("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, ?, ?)");
			pstmt.setInt(1, loan.getBookId());
			pstmt.setInt(2, loan.getBranchId());
			pstmt.setInt(3, loan.getCardNo());
			//Sets Date Out to SQL format
			java.sql.Date date1 = new java.sql.Date(loan.getDateOut().getTime());
			//Sets Date in to SQL format
			java.sql.Date date2 = new java.sql.Date(loan.getDateDue().getTime());
			pstmt.setDate(4, date1);
			pstmt.setDate(5, date2);
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
	
	
	
	//Updates loan information in database
	public void updateLoan(BookLoans loan) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute statement
			pstmt = conn.prepareStatement("UPDATE tbl_book_loans SET dateOut=?, dueDate=? WHERE bookId=? AND branchId=? AND cardNo=?");
			pstmt.setInt(3, loan.getBookId());
			pstmt.setInt(4, loan.getBranchId());
			pstmt.setInt(5, loan.getCardNo());
			//Sets Date Out to SQL format
			java.sql.Date date1 = new java.sql.Date(loan.getDateOut().getTime());
			//Sets Date in to SQL format
			java.sql.Date date2 = new java.sql.Date(loan.getDateDue().getTime());
			pstmt.setDate(1, date1);
			pstmt.setDate(2, date2);
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
	
	
	//Deletes a loan for a specific book, at a specific branch, for a specific user
	public void deleteLoan(int bookId, int branchId, int cardNo) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute statement
			pstmt = conn.prepareStatement("DELETE FROM tbl_book_loans WHERE bookId=? AND branchId=? AND cardNo=?");
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, branchId);
			pstmt.setInt(3, cardNo);
			pstmt.executeUpdate();
			
			//Step 3: Clean up environment
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