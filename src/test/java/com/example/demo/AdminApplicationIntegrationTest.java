package com.example.demo;

import com.example.demo.controller.TestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AdminApplicationIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestController testController;

    @Test
    @DisplayName("Deve retornar controller instanciado")
    void main() {
        Assertions.assertNotNull(testController);
    }

}