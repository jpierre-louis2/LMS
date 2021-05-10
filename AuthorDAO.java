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
import com.ss.apr.jb.tables.Author;

public class AuthorDAO{
	
	//Returns Author from database specified by ID
	public Author getAuthor(int id) {
		
		Author author = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT authorName FROM tbl_author WHERE authorId=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			//Step 3: Extract data from result set
			if(rs.next()) {
				String name = rs.getString("authorName");
				author = new Author(id, name);
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
			catch(SQLException se) {se.printStackTrace();}
		}
		return author;
	}
	
	
	//Returns a list of all Authors in database
	public ArrayList<Author> getAuthors() {
		
		ArrayList<Author> authors = new ArrayList<Author>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT authorId, authorName FROM tbl_author WHERE authorId!=0";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int id = rs.getInt("authorId");
				String name = rs.getString("authorName");
				Author book = new Author(id, name);
				authors.add(book);
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
			catch(SQLException se) {se.printStackTrace();}
		}
				
		return authors;
	}
	
	
	//Adds a new author to Database
	public void addAuthor(Author author) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a Statement
			pstmt = conn.prepareStatement("INSERT INTO tbl_author (authorId, authorName) VALUES (?, ?)");
			pstmt.setInt(1, author.getId());
			pstmt.setString(2, author.getName());
			pstmt.executeUpdate();
			
			//Step 3: Clean-up environment
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
			catch(SQLException se) {se.printStackTrace();}
		}
	}
	
	
	//Updates an author's information in database
	public void updateAuthor(Author author) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute an update statement
			pstmt = conn.prepareStatement("UPDATE tbl_author SET authorName=? WHERE authorId=?");
			pstmt.setString(1, author.getName());
			pstmt.setInt(2, author.getId());
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
	
	
	//Deletes an author from Database specified by ID
	public void deleteAuthor(int id) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute Delete Statement
			pstmt = conn.prepareStatement("DELETE FROM tbl_author WHERE authorId=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			//Step 3: Clean Up Environment
			pstmt.close();
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
	}
	
	
}