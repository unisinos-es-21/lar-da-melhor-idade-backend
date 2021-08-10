package com.example.demo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperty {

    private String secret;
    private int accessTokenValiditySeconds;
    private String password;

}
