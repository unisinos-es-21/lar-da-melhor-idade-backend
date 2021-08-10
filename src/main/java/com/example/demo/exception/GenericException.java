package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GenericException extends ResponseStatusException {

    public GenericException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public GenericException(String message, Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, message, throwable);
    }

}
