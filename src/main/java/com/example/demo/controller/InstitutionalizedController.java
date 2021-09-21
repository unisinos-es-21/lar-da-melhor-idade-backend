package com.example.demo.controller;

import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.enumerator.GenderEnum;
import com.example.demo.exception.RecordsNotFoundException;
import com.example.demo.service.InstitutionalizedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/institutionalized")
@RequiredArgsConstructor
public class InstitutionalizedController {

    private final InstitutionalizedService institutionalizedService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<InstitutionalizedEntity> findAll(Pageable pageable,
                                                 @RequestParam(required = false, value = "id") Long id,
                                                 @RequestParam(required = false, value = "name") String name,
                                                 @RequestParam(required = false, value = "cpf") String cpf,
                                                 @RequestParam(required = false, value = "phone") String phone,
                                                 @RequestParam(required = false, value = "birthDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDay,
                                                 @RequestParam(required = false, value = "gender") GenderEnum gender) {
        Page<InstitutionalizedEntity> page = institutionalizedService.findAll(pageable, id, name, cpf, phone, birthDay, gender);
        if (CollectionUtils.isEmpty(page.getContent())) {
            throw new RecordsNotFoundException();
        } else {
            return page;
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
