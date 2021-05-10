//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'book' table of the Library Database
package com.ss.apr.jb.tables;

public class Book {
	
	private String title;
	private int id;
	private int authId;
	private int pubId;
	
	public Book(int bookId, String titleName, int aId, int pId) {
		setId(bookId);
		setAuthId(aId);
		setPubId(pId);
		setTitle(titleName);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthId() {
		return authId;
	}

	public void setAuthId(int authId) {
		this.authId = authId;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
	

}