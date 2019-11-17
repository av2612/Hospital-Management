package io.swagger.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.json.JSONObject;

public class DataBaseImpl implements Database {
//	private static DataBase dbInstance = null;
	private Connection myConn = null;
	@Context
	private ServletContext context;
	private static final String host = "jdbc:mysql://localhost:3306/mydb";
	private static final String user = "aakash";
	private static final String password = "aakash";

	public void getConnection() {
		try {
			this.myConn = DriverManager.getConnection(host, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<HashMap<String, String>> runGetQuery(String mQuery, Object[] listOfParamsValue)
			throws SQLException {
		ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
		ResultSet mResultSet = null;
		PreparedStatement mStatement = null;
		try {
			this.getConnection();

			mStatement = this.myConn.prepareStatement(mQuery);
			for (int i = 1; i <= listOfParamsValue.length; i++) {
				if (listOfParamsValue[i - 1] instanceof Integer)
					mStatement.setInt(i, (Integer) listOfParamsValue[i - 1]);
				else if (listOfParamsValue[i - 1] instanceof Long)
					mStatement.setLong(i, (Long) listOfParamsValue[i - 1]);
				else if (listOfParamsValue[i - 1] instanceof Date)
					mStatement.setDate(i, (Date) listOfParamsValue[i - 1]);
				else
					mStatement.setString(i, (String) listOfParamsValue[i - 1]);
			}
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				HashMap<String, String> row = new HashMap<>();
				for (int i = 1; i < mResultSet.getMetaData().getColumnCount() + 1; i++) {
					row.put(mResultSet.getMetaData().getColumnName(i), mResultSet.getString(i));
				}
				resultList.add(row);
			}
			mStatement.close();
			return resultList;
		} catch (Exception msg) {
			System.out.println("asnsn--" + msg.toString());
			msg.toString();
			return resultList;
		} finally {
			mResultSet.close();
			this.myConn.close();
		}
	}

	public int runPostQuery(String mQuery, Object[] listOfParamsValue) throws SQLException {
		try {
			this.getConnection();
			String updatedMQuery = new String(mQuery);
			String[] returnId = { "id" };
			PreparedStatement mStatement = this.myConn.prepareStatement(updatedMQuery, returnId);
			for (int i = 1; i <= listOfParamsValue.length; i++) {
				if (listOfParamsValue[i - 1] instanceof Integer)
					mStatement.setInt(i, (Integer) listOfParamsValue[i - 1]);
				else if (listOfParamsValue[i - 1] instanceof Long)
					mStatement.setLong(i, (Long) listOfParamsValue[i - 1]);
				else if (listOfParamsValue[i - 1] instanceof Date)
					mStatement.setDate(i, (Date) listOfParamsValue[i - 1]);
				else if (listOfParamsValue[i-1] instanceof JSONObject)
					mStatement.setObject(i, listOfParamsValue[i-1]);
				else
					mStatement.setString(i, (String) listOfParamsValue[i - 1]);
				
			}

			mStatement.executeUpdate();
			ResultSet generatedKeySet = mStatement.getGeneratedKeys();
			int id = 0;
			while (generatedKeySet.next())
				id = generatedKeySet.getInt(1);
			return id;
		} finally {
			this.myConn.close();
		}
	}

	public int runPutQuery(String mQuery, String[] keyList, Object[] listOfParamsValue) throws SQLException {
		this.getConnection();
		String updatedMQuery = new String(mQuery);

		ArrayList<String> updatedKeyList = new ArrayList<String>();
		ArrayList<Object> updatedListOfParamsValue = new ArrayList<Object>();
		for (int i = 0; i < keyList.length; i++) {
			if (listOfParamsValue[i] != null) {
				updatedKeyList.add(keyList[i]);
				updatedListOfParamsValue.add(listOfParamsValue[i]);
			}
		}
		for (int i = keyList.length; i < listOfParamsValue.length; i++) {
			updatedListOfParamsValue.add(listOfParamsValue[i]);
		}
		String keyValues = "";
		for (String columnName : updatedKeyList) {
			keyValues = keyValues + String.format("%s = ? ,", columnName);
		}
		keyValues = keyValues.substring(0, keyValues.length() - 1);
		updatedMQuery = String.format(updatedMQuery, keyValues);
		PreparedStatement mStatement = this.myConn.prepareStatement(updatedMQuery);

		for (int i = 1; i <= updatedListOfParamsValue.size(); i++) {
			if (updatedListOfParamsValue.get(i - 1) instanceof Integer)
				mStatement.setInt(i, (Integer) updatedListOfParamsValue.get(i - 1));
			else if (updatedListOfParamsValue.get(i - 1) instanceof Long)
				mStatement.setLong(i, (Long) updatedListOfParamsValue.get(i - 1));
			else if (updatedListOfParamsValue.get(i - 1) instanceof Date)
				mStatement.setDate(i, (Date) updatedListOfParamsValue.get(i - 1));
			else
				mStatement.setString(i, (String) updatedListOfParamsValue.get(i - 1));
		}

		return mStatement.executeUpdate();
	}

	public void close() {
		try {
			this.myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}