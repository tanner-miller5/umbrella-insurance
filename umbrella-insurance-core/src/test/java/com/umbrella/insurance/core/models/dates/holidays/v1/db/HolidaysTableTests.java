package com.umbrella.insurance.core.models.dates.holidays.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.dates.holidays.v1.db.jpa.HolidayService;
import com.umbrella.insurance.core.models.entities.Holiday;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
public class HolidaysTableTests {
    private static String holidayName = "1234";
    private static Date date = Date.valueOf("2023-12-12");
    private static String updatedHolidayName = "12345";
    private static Date updatedDate = Date.valueOf("2024-11-11");
    private static Holiday holiday = new Holiday();
    private static Holiday updatedHoliday = new Holiday();
    static {
        holiday.setHolidayName(holidayName);
        holiday.setHolidayDate(date);

        updatedHoliday.setHolidayName(updatedHolidayName);
        updatedHoliday.setHolidayDate(updatedDate);

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
    private HolidayService holidayService;

    @Test
    @Order(2)
    void insertHolidayTest() throws SQLException {
        holiday = holidayService.saveHoliday(holiday);
        updatedHoliday.setId(holiday.getId());
    }
    @Test
    @Order(3)
    void selectHolidayByHolidayNameTest() throws SQLException {
        Holiday holiday1 = holidayService.getHolidayByHolidayName(holidayName).get();
        assertTrue(holiday1.getHolidayName().equals(holidayName));
    }
    @Test
    @Order(4)
    void updateHolidayByHolidayNameTest() throws SQLException {
        updatedHoliday = holidayService.updateHoliday(updatedHoliday);
    }
    @Test
    @Order(5)
    void selectUpdatedHolidayByHolidayNameTest() throws SQLException {
        Holiday holiday1 = holidayService.getHolidayByHolidayName(updatedHolidayName).get();
        assertTrue(holiday1.getHolidayName().equals(updatedHolidayName));
    }
    @Test
    @Order(6)
    void deleteHolidayByHolidayNameTest() throws SQLException {
        holidayService.deleteHoliday(updatedHoliday.getId());
    }

}
