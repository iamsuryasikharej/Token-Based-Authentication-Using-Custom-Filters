package com.surya.filters;

import com.surya.authentications.OtpAuthentiction;
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
import java.util.UUID;

public class OtpFlilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager am;
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest req= (HttpServletRequest) request;
//        HttpServletResponse res= (HttpServletResponse) response;
//        String otp=req.getHeader("otp");
//        String name=req.getHeader("uname");
//        OtpAuthentiction otpAuthentiction=new OtpAuthentiction(name,otp,null);
//        Authentication auth=am.authenticate(otpAuthentiction);
//        if(auth.isAuthenticated())
//        {
//            ((HttpServletResponse) response).setHeader("Authorization", UUID.randomUUID().toString());
////            chain.doFilter(request,response);
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse res = response;
        String otp = request.getHeader("otp");
        String name = request.getHeader("uname");
        OtpAuthentiction otpAuthentiction = new OtpAuthentiction(name, otp, null);
        Authentication auth = am.authenticate(otpAuthentiction);
        if (auth.isAuthenticated()) {
            String token = UUID.randomUUID().toString();
            tokenService.addToken(token);
            response.setHeader("Authorization", token);
//            chain.doFilter(request,response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");

    }
}
