package com.alek0m0m.dronepizzabackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasRole("ADMIN")  // Require ADMIN role for all requests
                )
                .formLogin(AbstractAuthenticationFilterConfigurer
                        ::permitAll  // Allow access to login page
                )
                .httpBasic();    // Enable basic auth for API access

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        AdminUser adminUser = new AdminUser();
        UserDetails admin = User.builder()
                .username(adminUser.getUsername())
                .password("{noop}" + adminUser.getPassword())  // {noop} means no password encoding
                .roles(adminUser.getRole())
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}