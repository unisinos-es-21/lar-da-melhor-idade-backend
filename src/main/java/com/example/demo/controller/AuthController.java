package com.example.demo.controller;

import com.example.demo.request.AuthenticateRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.response.AuthenticateResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest.getUsername(), registerRequest.getPassword());
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AuthenticateResponse authenticate(@Valid @RequestBody AuthenticateRequest authenticateRequest) {
        return authService.authenticate(authenticateRequest.getUsername(), authenticateRequest.getPassword());
    }

}
