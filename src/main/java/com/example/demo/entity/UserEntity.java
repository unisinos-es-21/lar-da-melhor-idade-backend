package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user", schema = "public")
@SequenceGenerator(name = "generator_id", sequenceName = "sequence_user", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractEntity {

    @NotNull
    @Column(name = "account_non_expired", nullable = false)
    boolean accountNonExpired;

    @NotNull
    @Column(name = "account_non_locked", nullable = false)
    boolean accountNonLocked;

    @NotNull
    @Column(name = "credentials_non_expired", nullable = false)
    boolean credentialsNonExpired;

    @NotNull
    @Column(nullable = false)
    boolean enabled;

    @NotNull
    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(length = 100, nullable = false)
    private String password;

}
