package io.swagger.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import io.swagger.api.LoginApiService;
import io.swagger.api.NotFoundException;
import io.swagger.dao.LoginResponseDAO;
import io.swagger.model.Login;
import io.swagger.model.LoginResponse;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class LoginApiServiceImpl extends LoginApiService {
	LoginResponseDAO loginRespDAOObj;

	public LoginApiServiceImpl() {
		this(new LoginResponseDAO());
	}

	LoginApiServiceImpl(LoginResponseDAO ldao) {
		this.loginRespDAOObj = ldao;
	}

	@Override
	public Response loginPost(Login body, SecurityContext securityContext) throws NotFoundException {
		LoginResponse respObj = this.loginRespDAOObj.getLoginResponseData(body);
		if (respObj.getToken() != null) {
			return Response.ok().entity(respObj).build();
		} else
			return Response.status(Status.FORBIDDEN).build();
	}

}
