package com.example.demo.response;

import com.example.demo.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserResponse {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private boolean enabled;

    public UserResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.enabled = userEntity.isEnabled();
    }
}
