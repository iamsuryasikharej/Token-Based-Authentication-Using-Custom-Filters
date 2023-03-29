package com.surya.springsecurityproj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@SpringBootApplication
@EnableWebSecurity
@ComponentScan({"com.surya.config", "com.surya.controllers", "com.surya.service"})
@EnableJpaRepositories(basePackages = {"com.surya.repositories"})
@EntityScan("com.surya.entities")
public class SpringsecurityprojApplication {
    @Autowired
    static AuthenticationManager authenticationManager;

    public static void main(String[] args) {

        SpringApplication.run(SpringsecurityprojApplication.class, args);
    }

    @Bean
    public DataSource getdataSource() {

        return DataSourceBuilder.create()
                .username("root")
                .password("admin")
                .url("jdbc:mysql://localhost:3306/testapp1")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();

    }
}

