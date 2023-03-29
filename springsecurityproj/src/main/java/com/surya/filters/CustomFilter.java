package com.surya.filters;

import com.surya.authentications.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFilter extends OncePerRequestFilter {
    @Autowired
    AuthenticationManager am;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String name = request.getHeader("uname");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");
        System.out.println("entry");
        UsernamePasswordAuthentication usernamePasswordAuthentication = new UsernamePasswordAuthentication(name, password);
        Authentication authenticationResult = am.authenticate(usernamePasswordAuthentication);
        if (authenticationResult.isAuthenticated()) {
            System.out.println("authenticated");
            filterChain.doFilter(request, response);
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
