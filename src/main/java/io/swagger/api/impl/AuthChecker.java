package io.swagger.api.impl;

import javax.ws.rs.core.HttpHeaders;

import io.swagger.Utility;
import io.swagger.dao.LoginResponseDAO;
import io.swagger.model.Login;
import io.swagger.model.LoginResponse;

public class AuthChecker {
	private Integer lastChangedBy = null;
	private String userRole = null;
	
	public Integer getLastChangedBy() {
		return this.lastChangedBy;
	}
	
	public String getUserRole() {
		return this.userRole;
	}
	public void checkAuthentication(String BasicAuth)
	{
		
		String[] userNameAndPassword = Utility.getBase64Token(BasicAuth.split(" ")[1]).split(":");
		String userName = userNameAndPassword[0];
		String password = userNameAndPassword[1];
		LoginResponseDAO ldaoObj = new LoginResponseDAO();
		Login body = new Login();
		body.setUserName(userName);
		body.setPassword(password);
		LoginResponse lrObj = ldaoObj.getLoginResponseData(body);
		this.lastChangedBy = lrObj.getUserId() ;
		this.userRole = lrObj.getUserRole();
	}
	
	public AuthChecker getAuthorization(HttpHeaders header, Integer userId, String roleRequied)
			throws IllegalArgumentException {
		if (header.getRequestHeader(HttpHeaders.AUTHORIZATION) == null)
			throw new IllegalArgumentException("Not Authorized");
		String authorization = header.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0);
//		AuthChecker acObj = new AuthChecker();
		this.checkAuthentication(authorization);
		if (this.getUserRole() == null)
			throw new IllegalArgumentException("Not Authorized");
		if (this.getUserRole().equals("ADMIN"))
			return this;
		else if (roleRequied != null && this.getUserRole().equals(roleRequied))
			return this;
		else if (roleRequied == null && this.getUserRole().equals("DOCTOR"))
			return this;
		else if (roleRequied == null && this.getUserRole().equals("PATIENT") && this.getLastChangedBy() == userId)
			return this;
		else
			throw new IllegalArgumentException("Not Authorized");
	}
}
