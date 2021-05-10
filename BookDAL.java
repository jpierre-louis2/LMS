//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for BookDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.AuthorDAO;
import com.ss.apr.jb.DAO.BookDAO;
import com.ss.apr.jb.tables.Author;
import com.ss.apr.jb.tables.Book;


public class BookDAL {
	
	BookDAO bk = new BookDAO();
	AuthorDAO au = new AuthorDAO();
	
	//Returns the name of an Author
	private String getAuthorName(int id) {
		Author author = au.getAuthor(id);
		return author.getName();
	}
	//Prints out all books
	public void printBooks() {
		ArrayList<Book> books = getBooks();
		int count = 1;
		System.out.println("\nBooks:");
		for(Book book : books) {
			System.out.println(count + ". " + book.getTitle() + " by " + getAuthorName(book.getAuthId()));
			++count;
		}
	}
	//Returns a specific book
	public Book getBook(int id){
		Book book = bk.getBook(id);
		return book;
	}
	//Returns an list of all books
	public ArrayList<Book> getBooks(){
		ArrayList<Book> books = bk.getBooks();
		return books;
	}
	//Generates new Auto-Increment ID for adding a new book
	public int findNewBookId() {
		ArrayList<Book> books = bk.getBooks(); 
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
	//Adds a new book to Database
	public void addBook(String title, int aId, int pId) {
		int newId = findNewBookId();
		Book book = new Book(newId, title, aId, pId);
		bk.addBook(book);
	}
	//Updates an existing book's information
	public void updateBook(int bId, String title, int aId, int pId) {
		Book book = new Book(bId, title, aId, pId);
		bk.updateBook(book);
	}
	//Deletes a book from Database
	public void deleteBook(int id) {
		bk.deleteBook(id);
	}

}
