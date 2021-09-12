package com.example.demo.service;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.enumerator.GenderEnum;
import com.example.demo.exception.CreateEntityIdNotNullException;
import com.example.demo.repository.InstitutionalizedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class InstitutionalizedServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private InstitutionalizedService institutionalizedService;

    @Autowired
    private InstitutionalizedRepository institutionalizedRepository;

    @Test
    @DisplayName("Deve gerar excessão ao tentar criar institucionalizado com id")
    void mustThrowExceptionWhenCreateInstitutionalizedWithId() {
        InstitutionalizedEntity institutionalized = InstitutionalizedEntity.builder()
                .id(789L)
                .name("Paulo dos Santos")
                .cpf("12345678910")
                .phone("(51) 98765 - 1452")
                .birthDay(LocalDate.of(1960, 12, 31))
                .gender(GenderEnum.MASCULINE)
                .build();

        CreateEntityIdNotNullException exception = Assertions.assertThrows(CreateEntityIdNotNullException.class, () -> institutionalizedService.create(institutionalized));
        Assertions.assertEquals(CreateEntityIdNotNullException.CREATE_ENTITY_ID_NOT_NULL_EXCEPTION_MESSAGE, exception.getReason());

        Assertions.assertEquals(0, institutionalizedRepository.findAll().size());
    }

}