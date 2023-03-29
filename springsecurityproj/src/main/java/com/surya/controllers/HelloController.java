package com.surya.controllers;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.getAuthentication().getAuthorities();
        System
                .out
                .println(securityContext.getAuthentication().getAuthorities() + "" + securityContext.getAuthentication().getName());


        return "hello";
    }
}
