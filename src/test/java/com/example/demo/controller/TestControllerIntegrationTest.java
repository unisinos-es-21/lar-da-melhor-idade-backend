package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TestControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Deve retornar mensagem de teste")
    @WithMockUser(username = "user1", authorities = {"ADMIN"})
    void successTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/test/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Hello test message"));
    }

}