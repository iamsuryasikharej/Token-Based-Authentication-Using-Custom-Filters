package com.surya.voters;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public class CustomVoters implements AccessDecisionVoter {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection collection) {
        System.out.println("inside custom voter");
        //custom vote logic
        //return ACCESS_DENIED OR ACCESS_GRANTED or ACCESS_ABSTAIN
        //0                 //1              //-1
        //The authentication object contains all the user-name
        // details and authorities of the user tying to access the resource
        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }
}
