package com.example.demo.request;


import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
