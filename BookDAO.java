//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'book' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Book;

public class BookDAO extends BaseDAO<Book>{
	//Supports Database Connection
	public BookDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of Books based on the Select Statement sent
	public ArrayList<Book> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<Book> books = new ArrayList<Book>();
		while(rs.next()) {
			int id = rs.getInt("bookId");
			String title = rs.getString("title");
			int authId = rs.getInt("authId");
			int pubId = rs.getInt("pubId");
			Book book = new Book(id, title, authId, pubId);
			books.add(book);
		}
		return books;
	}
	//Returns an ArrayList of all Books
	public ArrayList<Book>getAllBooks() throws ClassNotFoundException, SQLException{
		ArrayList<Book> books = read("SELECT bookId, title, authId, pubId FROM tbl_book", null);
		return books;
	}
	//Returns a Book specified by Book Id
	public Book getBook(int id) throws ClassNotFoundException, SQLException{
		ArrayList<Book> books = read("SELECT bookId, title, authId, pubId FROM tbl_book WHERE bookId=?", new Object[] {id});
		return books.get(0);
	}
	//Inserts a new Book into Book table in database
	public void addBook(Book book) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book (bookId, title, authId, pubId) VALUES (?, ?, ?, ?)", new Object[] {book.getId(), book.getTitle(), book.getAuthId(), book.getPubId()});
	}
	//Updates a book in database based on Book Id
	public void updateBook(Book book) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book SET title=?, authId=?, pubId=? WHERE bookId=?", new Object[] {book.getTitle(), book.getAuthId(), book.getPubId(), book.getId()});
	}
	//-Used During Publisher Deletion- Sets User to an unknown publisher, updates all Books related to that publisher
	public void updatePublisherBook(int pId) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book SET pubId=0 WHERE pubId=?", new Object[] {pId});
	}
	//-Used During Author Deletion- Sets User to an unknown author, updates all books related to that author
	public void updateAuthorBook(int aId) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book SET authId=0 WHERE authId=?", new Object[] {aId});
	}
	//Deletes a Book from database
	public void deleteBook(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book WHERE bookId=?", new Object[] {id});
	}

}

