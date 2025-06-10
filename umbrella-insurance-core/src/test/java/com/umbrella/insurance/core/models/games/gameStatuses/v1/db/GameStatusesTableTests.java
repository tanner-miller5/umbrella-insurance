package com.umbrella.insurance.core.models.games.gameStatuses.v1.db;

import com.umbrella.insurance.core.models.entities.GameStatus;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.games.gameStatuses.v1.db.jpa.GameStatusService;
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
public class GameStatusesTableTests {
    private static String gameStatusName = "1234";
    private static String updatedGameStatusName = "12345";
    private static GameStatus gameStatus = new GameStatus();
    private static GameStatus updatedGameStatus = new GameStatus();
    static {
        gameStatus.setGameStatusName(gameStatusName);

        updatedGameStatus.setGameStatusName(updatedGameStatusName);

    }

    @Autowired
    GameStatusService gameStatusService;


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

    @Test
    @Order(2)
    void insertGameStatusTest() throws SQLException {
        gameStatus = gameStatusService.saveGameStatus(
                gameStatus);
        updatedGameStatus.setId(gameStatus.getId());
    }
    @Test
    @Order(3)
    void selectGameStatusByGameStatusNameTest() throws SQLException {
        GameStatus gameStatus1 = gameStatusService
                .getGameStatus(gameStatusName).get();
        assertTrue(gameStatus1.getGameStatusName().equals(gameStatusName));
    }
    @Test
    @Order(4)
    void updateGameStatusByGameStatusNameTest() throws SQLException {
        updatedGameStatus = gameStatusService
                .updateGameStatus(
                        updatedGameStatus);
    }
    @Test
    @Order(5)
    void selectUpdatedGameStatusByGameStatusNameTest() throws SQLException {
        GameStatus gameStatus1 = gameStatusService
                .getGameStatus(
                        updatedGameStatusName).get();
        assertTrue(gameStatus1.getGameStatusName().equals(updatedGameStatusName));
    }
    @Test
    @Order(6)
    void deleteGameStatusByGameStatusNameTest() throws SQLException {
        gameStatusService
                .deleteGameStatus(
                        updatedGameStatus.getId());
    }


}
