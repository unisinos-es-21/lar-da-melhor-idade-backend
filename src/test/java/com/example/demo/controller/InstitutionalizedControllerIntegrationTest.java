package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.enumerator.GenderEnum;
import com.example.demo.exception.CpfDuplicatedException;
import com.example.demo.exception.RecordsNotFoundException;
import com.example.demo.repository.InstitutionalizedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

class InstitutionalizedControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private InstitutionalizedRepository institutionalizedRepository;

    //Pré condição
    @BeforeEach
    public void doBefore() {
        super.doBefore();
        this.institutionalizedRepository.deleteAll();
    }

    @Test
    @DisplayName("Cenário 1 - Cadastro de institucionalizado com sucesso")
    @WithMockUser(username = "user1", authorities = {"ADMIN"})
    void successOnCreateInstitutionalized() throws Exception {
        Assertions.assertEquals(0, institutionalizedRepository.findAll().size());
        InstitutionalizedEntity institutionalized = InstitutionalizedEntity.builder()
                .name("José da Silva")
                .cpf("12345678910")
                .phone("(51) 98765 - 1452")
                .birthDay(LocalDate.of(1960, 12, 31))
                .gender(GenderEnum.MASCULINE)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/institutionalized")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(institutionalized)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("José da Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("12345678910"));

        List<InstitutionalizedEntity> institutionalizedInDB = institutionalizedRepository.findAll();
        Assertions.assertEquals(1, institutionalizedInDB.size());
        Assertions.assertNotNull(institutionalizedInDB.get(0).getId());
        Assertions.assertEquals("José da Silva", institutionalizedInDB.get(0).getName());
        Assertions.assertEquals("12345678910", institutionalizedInDB.get(0).getCpf());
    }

    @Test
    @DisplayName("Cenário 2 - Nenhum registro de institucionalizado encontrado")
    @WithMockUser(username = "user1", authorities = {"ADMIN"})
    void mustReturnNotFoundWhenInstitutionalizedIsEmpty() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/institutionalized"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof RecordsNotFoundException);
                    RecordsNotFoundException exception = (RecordsNotFoundException) result.getResolvedException();
                    Assertions.assertEquals("Nenhum registro encontrado", exception.getReason());
                });
    }

    @Test
    @DisplayName("Cenário 3 - CPF duplicado")
    @WithMockUser(username = "user1", authorities = {"ADMIN"})
    void cpfDuplicatedMustReturnConflict() throws Exception {
        //Pré condição
        Assertions.assertEquals(0, institutionalizedRepository.findAll().size());

        institutionalizedRepository.save(InstitutionalizedEntity.builder()
                .name("José da Silva")
                .cpf("12345678910")
                .phone("(51) 98765 - 1452")
                .birthDay(LocalDate.of(1960, 12, 31))
                .gender(GenderEnum.MASCULINE)
                .build());

        List<InstitutionalizedEntity> institutionalizedInDBBefore = institutionalizedRepository.findAll();
        Assertions.assertEquals(1, institutionalizedInDBBefore.size());
        Assertions.assertNotNull(institutionalizedInDBBefore.get(0).getId());
        Assertions.assertEquals("José da Silva", institutionalizedInDBBefore.get(0).getName());
        Assertions.assertEquals("12345678910", institutionalizedInDBBefore.get(0).getCpf());

        //Teste
        mockMvc.perform(MockMvcRequestBuilders.post("/institutionalized")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(InstitutionalizedEntity.builder()
                                .name("Paulo dos Santos")
                                .cpf("12345678910")
                                .phone("(51) 98765 - 1452")
                                .birthDay(LocalDate.of(1960, 12, 31))
                                .gender(GenderEnum.MASCULINE)
                                .build())))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof CpfDuplicatedException);
                    CpfDuplicatedException cpfDuplicatedException = (CpfDuplicatedException) result.getResolvedException();
                    Assertions.assertEquals("CPF 12345678910 já cadastrado", cpfDuplicatedException.getReason());
                });

        //Pós condição
        List<InstitutionalizedEntity> institutionalizedInDBAfter = institutionalizedRepository.findAll();
        Assertions.assertEquals(1, institutionalizedInDBAfter.size());
        Assertions.assertNotNull(institutionalizedInDBAfter.get(0).getId());
        Assertions.assertEquals("José da Silva", institutionalizedInDBAfter.get(0).getName());
        Assertions.assertEquals("12345678910", institutionalizedInDBAfter.get(0).getCpf());
    }

}