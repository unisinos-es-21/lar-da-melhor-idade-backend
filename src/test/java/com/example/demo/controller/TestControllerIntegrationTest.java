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
    @DisplayName("Deve retornar mensagem de teste")
    @WithMockUser(username = "user1", authorities = {"ADMIN"})
    void successTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/test/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Hello test message"));
    }

}