//Author: Jameson Pierre-Louis
//This is a Data Access Object which pulls information from the 'publisher' table in the Library Database
package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.tables.Publisher;

public class PublisherDAO extends BaseDAO<Publisher>{

	//Supports Database Connection
	public PublisherDAO(Connection conn) {
		super(conn);
	}
	//Returns an ArrayList of Publishers based on the Select Statement sent
	public ArrayList<Publisher> extractData(ResultSet rs) throws ClassNotFoundException, SQLException{
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		while(rs.next()) {
			int id = rs.getInt("publisherId");
			String name = rs.getString("publisherName");
			String address = rs.getString("publisherAddress");
			String phone = rs.getString("publisherPhone");
			Publisher publisher = new Publisher(id, name, address, phone);
			publishers.add(publisher);	
		}
		return publishers;
	}
	//Returns the publisher specified by Publisher Id
	public Publisher getPublisher(int id) throws ClassNotFoundException, SQLException{
		ArrayList<Publisher> publishers = read("SELECT publisherId, publisherName, publisherAddress, publisherPhone FROM tbl_publisher WHERE publisherId=?", new Object[] {id});
		return publishers.get(0);
	}
	//Returns an ArrayList of all Publishers
	public ArrayList<Publisher>getAllPublishers() throws ClassNotFoundException, SQLException{
		ArrayList<Publisher> publishers = read("SELECT publisherId, publisherName, publisherAddress, publisherPhone FROM tbl_publisher WHERE publisherId!=0", null);
		return publishers;
	}
	//Inserts a new publisher into Publisher table in database
	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_publisher (publisherId, publisherName, publisherAddress, publisherPhone) VALUES (?, ?, ?, ?)", new Object[] {publisher.getId(), publisher.getName(), publisher.getAddress(), publisher.getPhoneNumber()});
	}
	//Updates a publisher specified by Publisher Id in database
	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_publisher SET publisherName=?, publisherAddress=?, publisherPhone=? WHERE publisherId=?", new Object[] {publisher.getName(), publisher.getAddress(), publisher.getPhoneNumber(), publisher.getId()});
	}
	//Deletes a publisher specified by Id in database
	public void deletePublisher(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_publisher WHERE publisherId=?", new Object[] {id});
	}
	
}
