//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'book_loans' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.ss.apr.jb.tables.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans>{
	
	//Supports Database Connection
	public BookLoansDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of BookLoans based on the Select Statement sent
	public ArrayList<BookLoans> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<BookLoans> loans = new ArrayList<BookLoans>();
		while(rs.next()) {
			int book = rs.getInt("bookId");
			int branch = rs.getInt("branchId");
			int card = rs.getInt("cardNo");
			Date out = rs.getDate("dateOut");
			Date due = rs.getDate("dueDate");
			BookLoans loan = new BookLoans(book, branch, card, out, due);
			loans.add(loan);
		}
		return loans;
	}
	//Returns a BookLoan for a specified Book Id
	public BookLoans getLoan(int bookId, int branchId, int cardNo) throws ClassNotFoundException, SQLException{
		ArrayList<BookLoans> loans = read("SELECT bookId, branchId, cardNo, dateOut, dueDate FROM tbl_book_loans WHERE bookId=? AND branchId=? AND cardNo=?", new Object[] {bookId, branchId, cardNo});
		return loans.get(0);
	}
	//Returns an ArrayList of BookLoans belonging to the same cardNumber or Borrower
	public ArrayList<BookLoans>getBorrowerLoans(int branchId, int cardNo) throws ClassNotFoundException, SQLException{
		ArrayList<BookLoans> loans = read("SELECT bookId, branchId, cardNo, dateOut, dueDate FROM tbl_book_loans WHERE branchId=? AND cardNo=?", new Object[] {branchId, cardNo});
		return loans;
	}
	//Returns an ArrayList of all BookLoans
	public ArrayList<BookLoans>getAllLoans() throws ClassNotFoundException, SQLException{
		ArrayList<BookLoans> loans = read("SELECT bookId, branchId, cardNo, dateOut, dueDate FROM tbl_book_loans", null);
		return loans;
	}
	//Inserts a new BookLoan into database
	public void addLoan(BookLoans loan) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, ?, ?)", new Object[] {loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDateDue()});
	}
	//Updates the information for a BookLoan specified by Book Id, Branch Id, and Borrower Card Number
	public void updateLoan(BookLoans loan) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_loans SET dateOut=?, dueDate=? WHERE bookId=? AND branchId=? AND cardNo=?", new Object[] {loan.getDateOut(), loan.getDateDue(), loan.getBookId(), loan.getBranchId(), loan.getCardNo()});
	}
	//Deletes a BookLoan specified by Book Id, Branch Id, and Borrower Card Number
	public void deleteLoan(int bookId, int branchId, int cardNo) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_loans WHERE bookId=? AND branchId=? AND cardNo=?", new Object[] {bookId, branchId, cardNo});
	}

		
}