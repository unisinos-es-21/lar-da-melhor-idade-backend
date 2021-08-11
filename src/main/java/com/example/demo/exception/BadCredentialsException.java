package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadCredentialsException extends ResponseStatusException {

    public static final String INVALID_CREDENTIALS = "Credenciais inválidas";

    public BadCredentialsException() {
        super(HttpStatus.BAD_REQUEST, INVALID_CREDENTIALS);
    }

}
