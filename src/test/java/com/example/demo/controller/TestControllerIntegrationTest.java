package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TestControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void doBefore() {
        super.doBefore();
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar mensagem de teste")
    @WithMockUser(username = "samuel", authorities = {"ADMIN"})
    void successTest() throws Exception {
        this.userRepository.save(UserEntity.builder()
                .username("samuel")
                .password(new BCryptPasswordEncoder().encode("123"))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/test/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Hello test message"));
    }

}