//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'Branch' table of the Library Database
package com.ss.apr.jb.tables;

public class Branch {
	
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	public Branch(int id, String name, String address) {
		setBranchId(id);
		setBranchName(name);
		setBranchAddress(address);
	}
	
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

}