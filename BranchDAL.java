//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BranchDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;
import java.util.Scanner;
import com.ss.apr.jb.BLL.UtilityFunctions;
import com.ss.apr.jb.DAO.BranchDAO;
import com.ss.apr.jb.tables.Branch;

public class BranchDAL {
	
	BranchDAO bh = new BranchDAO();
	UtilityFunctions u = new UtilityFunctions();

	//Prints out all Branches
	public void printBranches() {
		ArrayList<Branch> branches = getBranches();
	
		int count = 1;
		System.out.println("\nBranches:");
		for(Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName() + ", " + branch.getBranchAddress());
			++count;
		}
	}
	//Deletes specified branch from Database
	public void deleteBranch(int id) {
		bh.deleteBranch(id);
	}
	//Returns a list of Library Branches
	public ArrayList<Branch> getBranches(){
		ArrayList<Branch> branches = bh.getBranches();
		return branches;
	}
	//Returns specified branch
	public Branch getBranch(int id) {
		Branch branch = bh.getBranch(id);
		return branch;
	}
	//Used to add a new branch by producing auto increment ID
	private int findNewBranchId() {
		ArrayList<Branch> branches = bh.getBranches(); 
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
	public void addBranch(String name, String address) {
		int newId = findNewBranchId();
		Branch branch = new Branch(newId, name, address);
		bh.addBranch(branch);
	}
	//Makes updates to a branch and prints out results
	public void updateBranch(int id) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please Enter Branch Name: ");
		String name = scan.nextLine();
		if(u.validateQuit(name)) {
			return;
		}
		System.out.print("Please Enter Branch Address: ");
		String address = scan.nextLine();
		if(u.validateQuit(name)) {
			return;
		}
		Branch branch = new Branch(id, name, address);
		bh.updateBranch(branch);
		printBranches();
	}
}
