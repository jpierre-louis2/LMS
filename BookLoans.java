//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'book_loans' table of the Library Database
package com.ss.apr.jb.tables;
import java.util.Date;

public class BookLoans {

	private int bookId;
	private int branchId;
	private int cardNo;
	private Date dateOut;
	private Date dateDue;
	
	
	public BookLoans(int book, int branch, int card, Date dOut, Date dDue) {
		setBookId(book);
		setBranchId(branch);
		setCardNo(card);
		setDateOut(dOut);
		setDateDue(dDue);
	}
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Date getDateDue() {
		return dateDue;
	}
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}
	
}