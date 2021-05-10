//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'book' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Book;

public class BookDAO {
	
	
	//Returns book specified by ID
	public Book getBook(int id) {
	
		Book book = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT title, authId, pubId FROM tbl_book WHERE bookId=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			//Step 3: Extract data from result set
			if(rs.next()) {
				String title = rs.getString("title");
				int authId = rs.getInt("authId");
				int pubId = rs.getInt("pubId");
				book = new Book(id, title, authId, pubId);
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
				
		return book;
	}
	
	
	//Returns a list of all Books in database
	public ArrayList<Book> getBooks() {
		
		ArrayList<Book> books = new ArrayList<Book>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT bookId, title, authId, pubId FROM tbl_book";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int id = rs.getInt("bookId");
				String title = rs.getString("title");
				int authId = rs.getInt("authId");
				int pubId = rs.getInt("pubId");
				Book book = new Book(id, title, authId, pubId);
				books.add(book);
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
				
		return books;
	}
	
	//Changes AuthorID to 0 in the case of an publisher deletion
	public void updatePublisherBooks(int pId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute update statement
			pstmt = conn.prepareStatement("UPDATE tbl_book SET pubId=0 WHERE pubId=?");
			pstmt.setInt(1, pId);
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
	
	
	//Changes AuthorID to 0 in the case of an author deletion
	public void updateAuthorBooks(int aId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute update statement
			pstmt = conn.prepareStatement("UPDATE tbl_book SET authId=0 WHERE authId=?");
			pstmt.setInt(1, aId);
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
	
	//Adds a new book to Database
	public void addBook(Book book) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("INSERT INTO tbl_book (bookId, title, authId, pubId) VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, book.getId());
			pstmt.setString(2, book.getTitle());
			pstmt.setInt(3, book.getAuthId());
			pstmt.setInt(4, book.getPubId());
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
	
	
	//Updates a book in database
	public void updateBook(Book book) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute update statement
			pstmt = conn.prepareStatement("UPDATE tbl_book SET title=?, authId=?, pubId=? WHERE bookId=?");
			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getAuthId());
			pstmt.setInt(3, book.getPubId());
			pstmt.setInt(4, book.getId());
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
	
	
	//Deletes a book specified by ID
	public void deleteBook(int id) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("DELETE FROM tbl_book WHERE bookId=?");
			pstmt.setInt(1, id);
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

