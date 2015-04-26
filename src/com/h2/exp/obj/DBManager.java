package com.h2.exp.obj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbcx.JdbcConnectionPool;

public class DBManager {
	private static DBManager dbManager;
	private final JdbcConnectionPool connectionPool;
	private DBManager() {
		String uri = "jdbc:h2:~/test";
		connectionPool = JdbcConnectionPool.create(uri, "test", "test@123");
	}
	
	public static DBManager get() {
		if(dbManager == null) {
			synchronized (DBManager.class) {
				if(dbManager == null) {
					dbManager = new DBManager();
				}
			}
		}
		return dbManager;
	}
	
	public Connection getConnection() {
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void execute(String qeury) {
		try {
			Connection conn = dbManager.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(qeury);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(Connection connection, PreparedStatement pstmt) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
			
			if(pstmt != null && !pstmt.isClosed()) {
				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		connectionPool.dispose();
	}
}
