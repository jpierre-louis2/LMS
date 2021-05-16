//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for PublisherDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.apr.jb.DAO.BookDAO;
import com.ss.apr.jb.DAO.PublisherDAO;
import com.ss.apr.jb.tables.Publisher;

public class PublisherDAL {
	
	Util u = new Util();


	//Prints out a list of all Publishers
	public void printPublishers() throws SQLException{
		ArrayList<Publisher> publishers = getPublishers();
		int count = 1;
		System.out.println("\nPublishers:");
		for(Publisher publisher : publishers) {
			System.out.println(count + ". " + publisher.getName() + ", Address: " + publisher.getAddress() + " Phone: " + publisher.getPhoneNumber());
			++count;
		}
	}
	//Returns the name of a publisher specified by Publisher Id
	public String getPublisherName(int id) throws SQLException{
		Publisher publisher = getPublisher(id);
		return publisher.getName();
	}
	//Returns an ArrayList of all Publishers
	public ArrayList<Publisher> getPublishers() throws SQLException{
		ArrayList<Publisher> publishers = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			PublisherDAO pu = new PublisherDAO(conn);
			publishers = pu.getAllPublishers();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		
		return publishers;
	}
	//Returns a publisher specified by Publisher Id
	public Publisher getPublisher(int id) throws SQLException{
		Publisher publisher = null;
		Connection conn = null;
		try {
			conn = u.getConnection();
			PublisherDAO pu = new PublisherDAO(conn);
			publisher = pu.getPublisher(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}
		
		return publisher;
	}
	//Returns a new ID to be used for adding a new Publisher
	//Based on auto-increment
	private int findNewPublisherId() throws SQLException{
		ArrayList<Publisher> publishers = getPublishers(); 
		if (publishers.size() == 0) {
			return 1;
		}
		int highest = publishers.get(0).getId();
		for(Publisher publisher : publishers) {
			if (publisher.getId() > highest)
				highest = publisher.getId();
		}
		
		return highest + 1;
	}	
	//Adds a new Publisher to Database
	public String addPublisher(String name, String address, String phone) throws SQLException{
		Connection conn = null;
		int newId = findNewPublisherId();
		Publisher publisher = new Publisher(newId, name, address, phone);
		try {
			conn = u.getConnection();
			PublisherDAO pu = new PublisherDAO(conn);
			pu.addPublisher(publisher);
			conn.commit();
			printPublishers();
			return "Publisher Successfully Added!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Publisher Could not be Added!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Updates a Publisher specified by Publisher Id
	public String updatePublisher(int pId, String name, String address, String phone) throws SQLException{
		Connection conn = null;
		Publisher publisher = new Publisher(pId, name, address, phone);
		try {
			conn = u.getConnection();
			PublisherDAO pu = new PublisherDAO(conn);
			pu.updatePublisher(publisher);;
			conn.commit();
			printPublishers();
			return "Publisher Successfully Updated!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Publisher Could not be Updated!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	//Deletes a Publisher from Database specified by Publisher Id
	//Also sets all Books with Publisher Ids associated to the Publisher's Id to 0
	public String deletePublisher(int id) throws SQLException{
		Connection conn = null;
		try {
			conn = u.getConnection();
			BookDAO b = new BookDAO(conn);
			PublisherDAO pu = new PublisherDAO(conn);
			b.updatePublisherBook(id);
			pu.deletePublisher(id);
			conn.commit();
			printPublishers();
			return "Publisher Successfully Deleted!";
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Publisher Could not be Deleted!";
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

}
