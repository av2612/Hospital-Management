package io.swagger.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.api.NotFoundException;
import io.swagger.dao.UserDAO;
import io.swagger.model.User;;

public class UsersApiServiceImplTest {
	
	UserDAO userDaoObj = Mockito.mock(UserDAO.class);
	AuthChecker acObj = Mockito.mock(AuthChecker.class);
	UsersApiServiceImpl userApiServiceObj;
		
	@Before
	public void setUserApi() {
		userApiServiceObj = new UsersApiServiceImpl(userDaoObj, new EDDBLL(), acObj);
	}
	public User setDefaultUser() {
		User userObj = new User();
		userObj.setUserId(1);
		userObj.setUserName("aakash");
		userObj.setUserEmail("a.v2612@gmail.com");
		userObj.setDateOfBirth("1993-12-26 12:30");
		return userObj;
	}
	
	@Test
	public void testConstructor() {
		UsersApiServiceImpl userApiServiceObj = new UsersApiServiceImpl();
		assertNotNull(userApiServiceObj.udaoObj); 
	}
	
	@Test
	public void testUsersGetIfUsersExists() throws NotFoundException {

		Mockito.when(acObj.getUserRole()).thenReturn("DOCTOR");
		Mockito.when(acObj.getLastChangedBy()).thenReturn(70);
		Mockito.when(acObj.getAuthorization(null, 1, null)).thenReturn(acObj);
		ArrayList<User> userListObj = new ArrayList<User>();
		User userObj = this.setDefaultUser();
		userListObj.add(userObj);
		Mockito.when(userDaoObj.getUserListData(null, (long) 1, null)).thenReturn(userListObj);
		Response actualResponse = userApiServiceObj.usersGet(null,(long) 1, null, null, null);
		assertEquals(Response.ok().entity(userListObj).build().toString(), actualResponse.toString());
		assertEquals(Response.ok().entity(userListObj).build().getEntity(), actualResponse.getEntity());
	}
	
	@Test
	public void testUsersGetWithUserNotExists() throws NotFoundException {
		Mockito.when(acObj.getUserRole()).thenReturn("DOCTOR");
		Mockito.when(acObj.getLastChangedBy()).thenReturn(70);
		Mockito.when(acObj.getAuthorization(null, 1, null)).thenReturn(acObj);
		ArrayList<User> userListObj = new ArrayList<User>();
		Mockito.when(userDaoObj.getUserListData(null, (long) 1, null)).thenReturn(userListObj);
		Response actualResponse = userApiServiceObj.usersGet(null,(long) 2, null, null, null);
		assertEquals(Response.noContent().entity(userListObj).build().toString(), actualResponse.toString());
		assertEquals(Response.ok().entity(userListObj).build().getEntity(), actualResponse.getEntity());
	}

}
