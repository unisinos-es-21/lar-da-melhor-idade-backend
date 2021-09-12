package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.request.AuthenticateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Cenário 1 - Login com sucesso")
    void loginSuccess() throws Exception {
        AuthenticateRequest authenticateRequest = AuthenticateRequest.builder()
                .username("user1")
                .password("blahblahblah")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(authenticateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.expires").exists());
    }

    @Test
    @DisplayName("Cenário 2 - Login sem sucesso")
    void loginUnauthorized() throws Exception {
        AuthenticateRequest authenticateRequest = AuthenticateRequest.builder()
                .username("user2")
                .password("xxxxxxxxx")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(authenticateRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof InternalAuthenticationServiceException);
                    InternalAuthenticationServiceException exception = (InternalAuthenticationServiceException) result.getResolvedException();
                    Assertions.assertEquals("401 UNAUTHORIZED \"Credenciais inválidas\"", exception.getMessage());
                });
    }

}