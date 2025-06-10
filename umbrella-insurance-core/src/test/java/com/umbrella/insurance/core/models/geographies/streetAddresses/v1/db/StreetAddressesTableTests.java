package com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db;

import com.umbrella.insurance.core.models.entities.StreetAddress;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
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
public class StreetAddressesTableTests {
    private static String streetAddressLine1 = "1234";
    private static String streetAddressLine2 = "123444";
    private static String updatedStreetAddressLine1 = "12345";
    private static String updatedStreetAddressLine2 = "123456";
    private static StreetAddress streetAddress = new StreetAddress();
    private static StreetAddress updatedStreetAddress = new StreetAddress();
    static {
        streetAddress.setStreetAddressLine1(streetAddressLine1);
        streetAddress.setStreetAddressLine2(streetAddressLine2);

        updatedStreetAddress.setStreetAddressLine1(updatedStreetAddressLine1);
        updatedStreetAddress.setStreetAddressLine2(updatedStreetAddressLine2);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @Autowired
    private StreetAddressService streetAddressService;

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
    void insertStreetAddressTest() throws SQLException {
        streetAddress = streetAddressService.saveStreetAddress(streetAddress);
        updatedStreetAddress.setId(streetAddress.getId());
    }
    @Test
    @Order(3)
    void selectStreetAddressByStreetAddressLine1AndStreetAddressLine2Test() throws SQLException {
        StreetAddress streetAddress1 = streetAddressService.getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                streetAddressLine1,streetAddressLine2).get();
        assertTrue(streetAddress1.getStreetAddressLine1().equals(streetAddressLine1));
        assertTrue(streetAddress1.getStreetAddressLine2().equals(streetAddressLine2));
    }
    @Test
    @Order(4)
    void updateStreetAddressByStreetAddressLine1AndStreetAddressLine2Test() throws SQLException {
        updatedStreetAddress = streetAddressService
                .updateStreetAddress(
                updatedStreetAddress);
    }
    @Test
    @Order(5)
    void selectUpdatedStreetAddressByStreetAddressLine1AndStreetAddressLine2Test() throws SQLException {
        StreetAddress streetAddress1 = streetAddressService
                .getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                updatedStreetAddressLine1, updatedStreetAddressLine2).get();
        assertTrue(streetAddress1.getStreetAddressLine1().equals(updatedStreetAddressLine1));
        assertTrue(streetAddress1.getStreetAddressLine2().equals(updatedStreetAddressLine2));
    }
    @Test
    @Order(6)
    void deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2Test() throws SQLException {
        streetAddressService
                .deleteStreetAddress(updatedStreetAddress.getId());
    }
}
