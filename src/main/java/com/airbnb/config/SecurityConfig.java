package com.airbnb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //h(cd)2
        http.csrf().disable().cors().disable();

        //haap - security filter chain
        //http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/createUser","/api/v1/auth/createpropertyowner","/api/v1/auth/login","/api/v1/**")
                .permitAll()
                .requestMatchers("/api/v1/property/addProperty").hasAnyRole("OWNER","ADMIN","MANAGER")
                .requestMatchers("/api/v1/auth/createpropertymanager").hasRole("ADMIN")
                .requestMatchers("/api/v1/dummy/get-message")
                .hasAnyRole("ADMIN", "USER")
                .anyRequest().
                authenticated();
        return http.build();
    }
}
