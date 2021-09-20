package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DifferentIDException extends ResponseStatusException {

    public static final String DIFFERENT_ID_EXCEPTION_MESSAGE = "Os ids informados não são iguais";

    public DifferentIDException() {
        super(HttpStatus.BAD_REQUEST, DIFFERENT_ID_EXCEPTION_MESSAGE);
    }
}
