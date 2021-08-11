package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecordsNotFoundException extends ResponseStatusException {

    private static final String RECORD_NOT_FOUND = "Nenhum registro encontrado";
    private static final String RECORD_NOT_FOUND_BY_ID = "Não foi encontrado registro com o id %s";

    public RecordsNotFoundException() {
        super(HttpStatus.NOT_FOUND, RECORD_NOT_FOUND);
    }

    public RecordsNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public RecordsNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format(RECORD_NOT_FOUND_BY_ID, id));
    }

}
