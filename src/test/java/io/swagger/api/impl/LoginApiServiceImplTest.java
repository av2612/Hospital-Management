package io.swagger.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.api.NotFoundException;
import io.swagger.dao.LoginResponseDAO;
import io.swagger.model.Login;
import io.swagger.model.LoginResponse;

public class LoginApiServiceImplTest {
	LoginResponseDAO lrdao = Mockito.mock(LoginResponseDAO.class);
	LoginApiServiceImpl lraObj = new LoginApiServiceImpl(lrdao);
	
	public Login setLoginBody(String userName, String userEmail, String password) {
		Login body = new Login();
		body.setUserName(userName);
		body.setUserEmail(userEmail);
		body.setPassword(password);
		return body;
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
	public void testLoginApiConstructor() throws NotFoundException {
		LoginApiServiceImpl loginApiServiceObj = new LoginApiServiceImpl();
		assertNotNull(loginApiServiceObj.loginRespDAOObj); 
	}
	
	@Test
	public void testSuccessfullResponse() throws NotFoundException {
		Login body = this.setLoginBody("aakash", null, "asdf");
		LoginResponse lrObj = this.setLoginResponse(1, "aakash", "Doctor", "YWFrYXNoOmFzZGY=");
		Response expectedResponse = Response.ok().entity(lrObj).build();
		Mockito.when(lrdao.getLoginResponseData(body)).thenReturn(lrObj);
		assertEquals(expectedResponse.toString(), lraObj.loginPost(body, null).toString());
		assertEquals(expectedResponse.getEntity(), lraObj.loginPost(body, null).getEntity());
	}
	
	@Test
	public void testUnSuccessfullResponse() throws NotFoundException {
		Login body = this.setLoginBody("aakash", null, "asd");
		LoginResponse lrObj = new LoginResponse();
		Response expectedResponse = Response.status(Status.FORBIDDEN).build();;
		Mockito.when(lrdao.getLoginResponseData(body)).thenReturn(lrObj);
		assertEquals(expectedResponse.toString(), lraObj.loginPost(body, null).toString());
		assertEquals(expectedResponse.getEntity(), lraObj.loginPost(body, null).getEntity());
	}

}
