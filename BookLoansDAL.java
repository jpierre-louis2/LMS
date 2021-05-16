//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BookLoansDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.ss.apr.jb.DAO.*;
import com.ss.apr.jb.tables.*;

public class BookLoansDAL {
	
	Util u = new Util();
	
	//Returns an ArrayList of all BookCopies available (More than 1 copy) for a Branch specified by Branch Id
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
	
	//Prints out all available book copies for a branch
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
	//Returns an ArrayList of all Books available (More than 1 copy) for a Branch specified by Branch Id
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
	//Prints out all Books Loaned to a Borrower specified by Branch Id and Borrower Card Number/Id
	public void printLoanedBooks(int branchId, int cardNo) throws SQLException{
		ArrayList<Book> books = getBorrowedBooks(branchId, cardNo);
		BookDAL bDAL = new BookDAL();
		int count = 1;
		System.out.println("\nBooks Available for Return:");
		try {
			for(Book book : books) {
				System.out.println(count + ". " + book.getTitle() + " by " + bDAL.getAuthorName(book.getAuthId()));
				++count;
			}
		}catch(Exception e){
			System.out.println("No books for return!");
		}
	}	
	//Returns an ArrayList of all Books loaned to a Borrower from a Library Branch, specified by Branch Id and Borrower Card Number/Id
	public ArrayList<Book> getBorrowedBooks(int branchId, int cardNo) throws SQLException{
		ArrayList<Book> loanedBooks = new ArrayList<Book>();
		ArrayList<BookLoans> loaned = getBorrowerLoans(branchId, cardNo);
		BookDAL bDAL = new BookDAL();
		for(BookLoans b : loaned) {
			loanedBooks.add(bDAL.getBook(b.getBookId()));
		}
		return loanedBooks;
	}
	//Returns a BookLoan based on Book Id, Branch Id, and Borrower Card Number/Id
	public BookLoans getBookLoan(int bookId, int branchId, int cardNo) throws SQLException{
		Connection conn = null;
		BookLoans loan = null;
		try {
			conn = u.getConnection();
			BookLoansDAO bl = new BookLoansDAO(conn);
			loan = bl.getLoan(bookId, branchId, cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return loan;
	}
	//Returns an ArrayList of all BookLoans
	public ArrayList<BookLoans> getBookLoans() throws SQLException{
		Connection conn = null;
		ArrayList<BookLoans> loans = null;
		try {
			conn = u.getConnection();
			BookLoansDAO bl = new BookLoansDAO(conn);
			loans = bl.getAllLoans();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return loans;
	}
	//Returns an ArrayList of BorrowerLoans for a Borrower from a Branch specified by Branch Id and Borrower Card Number/Id
	public ArrayList<BookLoans> getBorrowerLoans(int branchId, int cardNo) throws SQLException{
		Connection conn = null;
		ArrayList<BookLoans> loans = null;
		try {
			conn = u.getConnection();
			BookLoansDAO bl = new BookLoansDAO(conn);
			loans = bl.getBorrowerLoans(branchId, cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		return loans;
	}
	//Updates a BookLoan's information
	public void updateLoan(BookLoans loan) throws SQLException{
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookLoansDAO bl = new BookLoansDAO(conn);
			bl.updateLoan(loan);
			conn.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Loans out a Book to a Borrower specified by Book Id, Branch Id, and Borrower Card Number/Id
	//Removes a Copy from the branch and updates BookCopies
	//Adds a new Book Loan to table
	public void loanBook(int bookId, int branchId, int cardNo) throws SQLException{
		Calendar calendar = Calendar.getInstance();
		Date out = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		Date due = calendar.getTime();
		BookLoans loan = new BookLoans(bookId, branchId, cardNo, out, due);
		
		//Checks to see if the user has already checked out the same book from the same branch
		if(loanStatus(loan)) {
			System.out.println("Error: Loan already exists!");
		}
		else {
			int total = findBookStatus(bookId, branchId);
			if (total != 0) {
				BookCopies b = new BookCopies(bookId, branchId, (total - 1));
				Connection conn = null;
				try {
					conn = u.getConnection();
					BookCopiesDAO bc = new BookCopiesDAO(conn);
					BookLoansDAO bl = new BookLoansDAO(conn);
					bc.updateBookCopies(b);
					bl.addLoan(loan);
					conn.commit();
					System.out.println("Book Successfully checked out! " + out);
					System.out.println("This book is due back: " + due);
					printAvailableBooks(branchId);
				}
				catch(Exception e) {
					e.printStackTrace();
					conn.rollback();
					System.out.println("Book Could not be Checked Out!");
				}finally {
					if (conn != null) {
						conn.close();
					}
				}
			}
		}
	}
	//Checks to see if a loan already exists for a user, for a book at a specific branch, so no duplicates
	public boolean loanStatus(BookLoans test) throws SQLException{
		ArrayList <BookLoans> loans = getBookLoans();
		for (BookLoans loan : loans) {
			if(test.getBookId() == loan.getBookId()) {
				if(test.getBranchId() == loan.getBranchId()) {
					if(test.getCardNo() == loan.getCardNo())
						return true;
					}
				}
			}
			
		return false;
	}
	//Returns a book to Library Branch specified by Book Id, Branch Id and Borrower Card Number/Id
	//Adds a Book Copy to Branch and updates Book Copies
	//Deletes the Loan Entry from Loan Table
	public String returnBook(int bookId, int branchId, int cardNo) throws SQLException{
		int total = findBookStatus(bookId, branchId);
		BookCopies b = new BookCopies(bookId, branchId, (total + 1));
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookCopiesDAO bc = new BookCopiesDAO(conn);
			BookLoansDAO bl = new BookLoansDAO(conn);
			bc.updateBookCopies(b);
			bl.deleteLoan(bookId, branchId, cardNo);
			conn.commit();
			return "\nBook Successfully Returned!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "\nBook could not be Returned!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
		
	}
	//Returns the amount of BookCopies for a Branch specified by Book Id and Branch Id
	public int findBookStatus(int bookId, int branchId) throws SQLException{
		BookCopies b = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookCopiesDAO bc = new BookCopiesDAO(conn);
			b= bc.getCopies(bookId, branchId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		int num = 0;
		try { 
			num = b.getCopies(); 
		}
		catch(NullPointerException e) {}
		
		return num;
	}

}
