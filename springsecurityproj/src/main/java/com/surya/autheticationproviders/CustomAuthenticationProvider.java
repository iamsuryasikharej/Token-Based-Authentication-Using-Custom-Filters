package com.surya.autheticationproviders;

import com.surya.authentications.UsernamePasswordAuthentication;
import com.surya.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JpaUserDetailsService jpaUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails ud = jpaUserDetailsService.loadUserByUsername(name);
        System.out.println("entry");
        System.out.println(ud.getAuthorities());
        System.out.println(passwordEncoder.matches(password, ud.getPassword()));
        if (passwordEncoder.matches(password, ud.getPassword())) {
            System.out.println("entry 2");
            Authentication a = new UsernamePasswordAuthentication(authentication.getName(), authentication.getCredentials(), ud.getAuthorities());
            System.out.println(a.isAuthenticated());
            return a;
        } else throw new BadCredentialsException("!Error");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
