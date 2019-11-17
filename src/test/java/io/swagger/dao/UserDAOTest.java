package io.swagger.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.model.Patient;
import io.swagger.model.User;

public class UserDAOTest {
//	UserDAO udaoObj = new UserDAO();
	Database db = Mockito.mock(Database.class);
	PatientDAO pdaoObj = Mockito.mock(PatientDAO.class);

	UserDAO udaoObj = new UserDAO(db, pdaoObj);

	@SuppressWarnings("deprecation")
	public User setDefaultUser(int id) {
		User userObj = new User();
		userObj.setUserId(id);
		userObj.setUserName("aakash" + id);
		userObj.setFirstName("aakash" + id);
		userObj.setUserEmail("a.v2612" + id + "@gmail.com");
		userObj.setDateOfBirth("1993-12-26 18:30:00");
		return userObj;
	}

	public HashMap<String, String> getSQLGETUserResponse(int id) {
		HashMap<String, String> userRespObj = new HashMap<String, String>();
		userRespObj.put("id", "" + id);
		userRespObj.put("userName", "aakash" + id);
		userRespObj.put("firstName", "aakash" + id);
		userRespObj.put("email", "a.v2612" + id + "@gmail.com");
		userRespObj.put("dateOfBirth", "1993-12-26 18:30:00");
		return userRespObj;
	}

	@Test
	public void testGetUserListDataWithId() throws SQLException {
		ArrayList<User> listOfUsers = new ArrayList<User>();
		listOfUsers.add(this.setDefaultUser(1));
		Object[] listOfParamsValue = { (long) 1 };
		ArrayList<HashMap<String, String>> getUserResponse = new ArrayList<HashMap<String, String>>();
		getUserResponse.add(getSQLGETUserResponse(1));
		Mockito.when(db.runGetQuery(GetSqlQueries.SQLGETUser, listOfParamsValue)).thenReturn(getUserResponse);
		assertEquals(listOfUsers, udaoObj.getUserListData(null, (long) 1, null));
	}

	@Test
	public void testGetUserListDataWithName() throws SQLException {
		ArrayList<User> listOfUsers = new ArrayList<User>();
		listOfUsers.add(this.setDefaultUser(1));
		listOfUsers.add(this.setDefaultUser(2));
		Object[] listOfParamsValue = { "%%" };
		ArrayList<HashMap<String, String>> getUserResponse = new ArrayList<HashMap<String, String>>();
		getUserResponse.add(getSQLGETUserResponse(1));
		getUserResponse.add(getSQLGETUserResponse(2));
		Mockito.when(db.runGetQuery(GetSqlQueries.SQLGETUsers, listOfParamsValue)).thenReturn(getUserResponse);
		assertEquals(listOfUsers, udaoObj.getUserListData(null, null, null));
	}

	@Test
	public void testGetUserListDataWithPatientDetails() throws SQLException {
		Object[] listOfParamsValue = { (long) 1 };
		ArrayList<HashMap<String, String>> getUserResponse = new ArrayList<HashMap<String, String>>();
		getUserResponse.add(getSQLGETUserResponse(1));
		Mockito.when(db.runGetQuery(GetSqlQueries.SQLGETUser, listOfParamsValue)).thenReturn(getUserResponse);
		Mockito.when(pdaoObj.getPatientData(1)).thenReturn(new Patient());
		assertNotNull(udaoObj.getUserListData(null, (long) 1, "PATIENT").get(0).getPatientData());

	}
}