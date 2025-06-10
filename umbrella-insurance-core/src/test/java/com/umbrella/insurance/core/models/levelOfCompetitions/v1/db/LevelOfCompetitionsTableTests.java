package com.umbrella.insurance.core.models.levelOfCompetitions.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.LevelOfCompetition;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa.LevelOfCompetitionService;
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
public class LevelOfCompetitionsTableTests {
    private static String levelOfCompetitionName = "1234";
    private static String updatedLevelOfCompetitionName = "12345";
    private static LevelOfCompetition levelOfCompetition = new LevelOfCompetition();
    private static LevelOfCompetition updatedLevelOfCompetition = new LevelOfCompetition();
    static {
        levelOfCompetition.setLevelOfCompetitionName(levelOfCompetitionName);

        updatedLevelOfCompetition.setLevelOfCompetitionName(updatedLevelOfCompetitionName);
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
    private LevelOfCompetitionService levelOfCompetitionService;

    @Test
    @Order(2)
    void insertLevelOfCompetitionTest() throws SQLException {
        levelOfCompetition = levelOfCompetitionService.saveLevelOfCompetition(levelOfCompetition);
        updatedLevelOfCompetition.setId(levelOfCompetition.getId());
    }
    @Test
    @Order(3)
    void selectLevelOfCompetitionByLevelOfCompetitionNameTest() throws SQLException {
        LevelOfCompetition levelOfCompetition1 =
                levelOfCompetitionService.getLevelOfCompetitionByLevelOfCompetitionName(
                        levelOfCompetitionName).get();
        assertTrue(levelOfCompetition1.getLevelOfCompetitionName().equals(levelOfCompetitionName));
    }
    @Test
    @Order(4)
    void updateLevelOfCompetitionByLevelOfCompetitionNameTest() throws SQLException {
        updatedLevelOfCompetition = levelOfCompetitionService
                .updateLevelOfCompetition(
                updatedLevelOfCompetition);
    }
    @Test
    @Order(5)
    void selectUpdatedLevelOfCompetitionByLevelOfCompetitionNameTest() throws SQLException {
        LevelOfCompetition levelOfCompetition1 =
                levelOfCompetitionService.getLevelOfCompetitionByLevelOfCompetitionName(
                        updatedLevelOfCompetitionName).get();
        assertTrue(levelOfCompetition1.getLevelOfCompetitionName().equals(updatedLevelOfCompetitionName));
    }
    @Test
    @Order(6)
    void deleteLevelOfCompetitionByLevelOfCompetitionNameTest() throws SQLException {
        levelOfCompetitionService.deleteLevelOfCompetition(updatedLevelOfCompetition.getId());
    }
}
