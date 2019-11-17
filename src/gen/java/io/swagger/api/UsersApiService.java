package io.swagger.api;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.model.Edd;
import io.swagger.model.User;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public abstract class UsersApiService {
    public abstract Response usersDelete( @NotNull Long userId,SecurityContext securityContext, HttpHeaders header) throws NotFoundException;
    public abstract Response usersEddPost(Edd body,SecurityContext securityContext, HttpHeaders header) throws NotFoundException;
    public abstract Response usersEddPut(Edd body,SecurityContext securityContext, HttpHeaders header) throws NotFoundException;
    public abstract Response usersGet( String name, Long userId, String userRoleType,SecurityContext securityContext, HttpHeaders header) throws NotFoundException;
    public abstract Response usersPost(User body,SecurityContext securityContext, HttpHeaders header) throws NotFoundException;
    public abstract Response usersPut(User body,SecurityContext securityContext, HttpHeaders header) throws NotFoundException;
}
