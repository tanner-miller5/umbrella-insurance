package com.umbrella.insurance.core;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlywayTest {
    @Autowired
    private Flyway flyway;

    public void migrateDatabase() {
        flyway.migrate();
    }

    @Test
    public void migrationSuccessTest() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
        migrateDatabase();
    }
}
