package com.uade.tpo.marketplace.config;

import com.uade.tpo.marketplace.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req
                                                .requestMatchers("/error/**").permitAll()
                                                // AuthenticationController endpoints
                                                .requestMatchers("/api/v1/auth/**").permitAll()
                                                // CategoriesController endpoints
                                                .requestMatchers("/categories").permitAll()
                                                .requestMatchers("/categories/**").hasAnyAuthority("ADMIN")
                                                 // ProductController endpoints
                                                .requestMatchers(HttpMethod.GET, "/api/products").hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                                                .requestMatchers("/api/products/**").hasAuthority("ADMIN")
                                                // OrderController endpoints
                                                .requestMatchers("/api/orders/**").hasAnyAuthority("USER", "ADMIN")
                                                 // UserController endpoints
                                                .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAnyAuthority("USER")
                                                .requestMatchers("/api/users/**").hasAnyAuthority("ADMIN")
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
