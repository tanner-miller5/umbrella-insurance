package com.umbrella.insurance.core.models.databases;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

import static com.umbrella.insurance.core.constants.Database.TEST2_DATABASE_NAME;
import static com.umbrella.insurance.core.models.databases.v1.Database.createConnectionWithEnv;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TablesTests {
    private static String database = "";
    static{
        database = System.getenv().get(TEST2_DATABASE_NAME);
    }

    /**
     * CREATE DATABASE
     */
    @Test
    @Order(1)
    void createDatabaseByNameTest() {
        Connection connection = null;
        try {
            connection = createConnectionWithEnv(EnvironmentEnum.TEST);
            Database.createDatabaseByName(database, connection);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }

    /**
     * DROP DATABASE
     */
    @Test
    @Order(5001)
    void dropDatabaseByNameTest() {
        Connection connection = null;
        try {
            connection = createConnectionWithEnv(EnvironmentEnum.TEST);
            Database.dropDatabaseByName(database, connection);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
}
