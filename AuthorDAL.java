//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for AuthorDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.AuthorDAO;
import com.ss.apr.jb.DAO.BookDAO;
import com.ss.apr.jb.tables.Author;

public class AuthorDAL {
	
	Util u = new Util();
	
	//Prints out all Authors in database
	public void printAuthors() throws SQLException{
		ArrayList<Author> Authors = getAuthors();
		int count = 1;
		System.out.println("\nAuthors:");
		for(Author author : Authors) {
			System.out.println(count + ". " + author.getName());
			++count;
		}
	}
	//Returns the name of an Author specified by Author Id
	public String getAuthorName(int id) throws SQLException{
		Author author = getAuthor(id);
		return author.getName();
	}
	//Returns an ArrayList of all Authors
	public ArrayList<Author> getAuthors() throws SQLException{
		Connection conn = null;
		ArrayList<Author> authors = null;
		try {
			conn = u.getConnection();
			AuthorDAO au = new AuthorDAO(conn);
			authors = au.getAllAuthors();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return authors;
	}
	//Returns an Author specified by Author Id
	public Author getAuthor(int id) throws SQLException{
		Author author = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			AuthorDAO au = new AuthorDAO(conn);
			author = au.getAuthor(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return author;
	}
	//Returns a new ID to be used for adding a new Author
	//Based on auto-increment
	private int findNewAuthorId() throws SQLException{
		ArrayList<Author> authors = getAuthors(); 
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
	//Adds an Author to database
	public String addAuthor(String name) throws SQLException{
		Connection conn = null;
		int newId = findNewAuthorId();
		Author author = new Author(newId, name);
		try {
			conn = u.getConnection();
			AuthorDAO au = new AuthorDAO(conn);
			au.addAuthor(author);
			conn.commit();
			printAuthors();
			return "Author Successfully Added!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Author Could not be Added!";
		}
		finally {
			if(conn!=null) {
				conn.close();
			}
		}
	}
	//Updates an Author's information in database based on Author Id
	public String updateAuthor(int aId, String name) throws SQLException{
		Author author = new Author(aId, name);
		Connection conn = null;
		try {
			conn = u.getConnection();
			AuthorDAO au = new AuthorDAO(conn);
			au.updateAuthor(author);
			conn.commit();
			printAuthors();
			return "Author Successfully Updated!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Author Could not be Updated!";
		}
		finally {
			if(conn!=null) {
				conn.close();
			}
		}
	}
	//Removes an Author from database specified by Author Id
	//Also sets all Books with Author's Id associated to the Author's Id to 0
	public String deleteAuthor(int id) throws SQLException{
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookDAO b = new BookDAO(conn);
			AuthorDAO au = new AuthorDAO(conn);
			b.updateAuthorBook(id);
			au.deleteAuthor(id);
			conn.commit();
			printAuthors();
			return "Author Successfully Deleted!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Author Could not be Deleted!";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	

}
