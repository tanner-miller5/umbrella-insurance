package com.umbrella.insurance.core.models.geographies.countries.v1.db;

import com.umbrella.insurance.core.models.entities.Country;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
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
public class CountriesTableTests {
    private static String countryName = "1234";
    private static String updatedCountryName = "12345";
    private static Country country = new Country();
    private static Country updatedCountry = new Country();
    static {
        country.setCountryName(countryName);

        updatedCountry.setCountryName(updatedCountryName);

    }

    @Autowired
    private CountryService countryService;

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
    void insertCountryTest() throws SQLException {
        country = countryService.saveCountry(country);
        updatedCountry.setId(country.getId());
    }
    @Test
    @Order(3)
    void selectCountryByCountryNameTest() throws SQLException {
        Country country1 = countryService
                .findByCountryName(countryName).get();
        assertTrue(country1.getCountryName().equals(countryName));
    }
    @Test
    @Order(4)
    void updateCountryByCountryNameTest() throws SQLException {
        updatedCountry = countryService.updateCountry(
                updatedCountry);
    }
    @Test
    @Order(5)
    void selectUpdatedCountryByCountryNameTest() throws SQLException {
        Country country1 = countryService
                .findByCountryName(updatedCountryName).get();
        assertTrue(country1.getCountryName().equals(updatedCountryName));
    }
    @Test
    @Order(6)
    void deleteCountryByCountryNameTest() throws SQLException {
        countryService
                .deleteCountry(updatedCountry.getId());
    }


}
