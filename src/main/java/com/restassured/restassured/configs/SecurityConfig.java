package com.restassured.restassured.configs;

import com.restassured.restassured.converters.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final  JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter){
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable() )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/v2/posts").authenticated()
                                .requestMatchers("/api/v2/post/**").authenticated()
                                .requestMatchers("/api/v2/count").hasRole("client1_ADMIN")
                                .requestMatchers("api/v2/newpost").hasRole("client1_USER")
                                .requestMatchers("/api/v2/updatepost").hasRole("client1_USER")
                                .requestMatchers("/api/v2/deletepost/**").hasAnyRole("client1_USER", "client1_ADMIN")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );

        return http.build();
    }

}
