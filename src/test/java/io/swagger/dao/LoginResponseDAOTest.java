package io.swagger.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.model.Login;
import io.swagger.model.LoginResponse;

public class LoginResponseDAOTest {

	Database db = Mockito.mock(Database.class);

	public Login setLoginBody(String userName, String userEmail, String password) {
		Login body = new Login();
		body.setUserName(userName);
		body.setUserEmail(userEmail);
		body.setPassword(password);
		return body;
	}

	public ArrayList<HashMap<String, String>> getQueryResponse(String[] columnName, String[][] columnValueList) {
		ArrayList<HashMap<String, String>> respObj = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < columnValueList.length; i++) {
			HashMap<String, String> item = new HashMap<String, String>();
			for (int j = 0; j < columnValueList[i].length; j++) {
				item.put(columnName[j], columnValueList[i][j]);
			}
			respObj.add(item);
		}
		return respObj;
	}
	public LoginResponse setLoginResponse(Integer id, String userName, String role, String token) {
		LoginResponse lr = new LoginResponse();
		lr.setUsername(userName);
		lr.setUserId(id);
		lr.setUserRole(role);
		lr.setToken(token);
		return lr;
	}
	@Test
	public void testSuccesfullLoginResponse() {
		Login body = this.setLoginBody("aakash", null, "asdf");
		String[] columnName = {"id", "userName", "userRoleName"};
		String[][] columnValueList = {{"1", "aakash", "Doctor"}};
		ArrayList<HashMap<String, String>> queryResp = getQueryResponse(columnName, columnValueList);
		Object[] params = { "aakash", "asdf" };
		try {
			Mockito.when(db.runGetQuery(GetSqlQueries.SQLLoginUser, params)).thenReturn(queryResp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoginResponseDAO ldao = new LoginResponseDAO();
		ldao.setDatabaseService(db);
		LoginResponse lr = this.setLoginResponse(1, "aakash", "Doctor", "YWFrYXNoOmFzZGY=");
		assertEquals(lr, ldao.getLoginResponseData(body));
	}
	@Test
	public void testLoginNoResponse() {
		Login body = this.setLoginBody("aakash", null, "asd");
		ArrayList<HashMap<String, String>> queryResp = new ArrayList<HashMap<String,String>>();
		Object[] params = { "aakash", "asd" };
		try {
			Mockito.when(db.runGetQuery(GetSqlQueries.SQLLoginUser, params)).thenReturn(queryResp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoginResponseDAO ldao = new LoginResponseDAO();
		ldao.setDatabaseService(db);
		LoginResponse lr = new LoginResponse();
		assertEquals(lr, ldao.getLoginResponseData(body));
	}
	
	@Test
	public void testLoginNoResponsewithSQLException() {
		Login body = this.setLoginBody("aakash", null, "asd");
		Object[] params = { "aakash", "asd" };
		try {
			Mockito.when(db.runGetQuery(GetSqlQueries.SQLLoginUser, params)).thenThrow(new SQLException());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoginResponseDAO ldao = new LoginResponseDAO();
		ldao.setDatabaseService(db);
		LoginResponse lr = new LoginResponse();
		assertEquals(lr, ldao.getLoginResponseData(body));
	}

}
