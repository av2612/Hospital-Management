package io.swagger.api.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.api.UsersApiService;
import io.swagger.dao.UserDAO;
import io.swagger.model.Edd;
import io.swagger.model.User;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class UsersApiServiceImpl extends UsersApiService {

	UserDAO udaoObj;
	EDDBLL eddBllObj;
	AuthChecker acObj;

	public UsersApiServiceImpl() {
		this(new UserDAO(), new EDDBLL(), new AuthChecker());
	}

	public UsersApiServiceImpl(UserDAO udaoObj, EDDBLL eddBllObj, AuthChecker acObj) {
		this.udaoObj = udaoObj;
		this.eddBllObj = eddBllObj;
		this.acObj = acObj;
	}


	@Override
	public Response usersDelete(@NotNull Long userId, SecurityContext securityContext, HttpHeaders header)
			throws NotFoundException {
		// do some magic!
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}

	@Override
	public Response usersEddPost(Edd body, SecurityContext securityContext, HttpHeaders header)
			throws NotFoundException {
		// do some magic!
		try {
			acObj.getAuthorization(header, body.getUserId().intValue(), null);
			Edd eddResp = this.eddBllObj.eddEntryPoint(body, acObj);
			return Response.ok().entity(eddResp).build();
		} catch (IllegalArgumentException ex) {
			return Response.status(Status.FORBIDDEN).entity(ex.getMessage()).build();
		}

	}

	@Override
	public Response usersEddPut(Edd body, SecurityContext securityContext, HttpHeaders header)
			throws NotFoundException {
		try {
			acObj.getAuthorization(header, body.getUserId().intValue(), null);
			Edd eddResp = this.eddBllObj.eddEntryPoint(body, acObj);
			return Response.ok().entity(eddResp).build();
		} catch (IllegalArgumentException ex) {
			return Response.status(Status.FORBIDDEN).entity(ex.getMessage()).build();
		}
	}

	@Override
	public Response usersGet(String name, Long userId, String userRoleType, SecurityContext securityContext,
			HttpHeaders header) throws NotFoundException {
		try {
			acObj.getAuthorization(header, userId!=null?userId.intValue(): null, null);
			ArrayList<User> resUsers = udaoObj.getUserListData(name, userId, userRoleType, acObj.getLastChangedBy());
			if (resUsers.size() >= 1)
				return Response.ok().entity(resUsers).build();
			else
				return Response.noContent().entity(resUsers).build();
		} catch (IllegalArgumentException ex) {
			return Response.status(Status.FORBIDDEN).entity(ex.getMessage()).build();
		}
	}

	@Override
	public Response usersPost(User body, SecurityContext securityContext, HttpHeaders header) throws NotFoundException {
		// do some magic!
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}

	@Override
	public Response usersPut(User body, SecurityContext securityContext, HttpHeaders header) throws NotFoundException {
		try {
			acObj.getAuthorization(header, body.getUserId().intValue(), null);
			User resUsers = udaoObj.putUserData(body, true, acObj);
			return Response.ok().entity(resUsers).build();
		} catch (SQLException ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
		} catch (IllegalArgumentException ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
		}
	}
}
