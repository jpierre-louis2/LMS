//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'branch' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Branch;


public class BranchDAO {
	

	//Returns branch specified by ID
	public Branch getBranch(int id) {
	
		Branch branch = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			pstmt = conn.prepareStatement("SELECT branchName, branchAddress FROM tbl_library_branch WHERE branchId=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			//Step 3: Extract data from result set
			if(rs.next()) {
				String name = rs.getString("branchName");
				String address = rs.getString("branchAddress");
				branch = new Branch(id, name, address);
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
				
		return branch;
	}
	
	
	//Returns a list all branches in database
	public ArrayList<Branch> getBranches() {
		
		ArrayList<Branch> branches = new ArrayList<Branch>();
		Connection conn = null;
		Statement stmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT branchId, branchName, branchAddress FROM tbl_library_branch";
			ResultSet rs = stmt.executeQuery(sql);
			
			//Step 3: Extract data from result set
			while(rs.next()) {
				int id = rs.getInt("branchId");
				String name = rs.getString("branchName");
				String address = rs.getString("branchAddress");
				Branch branch = new Branch(id, name, address);
				branches.add(branch);
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
				
		return branches;
	}
	
	
	
	//Adds a new branch to Database
	public void addBranch(Branch branch) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute a Statement
			pstmt = conn.prepareStatement("INSERT INTO tbl_library_branch (branchId, branchName, branchAddress) VALUES (?, ?, ?)");
			pstmt.setInt(1, branch.getBranchId());
			pstmt.setString(2, branch.getBranchName());
			pstmt.setString(3, branch.getBranchAddress());
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
	}
	
	
	//Updates a branch in database
	public void updateBranch(Branch branch) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 2: Execute update statement
			pstmt = conn.prepareStatement("UPDATE tbl_library_branch SET branchName=?, branchAddress=? WHERE branchId=?");
			pstmt.setString(1, branch.getBranchName());
			pstmt.setString(2, branch.getBranchAddress());
			pstmt.setInt(3, branch.getBranchId());
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
	
	
	//Deletes branch specified by ID
	public void deleteBranch(int id) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			//Step 1: Open a connection (Inputs are Database URL, Username and Password)
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
			
			//Step 4: Execute delete statement
			pstmt = conn.prepareStatement("DELETE FROM tbl_library_branch WHERE branchId=?");
			pstmt.setInt(1, id);
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
	