package com.eazybytes.jobportal.scopes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Getter @Setter
public class SessionScopeBean {

    private String userName;

    public SessionScopeBean(){
        System.out.println("Session Scope Bean Created");
    }
}
