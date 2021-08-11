package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders(HttpHeaders.LINK, HttpHeaders.LOCATION,
                        HttpHeaders.CONTENT_TYPE, HttpHeaders.CONTENT_DISPOSITION, HttpHeaders.CONTENT_LENGTH);
    }
}