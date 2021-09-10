package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {

    private static final String FORBIDEN_MESSAGE = "Usuário sem permissões";

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, FORBIDEN_MESSAGE);
    }
}
