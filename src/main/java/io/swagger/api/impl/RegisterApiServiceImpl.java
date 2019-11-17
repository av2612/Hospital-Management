package io.swagger.api.impl;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import io.swagger.api.NotFoundException;
import io.swagger.api.RegisterApiService;
import io.swagger.dao.UserDAO;
import io.swagger.model.LoginResponse;
import io.swagger.model.User;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class RegisterApiServiceImpl extends RegisterApiService {
	
	UserDAO userDAOObj;

	public RegisterApiServiceImpl() {
		this(new UserDAO());
	}

	RegisterApiServiceImpl(UserDAO udao) {
		this.userDAOObj = udao;
	}
	
	@Override
	public Response registerPost(User body, SecurityContext securityContext) throws NotFoundException {
		// do some magic!
		body.setUserRole("PATIENT");
		try {
		LoginResponse respObj = userDAOObj.postUserData(body);
		if (respObj.getToken() != null) {
			return Response.ok().entity(respObj).build();
		} else
			return Response.status(Status.FORBIDDEN).build();
		}
		catch(SQLException ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
		}
	}
}
