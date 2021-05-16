package com.ss.apr.jb.DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "root");
		//Sets soft commits incase of rollbacks
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}

}
