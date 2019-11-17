package io.swagger.api;

import javax.servlet.ServletConfig;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.RegisterApiServiceFactory;
import io.swagger.model.LoginResponse;
import io.swagger.model.User;

@Path("/register")


@io.swagger.annotations.Api(description = "the register API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class RegisterApi  {
   private final RegisterApiService delegate;

   public RegisterApi(@Context ServletConfig servletContext) {
      RegisterApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("RegisterApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (RegisterApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = RegisterApiServiceFactory.getRegisterApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Registers user into the system", notes = "", response = LoginResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = LoginResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid username/password supplied", response = Void.class) })
    public Response registerPost(@ApiParam(value = "Created employee object" ,required=true) User body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.registerPost(body,securityContext);
    }
}
