package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UpdateEntityIdNullException extends ResponseStatusException {

    public static final String UPDATE_ENTITY_ID_NULL_EXCEPTION_MESSAGE = "Para atualizar um registro é obrigatório informar um ID";

    public UpdateEntityIdNullException() {
        super(HttpStatus.BAD_REQUEST, UPDATE_ENTITY_ID_NULL_EXCEPTION_MESSAGE);
    }
}
