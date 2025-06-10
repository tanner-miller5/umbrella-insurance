package com.umbrella.insurance.core.models.geographies.cities.v1.db;

import com.umbrella.insurance.core.models.entities.City;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
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
public class CitiesTableTests {
    private static String cityName = "1234";
    private static String updatedCityName = "12345";
    private static City city = new City();
    private static City updatedCity = new City();
    static {
        city.setCityName(cityName);

        updatedCity.setCityName(updatedCityName);

    }

    @Autowired
    private CityService cityService;

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
    void insertCityTest() throws SQLException {
        city = cityService.saveCity(city);
        updatedCity.setId(city.getId());
    }
    @Test
    @Order(3)
    void selectCityByCityNameTest() throws SQLException {
        City city1 = cityService.getCityByCityName(cityName).get();
        assertTrue(city1.getCityName().equals(cityName));
    }
    @Test
    @Order(4)
    void updateCityByCityNameTest() throws SQLException {
        updatedCity = cityService.updateCity(
                updatedCity);
    }
    @Test
    @Order(5)
    void selectUpdatedCityByCityNameTest() throws SQLException {
        City city1 = cityService.getCityByCityName(
                updatedCityName).get();
        assertTrue(city1.getCityName().equals(updatedCityName));
    }
    @Test
    @Order(6)
    void deleteCityByCityNameTest() throws SQLException {
        cityService.deleteCity(
                updatedCity.getId());
    }
}
