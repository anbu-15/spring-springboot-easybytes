package com.eazybytes.jobportal.scopes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scope")
@RequiredArgsConstructor
public class ScopeController {

    private final RequestScopeBean requestScopeBean;
    private final SessionScopeBean sessionScopeBean;
    private final ApplicationScopeBean applicationScopeBean;

    @GetMapping("/request")
    public ResponseEntity<String> testRequestScope(){
        requestScopeBean.setUserName("John Doe");
        return ResponseEntity.ok().body(requestScopeBean.getUserName());
    }

    @GetMapping("/session")
    public ResponseEntity<String> testSessionScope(){
        sessionScopeBean.setUserName("John Doe");
        return ResponseEntity.ok().body(sessionScopeBean.getUserName());
    }

    @GetMapping("/application")
    public ResponseEntity<Integer> testApplicationScope(){
        applicationScopeBean.incrementVistorsCount();
        return ResponseEntity.ok().body(applicationScopeBean.getVistorsCounts());
    }

    @GetMapping("/test")
    public ResponseEntity<String> testScope(){
        return ResponseEntity.ok().body(requestScopeBean.getUserName() +" - "+ sessionScopeBean.getUserName() +" - "+ applicationScopeBean.getVistorsCounts());
    }
}
