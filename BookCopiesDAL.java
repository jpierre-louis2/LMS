//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BookCopiesDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.*;
import com.ss.apr.jb.tables.Book;
import com.ss.apr.jb.tables.BookCopies;

public class BookCopiesDAL {
	
	Util u = new Util();
	
	
	//Prints out all available Books and Copy amounts specified by Branch Id
	public void printAvailableBooks(int branchId) throws SQLException{
		ArrayList<Book> books = getAvailableBooks(branchId);
		ArrayList<BookCopies> copies = getAvailableBookCopies(branchId);
		BookDAL bDAL = new BookDAL();
		int count = 1;
		System.out.println("\nBooks Available for Checkout:");
		for(Book book : books) {
			int amount = copies.get(count-1).getCopies();
			System.out.println(count + ". " + book.getTitle() + " by " + bDAL.getAuthorName(book.getAuthId()) + " (" + amount + ")");
			++count;
		}
	}
	//Returns an ArrayList of BookCopies at a branch for Books specified by Branch Id
	public ArrayList<BookCopies> getAvailableBookCopies(int branchId) throws SQLException{
		ArrayList<BookCopies> availableBooks = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookCopiesDAO bc = new BookCopiesDAO(conn);
			availableBooks = bc.getAllAvailableCopies(branchId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		
		return availableBooks;
	}
	//Returns an ArrayList of all books available at a Branch specified by Branch Id
	public ArrayList<Book> getAvailableBooks(int branchId) throws SQLException{
		ArrayList<Book> books = new ArrayList<Book>();
		ArrayList<BookCopies> availableBooks = getAvailableBookCopies(branchId);
		BookDAL bDAL = new BookDAL();
		for(BookCopies b : availableBooks) {
			//Checks for more than 1 book at branch
			if (b.getCopies() != 0) {
				books.add(bDAL.getBook(b.getBookId()));
			}
		}
		return books;
	}
	//Returns the amount of book copies in a branch
	public int findBookStatus(int bookId, int branchId) throws SQLException{
		BookCopies b = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookCopiesDAO bc = new BookCopiesDAO(conn);
			b = bc.getCopies(bookId, branchId);
		} catch (Exception e) {
			//No Print condition
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		
		int num = 0;
		try { num = b.getCopies(); }
		catch(NullPointerException e) {}
		return num;
	}
	//Orders new books for a Branch specified by Book Id, Branch Id, and amount to order
	public void orderBook(int bookId, int branchId, int num) throws SQLException{
		BookCopies b = null;
		Connection conn = null;
		BookDAL bDAL = new BookDAL();
		Book book = bDAL.getBook(bookId);
		int total = findBookStatus(bookId, branchId);
		
		//Checks whether to add a new Entry into Book Copies or Edit an existing one
		if(total > 0) {
			b = new BookCopies(bookId, branchId, (total + num));
			try {
				conn = u.getConnection();
				BookCopiesDAO bc = new BookCopiesDAO(conn);
				bc.updateBookCopies(b);
				conn.commit();
				System.out.print("\n" + num + " Copies of '" + book.getTitle() + "' Have been ordered");
				printAvailableBooks(branchId);
			}
			catch(Exception e) {
				e.printStackTrace();
				conn.rollback();
				System.out.println("Books could not be ordered!");
			}finally {
				if (conn != null) {
					conn.close();
				}
			}
		}
		else {
			System.out.println("Adding new Book to branch");
			b = new BookCopies(bookId, branchId, num);
			try {
				conn = u.getConnection();
				BookCopiesDAO bc = new BookCopiesDAO(conn);
				bc.addBookCopies(b);
				conn.commit();
				System.out.print("\n" + num + " Copies of '" + book.getTitle() + "' Have been ordered");
				printAvailableBooks(branchId);
			}catch(Exception e) {
				e.printStackTrace();
				conn.rollback();
				System.out.println("Books could not be ordered!");
			}finally {
				if (conn != null) {
					conn.close();
				}
			}
		}
	}

}
