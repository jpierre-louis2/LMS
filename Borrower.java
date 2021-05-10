//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'borrower' table of the Library Database
package com.ss.apr.jb.tables;

public class Borrower {
	
	private int cardNumber;
	private String name;
	private String address;
	private String phone;
	
	public Borrower(int number, String n, String a, String p) {
		setCardNumber(number);
		setName(n);
		setAddress(a);
		setPhone(p);
	}
	
	
	public int getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}