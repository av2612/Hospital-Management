package io.swagger.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.model.Login;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public abstract class LoginApiService {
    public abstract Response loginPost(Login body,SecurityContext securityContext) throws NotFoundException;
}
	