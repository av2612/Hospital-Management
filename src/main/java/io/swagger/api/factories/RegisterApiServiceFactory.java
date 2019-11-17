package io.swagger.api.factories;

import io.swagger.api.RegisterApiService;
import io.swagger.api.impl.RegisterApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-09-08T11:58:17.132Z")
public class RegisterApiServiceFactory {
    private final static RegisterApiService service = new RegisterApiServiceImpl();

    public static RegisterApiService getRegisterApi() {
        return service;
    }
}
