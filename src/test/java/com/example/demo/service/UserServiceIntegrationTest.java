package com.example.demo.service;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve salvar usuário no banco")
    void mustSaveUserInDB() {
        Assertions.assertEquals(0, userRepository.findAll().size());

        UserEntity userReturned = userService.create("user", "blahblahblah");

        Assertions.assertNotNull(userReturned);
        Assertions.assertEquals("user", userReturned.getUsername());
        Assertions.assertTrue(new BCryptPasswordEncoder().matches("blahblahblah", userReturned.getPassword()));
        Assertions.assertTrue(userReturned.isEnabled());

        List<UserEntity> usersInDB = userRepository.findAll();
        Assertions.assertEquals(1, usersInDB.size());
        Assertions.assertEquals("user", usersInDB.get(0).getUsername());
        Assertions.assertTrue(new BCryptPasswordEncoder().matches("blahblahblah", usersInDB.get(0).getPassword()));
        Assertions.assertTrue(usersInDB.get(0).isEnabled());

    }

}