package io.swagger.api;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.UsersApiServiceFactory;
import io.swagger.model.Edd;
import io.swagger.model.LoginResponse;
import io.swagger.model.User;

@Path("/users")

@io.swagger.annotations.Api(description = "the users API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class UsersApi {
	private final UsersApiService delegate;

	public UsersApi(@Context ServletConfig servletContext) {
		UsersApiService delegate = null;

		if (servletContext != null) {
			String implClass = servletContext.getInitParameter("UsersApi.implementation");
			if (implClass != null && !"".equals(implClass.trim())) {
				try {
					delegate = (UsersApiService) Class.forName(implClass).newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		if (delegate == null) {
			delegate = UsersApiServiceFactory.getUsersApi();
		}

		this.delegate = delegate;
	}

	@DELETE

	@Produces({ "application/xml", "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Deletes a patient", notes = "", response = Void.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "users", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "succesfully deleted", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "Invalid user Id", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "Patient not found", response = Void.class) })
	public Response usersDelete(
			@ApiParam(value = "User id to delete", required = true) @QueryParam("userId") Long userId,
			@Context SecurityContext securityContext
			, @Context HttpHeaders httpHeader) throws NotFoundException {
		return delegate.usersDelete(userId, securityContext, httpHeader);
	}

	@POST
	@Path("/edd")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "calculates expected delivery date", notes = "", response = Edd.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "users", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Edd.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Something went wrong", response = Void.class) })
	public Response usersEddPost(
			@ApiParam(value = "Additional data to pass to server", required = true) Edd body,
			@Context SecurityContext securityContext
			, @Context HttpHeaders httpHeader) throws NotFoundException {
		return delegate.usersEddPost(body, securityContext, httpHeader);
	}

	@PUT
	@Path("/edd")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "calculates expected delivery date", notes = "", response = Edd.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "users", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Edd.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Something went wrong", response = Void.class) })
	public Response usersEddPut(@ApiParam(value = "Additional data to pass to server", required = true) Edd body,
			@Context SecurityContext securityContext
			, @Context HttpHeaders httpHeader) throws NotFoundException {
		return delegate.usersEddPut(body, securityContext, httpHeader);
	}

	@GET

	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "get list of users according to the role of user", notes = "", response = User.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "users", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = User.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "Invalid string could not find", response = Void.class) })
	public Response usersGet(@ApiParam(value = "user name to search any patient") @QueryParam("name") String name,
			@ApiParam(value = "ID of user to search") @QueryParam("userId") Long userId,
			@ApiParam(value = "true to get patient data") @QueryParam("userRoleType") String userRoleType,
			@Context SecurityContext securityContext
			, @Context HttpHeaders httpHeader) throws NotFoundException {
		return delegate.usersGet(name, userId, userRoleType, securityContext, httpHeader);
	}

	@POST

	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Registers user into the system", notes = "", response = LoginResponse.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "users", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = LoginResponse.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "Some data is missing", response = Void.class) })
	public Response usersPost(@ApiParam(value = "Created user object", required = true) User body,
			@Context SecurityContext securityContext
			, @Context HttpHeaders httpHeader) throws NotFoundException {
		return delegate.usersPost(body, securityContext, httpHeader);
	}

	@PUT

	@Consumes({ "application/json" })
	@Produces({ "application/xml", "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Updates a patient with body data", notes = "", response = Void.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "users", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Successfully Updated", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Something went wrong", response = Void.class) })
	public Response usersPut(@ApiParam(value = "Updated name of the patient", required = true) User body,
			@Context SecurityContext securityContext
			, @Context HttpHeaders httpHeader) throws NotFoundException {
		return delegate.usersPut(body, securityContext, httpHeader);
	}
}
