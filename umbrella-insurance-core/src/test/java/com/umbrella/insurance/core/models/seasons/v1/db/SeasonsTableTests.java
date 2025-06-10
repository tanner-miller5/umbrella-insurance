package com.umbrella.insurance.core.models.seasons.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Season;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeasonsTableTests {
    private static String seasonName = "1234";
    private static Date startDate = Date.valueOf("2023-11-11");
    private static Date endDate = Date.valueOf("2024-12-12");
    private static String updatedSeasonName = "12345";
    private static Date updatedStartDate = Date.valueOf("2022-11-11");
    private static Date updatedEndDate = Date.valueOf("2021-12-12");
    private static Season season = new Season();
    private static Season updatedSeason = new Season();
    static {
        season.setSeasonName(seasonName);
        season.setStartDate(startDate);
        season.setEndDate(endDate);

        updatedSeason.setSeasonName(updatedSeasonName);
        updatedSeason.setStartDate(updatedStartDate);
        updatedSeason.setEndDate(updatedEndDate);
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
    private SeasonService seasonService;

    @Test
    @Order(2)
    void insertSeasonTest() throws SQLException {
        season = seasonService.saveSeason(season);
        updatedSeason.setId(season.getId());
    }
    @Test
    @Order(3)
    void selectSeasonBySeasonNameTest() throws SQLException {
        Season season1 = seasonService.getSeasonBySeasonName(seasonName).get();
        assertTrue(season1.getSeasonName().equals(seasonName));
        assertTrue(season1.getStartDate().equals(startDate));
        assertTrue(season1.getEndDate().equals(endDate));
    }
    @Test
    @Order(4)
    void updateSeasonBySeasonNameTest() throws SQLException {
        updatedSeason = seasonService.updateSeason(updatedSeason);
    }
    @Test
    @Order(5)
    void selectUpdatedSeasonBySeasonNameTest() throws SQLException {
        Season season1 = seasonService.getSeasonBySeasonName(updatedSeasonName).get();
        assertTrue(season1.getSeasonName().equals(updatedSeasonName));
        assertTrue(season1.getStartDate().equals(updatedStartDate));
        assertTrue(season1.getEndDate().equals(updatedEndDate));
    }
    @Test
    @Order(6)
    void deleteSeasonBySeasonNameTest() throws SQLException {
        seasonService.deleteSeason(updatedSeason.getId());
    }
}
