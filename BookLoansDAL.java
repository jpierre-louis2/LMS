//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BookLoansDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.ss.apr.jb.BLL.UtilityFunctions;
import com.ss.apr.jb.DAO.*;
import com.ss.apr.jb.tables.*;

public class BookLoansDAL {
	
	UtilityFunctions u = new UtilityFunctions();
	AuthorDAO au = new AuthorDAO();
	BookCopiesDAO bc = new BookCopiesDAO();
	BookDAO bk = new BookDAO();
	BookLoansDAO bl = new BookLoansDAO();
	BorrowerDAO br = new BorrowerDAO();
	BranchDAO bh = new BranchDAO();
	PublisherDAO pu = new PublisherDAO();
	
	//Returns the name of an Author
	public String getAuthorName(int id) {
		Author author = au.getAuthor(id);
		return author.getName();
	}
	//Prints out all available book copies for a branch
	public void printAvailableBooks(int branchId) {
		ArrayList<Book> books = getAvailableBooks(branchId);
		ArrayList<BookCopies> copies = bc.getAvailableCopy(branchId);
		int count = 1;
		System.out.println("\nBooks Available for Checkout:");
		for(Book book : books) {
			int amount = copies.get(count-1).getCopies();
			System.out.println(count + ". " + book.getTitle() + " by " + getAuthorName(book.getAuthId()) + " (" + amount + ")");
			++count;
		}
	}
	//Returns an list of all books available for a Branch
	public ArrayList<Book> getAvailableBooks(int branchId){
		ArrayList<Book> books = new ArrayList<Book>();
		ArrayList<BookCopies> availableBooks = bc.getAvailableCopy(branchId);
		
		for(BookCopies b : availableBooks) {
			//Checks for more than 1 book at branch
			if (b.getCopies() != 0) {
				books.add(bk.getBook(b.getBookId()));
			}
		}
		
		return books;
	}
	//Prints out all books loaned to a user at a specific Branch
	public void printLoanedBooks(int branchId, int cardNo) {
		ArrayList<Book> books = getBorrowedBooks(branchId, cardNo);
		int count = 1;
		System.out.println("\nBooks Available for Return:");
		try {
			for(Book book : books) {
				System.out.println(count + ". " + book.getTitle() + " by " + getAuthorName(book.getAuthId()));
				++count;
			}
		}catch(Exception e){
			System.out.println("No books for return!");
		}
	}	
	//Returns an list of all books loaned books at a specific Branch
	public ArrayList<Book> getBorrowedBooks(int branchId, int cardNo){
		ArrayList<Book> loanedBooks = new ArrayList<Book>();
		ArrayList<BookLoans> loaned = bl.getBorrowerLoans(branchId, cardNo);
		for(BookLoans b : loaned) {
			loanedBooks.add(bk.getBook(b.getBookId()));
		}
		return loanedBooks;
	}
	//Returns a loan based on book, branch, and user
	public BookLoans getBookLoan(int bookId, int branchId, int cardNo) {
		BookLoans loan = bl.getBookLoan(bookId, branchId, cardNo);
		return loan;
	}
	//Updates a loan's information
	public void updateLoan(BookLoans loan) {
		bl.updateLoan(loan);
	}
	//Loans out a book to desired user at desired branch
	public void loanBook(int bookId, int branchId, int cardNo) {
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
			bl.addLoan(loan);
			int total = findBookStatus(bookId, branchId);
			if (total != 0) {
				BookCopies b = new BookCopies(bookId, branchId, (total - 1));
				bc.updateCopies(b);
				System.out.println("Book Successfully checked out! " + out);
				System.out.println("This book is due back: " + due);
				printAvailableBooks(branchId);
			}
		}
	}
	//Checks to see if a loan already exists for a user, for a book at a specific branch, so no duplicates
	public boolean loanStatus(BookLoans test) {
		ArrayList <BookLoans> loans = bl.getBookLoans();
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
	//Returns a book to library branch for a user
	public void returnBook(int bookId, int branchId, int cardNo) {
		System.out.println("Returning Book!");
		//Get total amount of book copies at the branch
		int total = findBookStatus(bookId, branchId);
		//Update the amount of book copies by 1
		BookCopies b = new BookCopies(bookId, branchId, (total + 1));
		bc.updateCopies(b);
		//Remove the loan entry
		bl.deleteLoan(bookId, branchId, cardNo);
	}
	//Returns the amount of book copies in a branch
	public int findBookStatus(int bookId, int branchId) {
		BookCopies b = bc.getCopy(bookId, branchId);
		int num = 0;
		
		try { 
			num = b.getCopies(); 
		}
		catch(NullPointerException e) {}
		
		return num;
	}

}
