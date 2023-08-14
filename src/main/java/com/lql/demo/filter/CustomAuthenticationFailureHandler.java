package com.lql.demo.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Enumeration;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        System.out.println(request.getPa);
        Enumeration<String> parameterNames = request.getParameterNames();

        System.err.println(parameterNames);
        System.err.println(parameterNames.nextElement());
        while (parameterNames.hasMoreElements()){
            String s = parameterNames.nextElement();
            System.out.printf("%s : %s \n", s, request.getParameter(s));
        }
            // Log the incorrect credentials for debugging purposes
        System.out.println("Failed login attempt with Username: " + username + ", Password: " + password);
        response.setStatus(401);
        // Redirect to the login page with an error message or handle the failure appropriately
        response.sendRedirect("/login-status?error=true");
    }
}
