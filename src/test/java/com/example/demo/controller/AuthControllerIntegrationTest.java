package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AuthenticateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void doBefore() {
        super.doBefore();
        this.userRepository.deleteAll();
        this.createAndSaveUserToLogin();
    }

    public void createAndSaveUserToLogin() {
        this.userRepository.save(UserEntity.builder()
                .username("user1")
                .password(new BCryptPasswordEncoder().encode("blahblahblah"))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build());
    }

    @Test
    @DisplayName("Cenário 1 - Login com sucesso retorna status 200")
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
    @DisplayName("Cenário 2 - Login sem sucesso retorna status 401")
    void loginUnauthorized() throws Exception {
        AuthenticateRequest authenticateRequest = AuthenticateRequest.builder()
                .username("user2")
                .password("xxxxxxxxx")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(authenticateRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}