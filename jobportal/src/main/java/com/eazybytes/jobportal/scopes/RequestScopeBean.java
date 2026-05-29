package com.eazybytes.jobportal.scopes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter @Setter
public class RequestScopeBean {

    private String userName;

    public RequestScopeBean(){
        System.out.println("Request Scope Bean Created");
    }
}
