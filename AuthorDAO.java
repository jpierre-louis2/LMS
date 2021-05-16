//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'author' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Author;

public class AuthorDAO extends BaseDAO<Author>{
	//Supports Database Connection
	public AuthorDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of Authors based on the Select Statement sent
	public ArrayList<Author> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<Author> authors = new ArrayList<Author>();
		while(rs.next()) {
			int id = rs.getInt("authorId");
			String name = rs.getString("authorName");
			Author author = new Author(id, name);
			authors.add(author);
		}
		return authors;
	}
	//Returns the Author specified by Author Id
	public Author getAuthor(int id) throws ClassNotFoundException, SQLException{
		ArrayList<Author> authors = read("SELECT authorId, authorName FROM tbl_author WHERE authorId=?", new Object[] {id});
		return authors.get(0);
	}
	//Returns an ArrayList of all Authors in Author Table
	public ArrayList<Author>getAllAuthors() throws ClassNotFoundException, SQLException{
		ArrayList<Author> authors = read("SELECT authorId, authorName FROM tbl_author WHERE authorId!=0", null);
		return authors;
	}
	//Inserts a new Author into database
	public void addAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_author (authorId, authorName) VALUES (?, ?)", new Object[] {author.getId(), author.getName()});
	}
	//Updates the information for an Author specified by Author Id
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_author SET authorName=? WHERE authorId=?", new Object[] {author.getName(), author.getId()});
	}
	//Deletes the Author specified by Author Id
	public void deleteAuthor(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_author WHERE authorId=?", new Object[] {id});
	}

}