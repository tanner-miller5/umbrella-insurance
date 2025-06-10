package com.umbrella.insurance.core.models.weeks.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Week;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.weeks.v1.db.jpa.WeekService;
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
public class WeeksTableTests {
    private static Long weekNumber = 1l;
    private static String weekTitle = "1";
    private static Date weekStartDate = Date.valueOf("2023-12-12");
    private static Date weekEndDate = Date.valueOf("2024-05-20");
    private static Long updatedWeekNumber = 2l;
    private static String updatedWeekTitle = "2";
    private static Date updatedWeekStartDate = Date.valueOf("2021-12-12");
    private static Date updatedWeekEndDate = Date.valueOf("2020-12-12");
    private static Week week = new Week();
    private static Week updatedWeek = new Week();
    static {
        week.setWeekNumber(weekNumber);
        week.setWeekTitle(weekTitle);
        week.setWeekStartDate(weekStartDate);
        week.setWeekEndDate(weekEndDate);

        updatedWeek.setWeekNumber(updatedWeekNumber);
        updatedWeek.setWeekTitle(updatedWeekTitle);
        updatedWeek.setWeekStartDate(updatedWeekStartDate);
        updatedWeek.setWeekEndDate(updatedWeekEndDate);
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
    private WeekService weekService;

    @Test
    @Order(2)
    void insertWeekTest() throws SQLException {
        week = weekService.saveWeek(week);
        updatedWeek.setId(week.getId());
    }
    @Test
    @Order(3)
    void selectWeekByWeekTitleTest() throws SQLException {
        Week week1 = weekService.getWeekByWeekTitle(weekTitle).get();
        assertTrue(week1.getWeekTitle().equals(weekTitle));
        assertTrue(week1.getWeekNumber().equals(weekNumber));
        assertTrue(week1.getWeekStartDate().equals(weekStartDate));
        assertTrue(week1.getWeekEndDate().equals(weekEndDate));
    }
    @Test
    @Order(4)
    void updateWeekByWeekNameTest() throws SQLException {
        updatedWeek = weekService.updateWeek(updatedWeek);
    }
    @Test
    @Order(5)
    void selectUpdatedWeekByWeekTitleTest() throws SQLException {
        Week week1 = weekService.getWeekByWeekTitle(updatedWeek.getWeekTitle()).get();
        assertTrue(week1.getWeekTitle().equals(updatedWeekTitle));
        assertTrue(week1.getWeekNumber().equals(updatedWeekNumber));
        assertTrue(week1.getWeekStartDate().equals(updatedWeekStartDate));
        assertTrue(week1.getWeekEndDate().equals(updatedWeekEndDate));
    }
    @Test
    @Order(6)
    void deleteWeekByWeekNameTest() throws SQLException {
        weekService.deleteWeek(updatedWeek.getId());
    }

}
