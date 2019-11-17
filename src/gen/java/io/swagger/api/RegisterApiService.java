package io.swagger.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.HttpHeaders;

import io.swagger.model.User;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public abstract class RegisterApiService {
	public abstract Response registerPost(User body, SecurityContext securityContext) throws NotFoundException;
}
