//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'branch' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Branch;

public class BranchDAO extends BaseDAO<Branch>{

	//Supports Database Connection
	public BranchDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of Publishers based on the Select Statement sent
	public ArrayList<Branch> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<Branch> branches = new ArrayList<Branch>();
		while(rs.next()) {
			int id = rs.getInt("branchId");
			String name = rs.getString("branchName");
			String address = rs.getString("branchAddress");
			Branch branch = new Branch(id, name, address);
			branches.add(branch);
		}
		return branches;
	}
	//Returns the Branch specified by Branch Id
	public Branch getBranch(int id) throws ClassNotFoundException, SQLException{
		ArrayList<Branch> branches = read("SELECT branchId, branchName, branchAddress FROM tbl_library_branch WHERE branchId=?", new Object[] {id});
		return branches.get(0);
	}
	//Returns an ArrayList of all Branches in database
	public ArrayList<Branch>getAllBranches() throws ClassNotFoundException, SQLException{
		ArrayList<Branch> branches = read("SELECT branchId, branchName, branchAddress FROM tbl_library_branch", null);
		return branches;
	}
	//Inserts a new Branch into the Branch table
	public void addBranch(Branch branch) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_library_branch (branchId, branchName, branchAddress) VALUES (?, ?, ?)", new Object[] {branch.getBranchId(), branch.getBranchName(), branch.getBranchAddress()});
	}
	//Updates Branch in table specified by Branch Id
	public void updateBranch(Branch branch) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_library_branch SET branchName=?, branchAddress=? WHERE branchId=?", new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}
	//Updates Branch in table specified by Branch Id
	public void deleteBranch(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_library_branch WHERE branchId=?", new Object[] {id});
	}

}	
	