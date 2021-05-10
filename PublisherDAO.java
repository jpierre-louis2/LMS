//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'publisher' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Publisher;

public class PublisherDAO {
	
	//Gets 1 Publisher from database specified by ID
	public Publisher getPublisher(int id) {
		
		Publisher publisher = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT publisherName, publisherAddress, publisherPhone FROM tbl_publisher WHERE publisherId=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			//Step 3 Extract data from result set
			if(rs.next()) {
				String name = rs.getString("publisherName");
				String address = rs.getString("publisherAddress");
				String phone = rs.getString("publisherPhone");
				publisher = new Publisher(id, name, address, phone);
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
		
		return publisher;
	}
	
	
	
	//Returns a list of all Publishers in database
	public ArrayList<Publisher> getPublishers() {
		
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT publisherId, publisherName, publisherAddress, publisherPhone FROM tbl_publisher WHERE publisherId!=0";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int id = rs.getInt("publisherId");
				String name = rs.getString("publisherName");
				String address = rs.getString("publisherAddress");
				String phone = rs.getString("publisherPhone");
				Publisher publisher = new Publisher(id, name, address, phone);
				publishers.add(publisher);	
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
				
		return publishers;
	}
	
	
	//Adds a new publisher to Database
	public void addPublisher(Publisher publisher) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a Statement
			pstmt = conn.prepareStatement("INSERT INTO tbl_publisher (publisherId, publisherName, publisherAddress, publisherPhone) VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, publisher.getId());
			pstmt.setString(2, publisher.getName());
			pstmt.setString(3, publisher.getAddress());
			pstmt.setString(4, publisher.getPhoneNumber());
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
	
	
	//Updates a publisher in database
	public void updatePublisher(Publisher publisher) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute an update statement
			pstmt = conn.prepareStatement("UPDATE tbl_publisher SET publisherName=?, publisherAddress=?, publisherPhone=? WHERE publisherId=?");
			pstmt.setString(1, publisher.getName());
			pstmt.setString(2, publisher.getAddress());
			pstmt.setString(3, publisher.getPhoneNumber());
			pstmt.setInt(4, publisher.getId());
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
	public void deletePublisher(int id) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("DELETE FROM tbl_publisher WHERE publisherId=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			//Step 3: Clean up Environment
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
