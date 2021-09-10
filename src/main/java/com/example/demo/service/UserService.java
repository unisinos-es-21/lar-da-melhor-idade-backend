package com.example.demo.service;

import com.example.demo.configuration.AuditorAwareImpl;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.UsernameDuplicatedException;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserEntity createUser(AddUserRequest addUserRequest) {
        boolean existUsername = this.userRepository.existsByUsername(addUserRequest.getUsername());
        if (existUsername) {
            throw new UsernameDuplicatedException(addUserRequest.getUsername());
        }
        return create(addUserRequest.getUsername(), addUserRequest.getPassword());
    }

    public UserEntity findOneByUsername(String username) {
        return this.userRepository.findOneByUsername(username);
    }

    private UserEntity save(UserEntity userEntity) {
        if (userEntity.getUsername().equalsIgnoreCase(AuditorAwareImpl.ANONYMOUS_USER)) {
            throw new ForbiddenException();
        }
        boolean existUsername = Objects.isNull(userEntity.getId()) ?
                this.userRepository.existsByUsername(userEntity.getUsername()) :
                this.userRepository.existsByUsernameAndIdNot(userEntity.getUsername(), userEntity.getId());
        if (existUsername) {
            throw new UsernameDuplicatedException(userEntity.getUsername());
        }
        return this.userRepository.save(userEntity);
    }

    public UserEntity create(String username, String password) {
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .createdBy("ADMIN")
                .createdDate(LocalDateTime.now())
                .lastModifiedBy("ADMIN")
                .lastModifiedDate(LocalDateTime.now())
                .build();
        return save(userEntity);
    }
}
