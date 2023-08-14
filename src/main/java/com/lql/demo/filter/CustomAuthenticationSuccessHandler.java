package com.lql.demo.filter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
//        response.sendRedirect("/homes");
//        SecurityContextHolder
//        System.out.println();
        response.setStatus(201);
        response.sendRedirect("/login-status?error=false");
//        Cookie session = new Cookie("SESSION", request.getSession().getId());
//        session.setMaxAge(10000);
//        response.addCookie(session);
//        response.sendRedirect("/dashboard");
    }
}
