package com.cupidmeet.locationdataservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up security using Spring Security.
 * <p>
 * This class configures authentication based on OAuth2 and JWT tokens,
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the Security Filter Chain.
     * <p>
     * - All requests require authentication with Admin permission (`.anyRequest().hasRole("ADMIN)`).
     * - OAuth2 Resource Server with JWT authentication is used.
     *
     * @param http the HTTP security configuration object.
     * @return the configured security filter chain.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasRole("ADMIN"))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
