//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'publisher' table of the Library Database
package com.ss.apr.jb.tables;

public class Publisher {
	
	private int id;
	private String name;
	private String address;
	private String phoneNumber;
	
	public Publisher(int pId, String pName, String pAddress, String pNumber) {
		setId(pId);
		setName(pName);
		setAddress(pAddress);
		setPhoneNumber(pNumber);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}