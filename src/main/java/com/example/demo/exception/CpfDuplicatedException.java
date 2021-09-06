package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CpfDuplicatedException extends ResponseStatusException {

    private static final String CPF_DUPLICATED_MESSAGE_PATTERN = "CPF %s já cadastrado";

    public CpfDuplicatedException(String cpf) {
        super(HttpStatus.CONFLICT, String.format(CPF_DUPLICATED_MESSAGE_PATTERN, cpf));
    }
}
