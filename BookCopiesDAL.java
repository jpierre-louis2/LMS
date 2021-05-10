//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BookCopiesDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;
import com.ss.apr.jb.BLL.UtilityFunctions;
import com.ss.apr.jb.DAO.*;
import com.ss.apr.jb.tables.Author;
import com.ss.apr.jb.tables.Book;
import com.ss.apr.jb.tables.BookCopies;

public class BookCopiesDAL {
	
	UtilityFunctions u = new UtilityFunctions();
	AuthorDAO au = new AuthorDAO();
	BookCopiesDAO bc = new BookCopiesDAO();
	BookDAO bk = new BookDAO();
	BookLoansDAO bl = new BookLoansDAO();
	BorrowerDAO br = new BorrowerDAO();
	BranchDAO bh = new BranchDAO();
	PublisherDAO pu = new PublisherDAO();
	
	//Returns the name of an Author
	private String getAuthorName(int id) {
		Author author = au.getAuthor(id);
		return author.getName();
	}
	//Returns a specific book
	private Book getBook(int id){
		Book book = bk.getBook(id);
		return book;
	}
	//Prints out all available book copies for a Branch
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
	//Returns an list of all available books at a Branch
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
	//Returns the amount of book copies in a branch
	public int findBookStatus(int bookId, int branchId) {
		BookCopies b = bc.getCopy(bookId, branchId);
		int num = 0;
		try { num = b.getCopies(); }
		catch(NullPointerException e) {}
		return num;
	}
	//Orders new books for a Branch
	public void orderBook(int bookId, int branchId, int num) {
		BookCopies b = null;
		Book book = getBook(bookId);
		//Update book total
		try {
			int total = findBookStatus(bookId, branchId);
			if(total > 0) {
				b = new BookCopies(bookId, branchId, (total + num));
				bc.updateCopies(b);
			}
			else {
				System.out.println("Adding new Book to branch");
				b = new BookCopies(bookId, branchId, num);
				bc.addCopies(b);
			}
		}
		catch(NullPointerException e){}
		System.out.print("\n" + num + " Copies of '" + book.getTitle() + "' Have been ordered");
		printAvailableBooks(branchId);
	}

}
