package com.cupidmeet.chatservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSocketSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/ws/**").permitAll() // WebSocket эндпоинты
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
    }
}
