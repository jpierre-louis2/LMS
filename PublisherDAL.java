//Author: Jameson Pierre-Louis
//This is a Data Access Level which processes information for PublisherDAO (Data Access Object)
package com.ss.apr.jb.DAL;
import java.util.ArrayList;

import com.ss.apr.jb.DAO.BookDAO;
import com.ss.apr.jb.DAO.PublisherDAO;
import com.ss.apr.jb.tables.Publisher;

public class PublisherDAL {
	
	PublisherDAO pu = new PublisherDAO();
	BookDAO b = new BookDAO();

	//Prints out a list of all publishers
	public void printPublishers() {
		ArrayList<Publisher> publishers = getPublishers();
		int count = 1;
		System.out.println("\nPublishers:");
		for(Publisher publisher : publishers) {
			System.out.println(count + ". " + publisher.getName());
			++count;
		}
	}
	//Returns an list of all publishers
	public ArrayList<Publisher> getPublishers(){
		ArrayList<Publisher> publishers = pu.getPublishers();
		return publishers;
	}
	//Returns the name of the publisher at a specific ID
	public String getPublisherName(int id) {
		Publisher publisher = pu.getPublisher(id);
		return publisher.getName();
	}
	//Returns the publisher at specified ID
	public Publisher getPublisher(int id) {
		Publisher publisher = pu.getPublisher(id);
		return publisher;
	}
	//Deletes a Publisher from Database
	public void deletePublisher(int id) {
		b.updatePublisherBooks(id);
		pu.deletePublisher(id);
	}
	//Adds a new publisher to Database
	public void addPublisher(String name, String address, String phone) {
		int newId = findNewPublisherId();
		Publisher publisher = new Publisher(newId, name, address, phone);
		pu.addPublisher(publisher);
	}
	//Updates a publisher with requested information
	public void updatePublisher(int pId, String name, String address, String phone) {
		Publisher publisher = new Publisher(pId, name, address, phone);
		pu.updatePublisher(publisher);
	}
	//Returns a new ID to be used for adding a new Publisher
	//Based on auto-increment
	private int findNewPublisherId() {
		ArrayList<Publisher> publishers = pu.getPublishers(); 
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
	


}
