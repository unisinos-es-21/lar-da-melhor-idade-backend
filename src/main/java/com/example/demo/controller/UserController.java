package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.RecordsNotFoundException;
import com.example.demo.request.AddUserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.response.UserResponsePage;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<UserEntity> page = userService.findAll(pageable);
        if (CollectionUtils.isEmpty(page.getContent())) {
            throw new RecordsNotFoundException();
        } else {
            return UserResponsePage.build(page);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody AddUserRequest addUserRequest) {
        UserEntity userEntity = userService.createUser(addUserRequest);
        return new UserResponse(userEntity);
    }

}
