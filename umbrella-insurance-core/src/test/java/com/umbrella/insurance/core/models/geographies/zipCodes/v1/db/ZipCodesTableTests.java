package com.umbrella.insurance.core.models.geographies.zipCodes.v1.db;

import com.umbrella.insurance.core.models.entities.ZipCode;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
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
public class ZipCodesTableTests {
    private static String zipCodeValue = "1234";
    private static String updatedZipCodeValue = "12345";
    private static ZipCode zipCode = new ZipCode();
    private static ZipCode updatedZipCode = new ZipCode();
    static {
        zipCode.setZipCodeValue(zipCodeValue);

        updatedZipCode.setZipCodeValue(updatedZipCodeValue);

    }

    @Autowired
    ZipCodeService zipCodeService;

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
    void insertZipCodeTest() throws SQLException {
        zipCode = zipCodeService.saveZipCode(zipCode);
        updatedZipCode.setId(zipCode.getId());
    }
    @Test
    @Order(3)
    void selectZipCodeByZipCodeValueTest() throws SQLException {
        ZipCode zipCode1 = zipCodeService
                .getZipCodeByZipCodeValue(zipCodeValue).get();
        assertTrue(zipCode1.getZipCodeValue().equals(zipCodeValue));
    }
    @Test
    @Order(4)
    void updateZipCodeByZipCodeValueTest() throws SQLException {
        zipCodeService.updateZipCode(updatedZipCode);
    }
    @Test
    @Order(5)
    void selectUpdatedZipCodeByZipCodeValueTest() throws SQLException {
        ZipCode zipCode1 = zipCodeService
                .getZipCodeByZipCodeValue(updatedZipCodeValue).get();
        assertTrue(zipCode1.getZipCodeValue().equals(updatedZipCodeValue));
    }
    @Test
    @Order(6)
    void deleteZipCodeByZipCodeValueTest() throws SQLException {
        zipCodeService.deleteZipCode(
                updatedZipCode.getId());
    }
}
