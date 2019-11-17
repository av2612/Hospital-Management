package io.swagger.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.Utility;
import io.swagger.model.Login;
import io.swagger.model.LoginResponse;

public class LoginResponseDAO implements DAO {

	Database db;

	public LoginResponseDAO() {
		Database dbObj = new DataBaseImpl();
		this.setDatabaseService(dbObj);
	}

	public void setDatabaseService(Database dbObj) {
		this.db = dbObj;
	}

	public LoginResponse getLoginResponseData(Login body) {
		LoginResponse respObj = new LoginResponse();
		String loginQuery = GetSqlQueries.SQLLoginUser;
		Object[] loginQueryParams = { body.getUserName(), body.getPassword() };
		try {
			ArrayList<HashMap<String, String>> rset = db.runGetQuery(loginQuery, loginQueryParams);
			if (rset.size() == 1) {

				respObj.setUserId(Integer.parseInt(rset.get(0).get("id")));
				respObj.setUsername(rset.get(0).get("userName"));
				respObj.setUserRole(rset.get(0).get("userRoleName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respObj.getUsername() != null && respObj.getUserRole() != null && body.getPassword() != null) {
			respObj.setToken(Utility.setBase64Token(respObj.getUsername(), body.getPassword()));
		}
		return respObj;
	}

	public LoginResponse getById(int id, String password) {
		LoginResponse respObj = new LoginResponse();
		String loginQuery = GetSqlQueries.SQLLoginUserById;
		Object[] loginQueryParams = { id };
		try {
			ArrayList<HashMap<String, String>> rset = db.runGetQuery(loginQuery, loginQueryParams);
			if (rset.size() == 1) {

				respObj.setUserId(Integer.parseInt((String) rset.get(0).get("id")));
				respObj.setUsername((String) rset.get(0).get("userName"));
				respObj.setUserRole((String) rset.get(0).get("userRoleName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respObj.getUsername() != null && respObj.getUserRole() != null && password != null) {
			respObj.setToken(Utility.setBase64Token(respObj.getUsername(), password));
		}
		return respObj;
	}

}
