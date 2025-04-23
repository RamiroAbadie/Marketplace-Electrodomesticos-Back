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

                // Error y autenticación
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()

                // Categorías
                .requestMatchers("/categories").permitAll()
                .requestMatchers("/categories/**").hasAuthority("ADMIN")

                // Productos
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers("/api/products/**").hasAuthority("ADMIN")

                // Órdenes
                .requestMatchers(HttpMethod.GET, "/api/orders/user/**").hasAuthority("ADMIN")
                .requestMatchers("/api/orders/**").hasAnyAuthority("USER", "ADMIN")

                // Usuarios
                .requestMatchers("/api/users/**").hasAnyAuthority("USER", "ADMIN")

                // Todo lo demás requiere estar autenticado
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
