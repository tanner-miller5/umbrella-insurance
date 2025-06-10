package com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db;

import com.umbrella.insurance.core.models.entities.SeasonType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
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
public class SeasonTypesTableTests {
    private static String seasonTypeName = "1234";
    private static String updatedSeasonTypeName = "12345";
    private static SeasonType seasonType = new SeasonType();
    private static SeasonType updatedSeasonType = new SeasonType();
    static {
        seasonType.setSeasonTypeName(seasonTypeName);

        updatedSeasonType.setSeasonTypeName(updatedSeasonTypeName);
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
    private SeasonTypeService seasonTypeService;

    @Test
    @Order(2)
    void insertSeasonTypeTest() throws SQLException {
        seasonType = seasonTypeService.saveSeasonType(seasonType);
        updatedSeasonType.setId(seasonType.getId());
    }
    @Test
    @Order(3)
    void selectSeasonTypeBySeasonTypeNameTest() throws SQLException {
        SeasonType seasonType1 = seasonTypeService.getSeasonTypeBySeasonTypeName(
                seasonTypeName).get();
        assertTrue(seasonType1.getSeasonTypeName().equals(seasonTypeName));
    }
    @Test
    @Order(4)
    void updateSeasonTypeBySeasonTypeNameTest() throws SQLException {
        updatedSeasonType= seasonTypeService.updateSeasonType(updatedSeasonType);
    }
    @Test
    @Order(5)
    void selectUpdatedSeasonTypeBySeasonTypeNameTest() throws SQLException {
        SeasonType seasonType1 = seasonTypeService.getSeasonTypeBySeasonTypeName(
                updatedSeasonTypeName).get();
        assertTrue(seasonType1.getSeasonTypeName().equals(updatedSeasonTypeName));
    }
    @Test
    @Order(6)
    void deleteSeasonTypeBySeasonTypeNameTest() throws SQLException {
        seasonTypeService.deleteSeasonType(updatedSeasonType.getId());
    }


}
