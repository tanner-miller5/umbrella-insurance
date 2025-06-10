package com.umbrella.insurance.core.models.people.analysts.specialties.v1.db;

import com.umbrella.insurance.core.models.entities.Specialty;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
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
public class SpecialtiesTableTests {
    private static String specialtyName = "1234";
    private static String updatedSpecialtyName = "12345";
    private static Specialty specialty = new Specialty();
    private static Specialty updatedSpecialty = new Specialty();
    static {
        specialty.setSpecialtyName(specialtyName);

        updatedSpecialty.setSpecialtyName(updatedSpecialtyName);
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
    private SpecialtyService specialtyService;

    @Test
    @Order(2)
    void insertSpecialtyTest() throws SQLException {
        specialty = specialtyService.saveSpecialty(specialty);
        updatedSpecialty.setId(specialty.getId());
    }
    @Test
    @Order(3)
    void selectSpecialtyBySpecialtyNameTest() throws SQLException {
        Specialty specialty1 = specialtyService
                .getSpecialtyBySpecialtyName(specialtyName).get();
        assertTrue(specialty1.getSpecialtyName().equals(specialtyName));
    }
    @Test
    @Order(4)
    void updateSpecialtyBySpecialtyNameTest() throws SQLException {
        updatedSpecialty = specialtyService
                .updateSpecialty(updatedSpecialty);
    }
    @Test
    @Order(5)
    void selectUpdatedSpecialtyBySpecialtyNameTest() throws SQLException {
        Specialty specialty1 = specialtyService
                .getSpecialtyBySpecialtyName(updatedSpecialtyName).get();
        assertTrue(specialty1.getSpecialtyName().equals(updatedSpecialtyName));
    }
    @Test
    @Order(6)
    void deleteSpecialtyBySpecialtyNameTest() throws SQLException {
        specialtyService
                .deleteSpecialty(updatedSpecialty.getId());
    }
}
