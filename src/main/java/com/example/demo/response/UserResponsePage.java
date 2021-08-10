package com.example.demo.response;

import com.example.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponsePage {

    private UserResponsePage() {
        throw new IllegalStateException("Utility class");
    }

    public static Page<UserResponse> build(Page<UserEntity> page) {
        List<UserResponse> userResponses = page.getContent().stream().map(UserResponse::new).collect(Collectors.toList());
        return new PageImpl<>(userResponses, page.getPageable(), userResponses.size());
    }

}
