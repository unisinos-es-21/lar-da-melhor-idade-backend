package com.example.demo.controller;

import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.exception.RecordsNotFoundException;
import com.example.demo.service.InstitutionalizedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/institutionalized")
@RequiredArgsConstructor
public class InstitutionalizedController {

    private final InstitutionalizedService institutionalizedService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<InstitutionalizedEntity> findAll() {
        List<InstitutionalizedEntity> institutionalizedEntities = institutionalizedService.findAll();
        if (institutionalizedEntities.isEmpty()) {
            throw new RecordsNotFoundException();
        } else {
            return institutionalizedEntities;
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InstitutionalizedEntity create(@Valid @RequestBody InstitutionalizedEntity institutionalized) {
        return institutionalizedService.create(institutionalized);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InstitutionalizedEntity update(@PathVariable Long id, @Valid @RequestBody InstitutionalizedEntity institutionalized) {
        return institutionalizedService.update(id, institutionalized);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        institutionalizedService.delete(id);
    }

}
