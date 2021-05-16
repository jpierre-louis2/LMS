//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'book_copies' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.tables.BookCopies;


public class BookCopiesDAO extends BaseDAO<BookCopies>{
	
	//Supports Database Connection
	public BookCopiesDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of BookCopies based on the Select Statement sent
	public ArrayList<BookCopies> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<BookCopies> copies = new ArrayList<BookCopies>();
		while(rs.next()) {
			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int num = rs.getInt("noOfCopies");
			BookCopies copy = new BookCopies(bookId, branchId, num);
			copies.add(copy);
		}
		return copies;
	}
	//Returns an ArrayList of Books Copies available for a specified Library Branch
	public ArrayList<BookCopies>getAllAvailableCopies(int branchId) throws ClassNotFoundException, SQLException{
		ArrayList<BookCopies> copies = read("SELECT bookId, branchId, noOfCopies FROM tbl_book_copies WHERE branchId=? AND noOfCopies!=0", new Object[] {branchId});
		return copies;
	}
	//Returns an ArrayList of the copies for a specified book at a specified Library Branch
	public BookCopies getCopies(int bookId, int branchId) throws ClassNotFoundException, SQLException{
		ArrayList<BookCopies> copies = read("SELECT bookId, branchId, noOfCopies FROM tbl_book_copies WHERE bookId=? AND branchId=?", new Object[] {bookId, branchId});
		return copies.get(0);
	}
	//Returns an ArrayList of all BookCopies
	public ArrayList<BookCopies>getAllCopies() throws ClassNotFoundException, SQLException{
		ArrayList<BookCopies> copies = read("SELECT bookId, branchId, noOfCopies FROM tbl_book_copies", null);
		return copies;
	}
	//Inserts a new value into BookCopies Table
	public void addBookCopies(BookCopies copies) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?, ?, ?)", new Object[] {copies.getBookId(), copies.getBranchId(), copies.getCopies()});
	}
	//Updates BookCopies at a specified Branch for a specified book in BookCopies Table
	public void updateBookCopies(BookCopies copies) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_copies SET noOfCopies=? WHERE branchId=? AND bookId=?", new Object[] {copies.getCopies(), copies.getBranchId(), copies.getBookId()});
	}
	//Deletes BookCopies at a specified Branch for a specified book in BookCopies Table
	public void deleteBookCopies(int branchId, int bookId) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_copies WHERE branchId=? AND bookId=?", new Object[] {branchId, bookId});
	}


}
