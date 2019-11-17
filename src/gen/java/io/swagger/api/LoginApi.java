package io.swagger.api;

import javax.servlet.ServletConfig;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.LoginApiServiceFactory;
import io.swagger.model.Login;
import io.swagger.model.LoginResponse;

@Path("/login")


@io.swagger.annotations.Api(description = "the login API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class LoginApi  {
   private final LoginApiService delegate;

   public LoginApi(@Context ServletConfig servletContext) {
      LoginApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("LoginApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (LoginApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = LoginApiServiceFactory.getLoginApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Logs employee into the system", notes = "", response = LoginResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = LoginResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid username/password supplied", response = Void.class) })
    public Response loginPost(@ApiParam(value = "Logs user in" ,required=true) Login body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.loginPost(body,securityContext);
    }
}
