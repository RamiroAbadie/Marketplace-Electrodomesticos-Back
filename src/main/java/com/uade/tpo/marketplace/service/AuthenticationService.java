package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.dto.UserResponse;
import com.uade.tpo.marketplace.entity.dto.auth.AuthenticationRequest;
import com.uade.tpo.marketplace.entity.dto.auth.AuthenticationResponse;
import com.uade.tpo.marketplace.entity.dto.auth.RegisterRequest;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Transactional
        public AuthenticationResponse register(RegisterRequest request) {
                // Asegurate de tener importado correctamente el enum Role
                User user = User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER) // ← rol fijo desde backend
                        .build();
            
                userRepository.save(user);
            
                String jwtToken = jwtService.generateToken(user);
            
                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

                var user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow();

                var jwtToken = jwtService.generateToken(user);

                // Convertir user a UserResponse
                var userResponse = UserResponse.builder()
                        .id(user.getId())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build();

                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .user(userResponse)
                        .build();
        }
}
