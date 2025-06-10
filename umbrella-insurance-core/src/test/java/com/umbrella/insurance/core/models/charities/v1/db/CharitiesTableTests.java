package com.umbrella.insurance.core.models.charities.v1.db;

import com.umbrella.insurance.core.models.charities.v1.db.jpa.CharityService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Charity;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
public class CharitiesTableTests {
    private static String charityName = "1234";
    private static String updatedCharityName = "12345";
    private static Charity charity = new Charity();
    private static Charity updatedCharity = new Charity();
    static {
        charity.setCharityName(charityName);

        updatedCharity.setCharityName(updatedCharityName);

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
    CharityService charityService;

    @Test
    @Order(2)
    void insertCharityTest() throws SQLException {
        charity = charityService
                .saveCharity(charity);
        updatedCharity.setId(charity.getId());
    }
    @Test
    @Order(3)
    void selectCharityByCharityNameTest() throws SQLException {
        Charity charity1 = charityService.getCharityByCharityName(charityName).get();
        assertTrue(charity1.getCharityName().equals(charityName));
    }
    @Test
    @Order(4)
    void updateCharityByCharityNameTest() throws SQLException {
        updatedCharity = charityService.updateCharity(updatedCharity);
    }
    @Test
    @Order(5)
    void selectUpdatedCharityByCharityNameTest() throws SQLException {
        Charity charity1 = charityService
                .getCharityByCharityName(updatedCharityName).get();
        assertTrue(charity1.getCharityName().equals(updatedCharityName));
    }
    @Test
    @Order(6)
    void deleteCharityByCharityNameTest() throws SQLException {
        charityService
                .deleteCharity(updatedCharity.getId());
    }

}
