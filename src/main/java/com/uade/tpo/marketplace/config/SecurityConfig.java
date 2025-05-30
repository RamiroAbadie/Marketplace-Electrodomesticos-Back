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

import java.util.List;

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
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of("http://localhost:5173")); // Frontend
                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))
            .authorizeHttpRequests(req -> req
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers(HttpMethod.GET,
                                "/api/products/available",
                                "/api/products/category/**",
                                "/api/products/price-range",
                                "/api/products/price-less",
                                "/api/products/{id}"
                        ).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/orders/user/**").hasAuthority("ADMIN")
                .requestMatchers("/api/orders/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/api/users/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
