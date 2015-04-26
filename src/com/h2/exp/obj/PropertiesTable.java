package com.h2.exp.obj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PropertiesTable {
	private static final String KEY = "key";
	private static final String VALUE = "value";
	private static String tableName = "properties";
	
	public PropertiesTable() {

	}
	
	public void add(String key, Object value) throws SQLException {
		if(update(key, value) == 0) {
			insert(key, value);
		}
	}
	
	public void insert(String key, Object value) throws SQLException {
		Connection connection = DBManager.get().getConnection();
		PreparedStatement pstmt = null;
		if(connection != null) {
			try {
				String insertQuery = "insert into " + tableName + "(" + KEY + ", " + VALUE + ") values(?, ?)";
				pstmt = connection.prepareStatement(insertQuery);
				pstmt.setString(1, key);
				pstmt.setObject(2, value, Types.OTHER);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw e;
			} finally {
				DBManager.get().close(connection, pstmt);
			}
		}
	}
	
	private int update(String key, Object value) throws SQLException {
		int rowsEffected = 0;
		Connection connection = DBManager.get().getConnection();
		PreparedStatement pstmt = null;
		if(connection != null) {
			try {
				String updateQuery = "update " + tableName + " set " + VALUE + "=? where " + KEY + "=?";
				pstmt = connection.prepareStatement(updateQuery);
				pstmt.setObject(1, value, Types.OTHER);
				pstmt.setString(2, key);
				rowsEffected = pstmt.executeUpdate();
			} catch (SQLException e) {
				throw e;
			} finally {
				DBManager.get().close(connection, pstmt);
			}
		}
		return rowsEffected;
	}

	public void remove(String key) throws SQLException {
		Connection connection = DBManager.get().getConnection();
		PreparedStatement pstmt = null;
		if(connection != null) {
			try {
				String removeQuery = "delete from " + tableName + " where " + KEY + "=?";
				pstmt = connection.prepareStatement(removeQuery);
				pstmt.setString(1, key);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw e;
			} finally {
				DBManager.get().close(connection, pstmt);
			}
		}
	}

	public Object get(String key) {
		Object value = null;
		String query = "select " + VALUE +  " from " + tableName +  " where " +  KEY + "=?";

		Connection connection = DBManager.get().getConnection();
		PreparedStatement pstmt = null;
		if(connection != null) {
			try {
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, key);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				value = rs.getObject(VALUE);
				rs.close();
			} catch (SQLException e) {

			} finally {
				DBManager.get().close(connection, pstmt);
			}
		}
		return value;
	}

	public void createTable() {
		DBManager.get().execute("drop table if exists " + tableName);
		
		String query = "create table " + tableName + "(" + KEY + " varchar(255) primary key," + VALUE + " other)";
		DBManager.get().execute(query);
	}
}
