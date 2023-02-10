package com.example.demo.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        response.sendRedirect(determineTargetUrl(request, response));
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {

        // Get the role of logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String role = auth.getAuthorities().toString();

        String targetUrl = "";

        if (role.contains("ADMIN")) {
            targetUrl = "/home";
        } else if (role.contains("USER")) {
            targetUrl = "/users/homepage";
        }

        return targetUrl;
    }

}
