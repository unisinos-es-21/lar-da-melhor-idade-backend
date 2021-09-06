package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreateEntityIdNotNullException extends ResponseStatusException {

    private static final String PARA_INSERIR_UM_REGISTRO_NAO_PODE_SER_INFORMADO_UM_ID = "Para inserir um registro não pode ser informado um ID";

    public CreateEntityIdNotNullException() {
        super(HttpStatus.BAD_REQUEST, PARA_INSERIR_UM_REGISTRO_NAO_PODE_SER_INFORMADO_UM_ID);
    }
}
