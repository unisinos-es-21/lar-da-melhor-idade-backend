package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserService userService;

    public void register(String username, String password) {
        userService.create(username, password);
    }

}
