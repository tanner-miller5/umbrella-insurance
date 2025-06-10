package com.umbrella.insurance.core.models.databases;

import com.umbrella.insurance.core.models.ads.v1.db.AdsTable;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

import static com.umbrella.insurance.core.models.databases.v1.Database.createConnectionWithEnv;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTests {
    private static final String newDatabase = "tanner_test";
    @Test
    @Order(1)
    void createConnectionWithEnvTest() {
        Connection connection = createConnectionWithEnv(EnvironmentEnum.TEST);
        assertTrue(connection != null);
        Database.closeConnection(connection);
    }
    @Test
    @Order(2)
    void closeConnectionTest() throws SQLException {
        Connection connection = createConnectionWithEnv(EnvironmentEnum.TEST);
        assertTrue(connection != null);
        Database.closeConnection(connection);
        assertTrue(connection.isClosed());
    }
    //@Test
    //@Order(3)
    void createDatabaseByNameTest() {
        Connection connection = null;
        try {
            connection = createConnectionWithEnv(EnvironmentEnum.TEST);
            Database.createDatabaseByName(newDatabase, connection);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
    //@Test
    //@Order(4)
    void doesDatabaseExistsTest() {
        Connection connection = null;
        try {
            connection = createConnectionWithEnv(EnvironmentEnum.TEST);
            boolean doesDatabaseExists = Database.doesDatabaseExist(newDatabase, connection);
            assertTrue(doesDatabaseExists);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }

    @Test
    @Order(5)
    void doesTableExistsTest() {
        Connection connection = null;
        try {
            connection = createConnectionWithEnv(EnvironmentEnum.TEST);
            boolean doesDatabaseExists = Database.doesTableExist(AdsTable.ADS_TABLE_NAME, connection);
            assertTrue(doesDatabaseExists);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
    @Test
    @Order(7)
    void dropDatabaseByNameTest() {
        Connection connection = null;
        try {
            connection = createConnectionWithEnv(EnvironmentEnum.TEST);
            Database.dropDatabaseByName(newDatabase, connection);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
}
