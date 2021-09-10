package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameDuplicatedException extends ResponseStatusException {

    private static final String USERNAME_DUPLICATED_MESSAGE_PATTERN = "Usuário %s já cadastrado";

    public UsernameDuplicatedException(String username) {
        super(HttpStatus.CONFLICT, String.format(USERNAME_DUPLICATED_MESSAGE_PATTERN, username));
    }
}
