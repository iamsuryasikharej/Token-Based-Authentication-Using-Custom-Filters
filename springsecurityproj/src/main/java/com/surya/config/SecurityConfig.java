package com.surya.config;

import com.surya.autheticationproviders.CustomAuthenticationProvider;
import com.surya.autheticationproviders.OtpAuthenticationProider;
import com.surya.autheticationproviders.TokenAuthenticationProvider;
import com.surya.filters.CustomFilter;
import com.surya.filters.OtpFlilter;
import com.surya.filters.TokenFilter;
import com.surya.service.JpaUserDetailsService;
import com.surya.voters.CustomVoters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Lazy
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    @Lazy
    OtpAuthenticationProider otpAuthenticationProider;
    @Autowired
    @Lazy
    TokenAuthenticationProvider tokenAuthenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPaswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new JpaUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(otpAuthenticationProider)
                .authenticationProvider(customAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/hello")
//                .hasAuthority("ADMIN")
//
//                .and()
//                .addFilterAt(getFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(getOtpFilter(), CustomFilter.class);
        http.addFilterAt(getFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(getOtpFilter(), CustomFilter.class)
                .addFilterAfter(getTokenFilter(), OtpFlilter.class);
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public AccessDecisionManager getAccessDecisionManager() {
        List<AccessDecisionVoter<?>> accessDecisionVoterList = Arrays.asList(
                new CustomVoters(),
                new AuthenticatedVoter(),
                new RoleVoter(),
                new WebExpressionVoter()
        );
        return new UnanimousBased(accessDecisionVoterList);

    }

    @Bean
    public Filter getFilter() {
        return new CustomFilter();
    }

    @Bean
    public Filter getOtpFilter() {
        return new OtpFlilter();
    }

    @Bean
    public AuthenticationProvider getOtpAuthenticationProvider() {
        return new OtpAuthenticationProider();
    }

    @Bean
    public AuthenticationProvider getTokAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

    @Bean
    public Filter getTokenFilter() {
        return new TokenFilter();
    }
}
