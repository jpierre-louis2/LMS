//Author: Jameson Pierre-Louis
//This is a Data Object representing the 'book_copies' table of the Library Database
package com.ss.apr.jb.tables;

public class BookCopies {
	
	private int bookId;
	private int branchId;
	private int copies;
	
	public BookCopies(int book, int branch, int noCopy) {
		setBookId(book);
		setBranchId(branch);
		setCopies(noCopy);
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
	public int getCopies() {
		return copies;
	}
	public void setCopies(int copies) {
		this.copies = copies;
	}

}

