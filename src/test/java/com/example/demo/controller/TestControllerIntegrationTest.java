package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TestControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void successTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/test/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Hello test message"));
    }

}