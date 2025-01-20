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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf helps with: Cross-Site Request Forgery,
                //  which is a type of attack that occurs when a malicious web site, email, blog, instant message, or program causes
                //  a user’s web browser to perform an unwanted action on a trusted site when the user is authenticated.
                .csrf(AbstractHttpConfigurer::disable)
                // cors helps with: Cross-Origin Resource Sharing,
                // which is a mechanism that uses additional HTTP headers to tell browsers to give a web application running at one origin,
                // access to selected resources from a different origin.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasRole("ADMIN"))
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .httpBasic();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // ports 8080 and 63342 are for frontend
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://127.0.0.1:8080", "http://localhost:63342")); // frontend origin added
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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