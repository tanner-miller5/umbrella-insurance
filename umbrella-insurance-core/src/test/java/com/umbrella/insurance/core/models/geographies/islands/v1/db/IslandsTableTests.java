package com.umbrella.insurance.core.models.geographies.islands.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Island;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.geographies.islands.v1.db.jpa.IslandService;
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
public class IslandsTableTests {
    private static String islandName = "1234";
    private static String updatedIslandName = "12345";
    private static Island island = new Island();
    private static Island updatedIsland = new Island();
    static {
        island.setIslandName(islandName);

        updatedIsland.setIslandName(updatedIslandName);
    }

    @Autowired
    private IslandService islandService;

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
    void insertIslandTest() throws SQLException {
        island = islandService.saveIsland(island);
        updatedIsland.setId(island.getId());
    }
    @Test
    @Order(3)
    void selectIslandByIslandNameTest() throws SQLException {
        Island island1 = islandService.getIslandByIslandName(islandName).get();
        assertTrue(island1.getIslandName().equals(islandName));
    }
    @Test
    @Order(4)
    void updateIslandByIslandNameTest() throws SQLException {
        updatedIsland = islandService
                .updateIsland(updatedIsland);
    }
    @Test
    @Order(5)
    void selectUpdatedIslandByIslandNameTest() throws SQLException {
        Island island1 = islandService
                .getIslandByIslandName(updatedIslandName).get();
        assertTrue(island1.getIslandName().equals(updatedIslandName));
    }
    @Test
    @Order(6)
    void deleteIslandByIslandIdTest() throws SQLException {
        islandService.deleteIsland(updatedIsland.getId());
    }

}
