package com.eazybytes.jobportal.scopes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
@Getter
@Setter
public class ApplicationScopeBean {

    private int vistorsCounts;

    public ApplicationScopeBean() {
        System.out.println("Application Scope Bean Created");
    }

    public void incrementVistorsCount() {
        vistorsCounts++;
    }
}
