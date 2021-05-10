//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for AuthorDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.AuthorDAO;
import com.ss.apr.jb.DAO.BookDAO;
import com.ss.apr.jb.tables.Author;

public class AuthorDAL {
	
	AuthorDAO au = new AuthorDAO();
	BookDAO b = new BookDAO();
	
	//Prints out all authors in database
	public void printAuthors() {
		ArrayList<Author> Authors = getAuthors();
		int count = 1;
		System.out.println("\nAuthors:");
		for(Author author : Authors) {
			System.out.println(count + ". " + author.getName());
			++count;
		}
	}
	//Removes specified author from Database
	public void deleteAuthor(int id) {
		b.updateAuthorBooks(id);
		au.deleteAuthor(id);
	}
	//Returns an list of all authors
	public ArrayList<Author> getAuthors(){
		ArrayList<Author> authors = au.getAuthors();
		return authors;
	}
	//Returns the name of an Author
	public String getAuthorName(int id) {
		Author author = au.getAuthor(id);
		return author.getName();
	}
	//Returns an author at specified ID
	public Author getAuthor(int id) {
		Author author = au.getAuthor(id);
		return author;
	}
	//Adds an author to database
	public void addAuthor(String name) {
		int newId = findNewAuthorId();
		Author author = new Author(newId, name);
		au.addAuthor(author);
	}
	//Updates an author's information in database
	public void updateAuthor(int aId, String name) {
		Author author = new Author(aId, name);
		au.updateAuthor(author);
	}
	//Returns a new ID to be used for adding a new Author
	//Based on auto-increment
	private int findNewAuthorId() {
		ArrayList<Author> authors = au.getAuthors(); 
		if (authors.size() == 0) {
			return 1;
		}
		int highest = authors.get(0).getId();
		for(Author author : authors) {
			if (author.getId() > highest)
				highest = author.getId();
		}
		
		return highest + 1;
	}
	
	

}
