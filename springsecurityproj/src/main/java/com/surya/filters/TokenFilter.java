package com.surya.filters;

import com.surya.authentications.TokenAuthentication;
import com.surya.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization" + authHeader);
        System.out.println(tokenService.contins(authHeader));
//        if(tokenService.contins(authHeader))
//        {
//            System.out.println("entry");
//
//            filterChain.doFilter(request,response);
//        }
//        else
//        {
//            response.sendError(404,"not found bc");
//        }
        TokenAuthentication tokenAuthentication = new TokenAuthentication(authHeader, false);
        Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
        if (authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
