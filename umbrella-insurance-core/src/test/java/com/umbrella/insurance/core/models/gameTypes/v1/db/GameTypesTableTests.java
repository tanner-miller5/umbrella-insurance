package com.umbrella.insurance.core.models.gameTypes.v1.db;

import com.umbrella.insurance.core.models.entities.GameType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
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
public class GameTypesTableTests {
    private static String gameTypeName = "1234";
    private static String updatedGameTypeName = "12345";
    private static GameType gameType = new GameType();
    private static GameType updatedGameType = new GameType();
    static {
        gameType.setGameTypeName(gameTypeName);

        updatedGameType.setGameTypeName(updatedGameTypeName);

    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private GameTypeService gameTypeService;

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
    void insertGameTypeTest() throws SQLException {
        gameType = gameTypeService.saveGameType(gameType);
        updatedGameType.setId(gameType.getId());
    }
    @Test
    @Order(3)
    void selectGameTypeByGameTypeNameTest() throws SQLException {
        GameType gameType1 = gameTypeService.getGameTypeByGameTypeName(gameTypeName).get();
        assertTrue(gameType1.getGameTypeName().equals(gameTypeName));
    }
    @Test
    @Order(4)
    void updateGameTypeByGameTypeNameTest() throws SQLException {
        updatedGameType = gameTypeService
                .updateGameType(updatedGameType);
    }
    @Test
    @Order(5)
    void selectUpdatedGameTypeByGameTypeNameTest() throws SQLException {
        GameType gameType1 = gameTypeService
                .getGameTypeByGameTypeName(updatedGameTypeName).get();
        assertTrue(gameType1.getGameTypeName().equals(updatedGameTypeName));
    }
    @Test
    @Order(6)
    void deleteGameTypeByGameTypeNameTest() throws SQLException {
        gameTypeService
                .deleteGameType(updatedGameType.getId());
    }
}
