package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreateEntityIdNotNullException extends ResponseStatusException {

    public static final String CREATE_ENTITY_ID_NOT_NULL_EXCEPTION_MESSAGE = "Para inserir um registro não pode ser informado um ID";

    public CreateEntityIdNotNullException() {
        super(HttpStatus.BAD_REQUEST, CREATE_ENTITY_ID_NOT_NULL_EXCEPTION_MESSAGE);
    }
}
