//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BookDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.AuthorDAO;
import com.ss.apr.jb.DAO.BookDAO;
import com.ss.apr.jb.tables.Author;
import com.ss.apr.jb.tables.Book;


public class BookDAL {
	
	Util u = new Util();
	
	//Returns the name of an Author specified by Author Id
	public String getAuthorName(int id) throws SQLException{
		Connection conn = null;
		Author author = null;
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
		return author.getName();
	}
	//Prints out all Books
	public void printBooks() throws SQLException{
		ArrayList<Book> books = getBooks();
		int count = 1;
		System.out.println("\nBooks:");
		for(Book book : books) {
			System.out.println(count + ". " + book.getTitle() + " by " + getAuthorName(book.getAuthId()));
			++count;
		}
	}
	//Returns a Book specified by Book Id
	public Book getBook(int id) throws SQLException{
		Book book = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookDAO bk = new BookDAO(conn);
			book = bk.getBook(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return book;
	}
	//Returns an ArrayList of all Books
	public ArrayList<Book> getBooks() throws SQLException{
		Connection conn = null;
		ArrayList<Book> books = null;
		try {
			conn = u.getConnection();
			BookDAO bk = new BookDAO(conn);
			books = bk.getAllBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return books;
	}
	//Generates new Auto-Increment ID for adding a new book
	public int findNewBookId() throws SQLException{
		ArrayList<Book> books = getBooks(); 
		if (books.size() == 0) {
			return 1;
		}
		int highest = books.get(0).getId();
		for(Book book : books) {
			if (book.getId() > highest)
				highest = book.getId();
		}
		
		return highest + 1;
	}
	//Adds a new Book into database
	public String addBook(String title, int aId, int pId) throws SQLException{
		Connection conn = null;
		int newId = findNewBookId();
		Book book = new Book(newId, title, aId, pId);
		try {
			conn = u.getConnection();
			BookDAO bk = new BookDAO(conn);
			bk.addBook(book);
			conn.commit();
			printBooks();
			return "Book Successfully Added!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book Could not be Added!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Updates an existing Book's information based on Book Id
	public String updateBook(int bId, String title, int aId, int pId) throws SQLException{
		Book book = new Book(bId, title, aId, pId);
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookDAO bk = new BookDAO(conn);
			bk.updateBook(book);
			conn.commit();
			printBooks();
			return "Book Successfully Updated!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book Could not be Updated!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Deletes a Book from Database based on Book Id
	public String deleteBook(int id) throws SQLException{
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookDAO bk = new BookDAO(conn);
			bk.deleteBook(id);
			conn.commit();
			printBooks();
			return "Book Successfully Deleted!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book Could not be Deleted!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

}
