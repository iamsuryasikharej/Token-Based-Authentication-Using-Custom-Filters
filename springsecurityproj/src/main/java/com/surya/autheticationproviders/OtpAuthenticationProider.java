package com.surya.autheticationproviders;

import com.surya.authentications.OtpAuthentiction;
import com.surya.entities.Otp;
import com.surya.repositories.OtpRepository;
import com.surya.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class OtpAuthenticationProider implements AuthenticationProvider {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    JpaUserDetailsService jpaUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OtpAuthentiction otpAuthentiction = (OtpAuthentiction) authentication;
        Otp otp = otpRepository.findOtpByUsername(authentication.getName());
        String userName = authentication.getName();
        UserDetails u = jpaUserDetailsService.loadUserByUsername(userName);
        if (otp.getOtp().equals(otpAuthentiction.getOtp())) {
            OtpAuthentiction a = new OtpAuthentiction(userName, otp.getOtp(), u.getAuthorities());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(a);
            return a;
        } else throw new BadCredentialsException("!otp Doesn't Match");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println(OtpAuthentiction.class.equals(authentication));
        return OtpAuthentiction.class.equals(authentication);
    }
}
