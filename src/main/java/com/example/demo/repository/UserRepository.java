package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findOneByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

    @Modifying
    @Query("update UserEntity u set u.enabled = ?2 where u.id = ?1")
    void updateUserEnabled(Long id, boolean enabled);

    List<UserEntity> findAllByEnabledTrue();
}
