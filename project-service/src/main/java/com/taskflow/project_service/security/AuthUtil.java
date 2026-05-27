package com.taskflow.project_service.security;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public String getAuthenticatedUser(
            HttpServletRequest request) {

        return request.getHeader(
                "X-Authenticated-User"
        );
    }

    public String getAuthSource(
            HttpServletRequest request) {

        return request.getHeader(
                "X-Auth-Source"
        );
    }
}