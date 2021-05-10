//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'author' table of the Library Database
package com.ss.apr.jb.tables;

public class Author {
	
	private int id;
	private String name;
	
	public Author(int authorId, String authorName) {
		setId(authorId);
		setName(authorName);
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

}