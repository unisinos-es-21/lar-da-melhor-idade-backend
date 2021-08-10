package com.example.demo;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

    public PostgresContainer() {
        super("postgres:10");
    }

}
