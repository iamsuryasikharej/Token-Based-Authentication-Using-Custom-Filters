package com.surya.authentications;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedList;


public class OtpAuthentiction implements Authentication {

    String uname;
    boolean authenticated;
    String otp;
    Collection<? extends GrantedAuthority> authorities = new LinkedList<>();

    public OtpAuthentiction(String name, String otp, Collection<? extends GrantedAuthority> authorities) {
        this.otp = otp;
        this.uname = name;
        this.authenticated = true;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.uname;
    }

    public String getOtp() {
        return this.otp;
    }
}
