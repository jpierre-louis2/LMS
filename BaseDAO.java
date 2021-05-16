package com.ss.apr.jb.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class BaseDAO<T> {
	
	Connection conn = null;
	public BaseDAO(Connection conn) {
		this.conn = conn;
	}
	//This function performs add and update operations
	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int count = 1;
		for(Object o: vals) {
			pstmt.setObject(count, o);
			count++;
		}
		pstmt.executeUpdate(); 
	}
	//This function performs read functions (Get & Get All)
	public ArrayList<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int count = 1;
		if (vals != null) {
			for(Object o: vals) {
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	abstract public ArrayList<T> extractData(ResultSet rs) throws ClassNotFoundException, SQLException;
}
