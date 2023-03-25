package com.orderpicker.security.auth.service;

import com.orderpicker.security.auth.dto.AuthenticationRequest;
import com.orderpicker.security.auth.response.AuthenticationResponse;
import com.orderpicker.security.config.JwtService;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = this.jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .message("Login successfully")
                .token(jwtToken)
                .build();
    }
}
