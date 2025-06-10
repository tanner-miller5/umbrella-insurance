package com.umbrella.insurance.core.models.trophies.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Trophy;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.trophies.v1.db.jpa.TrophyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrophiesTableTests {
    private static String trophyName = "1234";
    private static String updatedTrophyName = "12345";
    private static Trophy trophy = new Trophy();
    private static Trophy updatedTrophy = new Trophy();
    static {
        trophy.setTrophyName(trophyName);

        updatedTrophy.setTrophyName(updatedTrophyName);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();

    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Autowired
    private TrophyService trophyService;

    @Test
    @Order(2)
    void insertTrophyTest() throws SQLException {
        trophy = trophyService.saveTrophy(trophy);
        updatedTrophy.setId(trophy.getId());
    }
    @Test
    @Order(3)
    void selectTrophyByTrophyNameTest() throws SQLException {
        Trophy trophy1 = trophyService.getTrophyByTrophyName(trophyName).get();
        assertTrue(trophy1.getTrophyName().equals(trophyName));
    }
    @Test
    @Order(4)
    void updateTrophyByTrophyNameTest() throws SQLException {
        updatedTrophy = trophyService.updateTrophy(updatedTrophy);
    }
    @Test
    @Order(5)
    void selectUpdatedTrophyByTrophyNameTest() throws SQLException {
        Trophy trophy1 = trophyService.getTrophyByTrophyName(updatedTrophyName).get();
        assertTrue(trophy1.getTrophyName().equals(updatedTrophyName));
    }
    @Test
    @Order(6)
    void deleteTrophyByTrophyNameTest() throws SQLException {
        trophyService.deleteTrophy(updatedTrophy.getId());
    }

}
