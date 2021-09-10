package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {

    public static final String INVALID_CREDENTIALS = "Credenciais inválidas";

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS);
    }

}
