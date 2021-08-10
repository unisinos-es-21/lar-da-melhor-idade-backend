package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.GenericException;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            throw new GenericException("O usuário informado já existe");
        }
        return create(addUserRequest.getUsername(), addUserRequest.getPassword());
    }

    private UserEntity save(UserEntity userEntity) {
//        if (userEntity.getUsername().equalsIgnoreCase(AuditorAwareImpl.ANONYMOUS_USER)) {
//            throw new BadCredentialsException();
//        }
//        boolean existUsername = Objects.isNull(userEntity.getId()) ?
//                this.userRepository.existsByUsername(userEntity.getUsername()) :
//                this.userRepository.existsByUsernameAndIdNot(userEntity.getUsername(), userEntity.getId());
//        if (existUsername) {
//            throw new BadCredentialsException();
//        }
        return this.userRepository.save(userEntity);
    }

    public UserEntity create(String username, String password) {
        UserEntity userEntity = UserEntity.builder()
                .username(username)
//                .password(new BCryptPasswordEncoder().encode(password))
                .password(password)
//                .authorities(new HashSet<>(authorities))
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
