package com.umbrella.insurance.core.models.geographies.continents.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Continent;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.geographies.continents.v1.db.jpa.ContinentService;
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
public class ContinentsTableTests {
    private static String continentName = "1234";
    private static String updatedContinentName = "12345";
    private static Continent continent = new Continent();
    private static Continent updatedContinent = new Continent();
    static {
        continent.setContinentName(continentName);

        updatedContinent.setContinentName(updatedContinentName);
    }

    @Autowired
    private ContinentService continentService;

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
    void insertContinentTest() throws SQLException {
        continent = continentService.saveContinent(continent);
        updatedContinent.setId(continent.getId());
    }
    @Test
    @Order(3)
    void selectContinentByContinentNameTest() throws SQLException {
        Continent continent1 = continentService.getContinentByContinentName(continentName).get();
        assertTrue(continent1.getContinentName().equals(continentName));
    }
    @Test
    @Order(4)
    void updateContinentByContinentNameTest() throws SQLException {
        updatedContinent = continentService
                .updateContinent(
                        updatedContinent);
    }
    @Test
    @Order(5)
    void selectUpdatedContinentByContinentNameTest() throws SQLException {
        Continent continent1 = continentService
                .getContinentByContinentName(updatedContinentName).get();
        assertTrue(continent1.getContinentName().equals(updatedContinentName));
    }
    @Test
    @Order(6)
    void deleteContinentByContinentNameTest() throws SQLException {
        continentService.deleteContinent(updatedContinent.getId());
    }

}
