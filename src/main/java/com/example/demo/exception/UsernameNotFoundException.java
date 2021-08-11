package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameNotFoundException extends ResponseStatusException {

    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    public UsernameNotFoundException() {
        super(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
    }
}
