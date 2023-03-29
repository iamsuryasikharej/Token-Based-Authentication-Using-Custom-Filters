package com.surya.service;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenService {
    private Set<String> tokens = new HashSet<>();

    public void addToken(String token) {
        tokens.add(token);
    }

    public boolean contins(String token) {
        return tokens.contains(token);
    }

}
