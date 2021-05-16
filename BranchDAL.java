//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BranchDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.BranchDAO;
import com.ss.apr.jb.tables.Branch;

public class BranchDAL {
	
	Util u = new Util();

	//Prints out all Branches
	public void printBranches() throws SQLException{
		ArrayList<Branch> branches = getBranches();
		int count = 1;
		System.out.println("\nBranches:");
		for(Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName() + ", " + branch.getBranchAddress());
			++count;
		}
	}
	//Returns an ArrayList of all Library Branches
	public ArrayList<Branch> getBranches() throws SQLException{
		Connection conn = null;
		ArrayList<Branch> branches = null;
		try {
			conn = u.getConnection();
			BranchDAO bh = new BranchDAO(conn);
			branches = bh.getAllBranches();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return branches;
	}
	//Returns Branch specified by Branch Id
	public Branch getBranch(int id) throws SQLException{
		Connection conn = null;
		Branch branch = null;
		try {
			conn = u.getConnection();
			BranchDAO bh = new BranchDAO(conn);
			branch = bh.getBranch(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return branch;
	}
	//Used to add a new branch by producing auto increment ID
	private int findNewBranchId() throws SQLException{
		ArrayList<Branch> branches = getBranches(); 
		if (branches.size() == 0) {
			return 1;
		}
		int highest = branches.get(0).getBranchId();
		for(Branch branch : branches) {
			if (branch.getBranchId() > highest)
				highest = branch.getBranchId();
		}
		
		return highest + 1;
	}
	//Adds a new Branch to Database
	public String addBranch(String name, String address) throws SQLException{
		Connection conn = null;
		int newId = findNewBranchId();
		Branch branch = new Branch(newId, name, address);
		try {
			conn = u.getConnection();
			BranchDAO bh = new BranchDAO(conn);
			bh.addBranch(branch);
			conn.commit();
			printBranches();
			return "Branch Successfully Added!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Branch Could not be Added!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
		
	}
	//Makes updates to a Branch specified by Branch Id
	public String updateBranch(int id, String name, String address) throws SQLException{
		Connection conn = null;
		Branch branch = new Branch(id, name, address);
		try {
			conn = u.getConnection();
			BranchDAO bh = new BranchDAO(conn);
			bh.updateBranch(branch);
			conn.commit();
			printBranches();
			return "Branch Successfully Updated!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Branch Could not be Updated!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Deletes specified Branch from Database specified by Branch Id
	public String deleteBranch(int id) throws SQLException{
		Connection conn = null;
		try {
			conn = u.getConnection();
			BranchDAO bh = new BranchDAO(conn);
			bh.deleteBranch(id);
			conn.commit();
			printBranches();
			return "Branch Successfully Deleted!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Branch Could not be Deleted!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
