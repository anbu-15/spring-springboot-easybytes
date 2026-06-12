package com.eazybytes.jobportal.util;

import com.eazybytes.jobportal.constants.ApplicationConstants;
import com.eazybytes.jobportal.entity.JobPortalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtility {

    public static String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return ApplicationConstants.SYSTEM;
        }
        Object principal = authentication.getPrincipal();
        String userName;
        if (principal instanceof JobPortalUser jobPortalUser) {
            userName = jobPortalUser.getEmail();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}