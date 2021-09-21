package com.example.demo.service;

import com.example.demo.response.AuthenticateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

//    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticateResponse authenticate(String username, String password) {

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

//        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var userDetails = this.userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenService.generateToken(userDetails);
        var expirationDate = this.jwtTokenService.getExpirationDateFromToken(token);
        return AuthenticateResponse.builder()
                .token(token)
                .expires(expirationDate.getTime())
                .build();
    }
}
