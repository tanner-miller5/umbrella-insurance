package com.umbrella.insurance.core.models.people.analysts.v1.db;

import com.umbrella.insurance.core.models.entities.Analyst;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.Specialty;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
import com.umbrella.insurance.core.models.people.analysts.v1.db.jpa.AnalystService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
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
public class AnalystsTableTests {
    private static Specialty specialty = new Specialty();
    private static Person person = new Person();
    private static Analyst analyst = new Analyst();
    private static Analyst updatedAnalyst = new Analyst();
    static {
        specialty.setSpecialtyName("test");
        person.setFirstName("test1");
        person.setMiddleName("test2");
        person.setSurname("test3");
        person.setDateOfBirth(Date.valueOf("2000-01-01"));
        person.setSsn("123456789012");

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

    @Autowired
    private PersonService personService;

    @Autowired
    private AnalystService analystService;

    @Test
    @Order(4)
    void insertAnalystTest() throws SQLException {
        specialty = specialtyService.saveSpecialty(
                specialty);
        analyst.setSpecialty(specialty);
        updatedAnalyst.setSpecialty(specialty);

        person = personService.savePerson(person);
        analyst.setPerson(person);
        updatedAnalyst.setPerson(person);

        analyst = analystService.saveAnalyst(analyst);
        updatedAnalyst.setId(analyst.getId());
    }
    @Test
    @Order(5)
    void selectAnalystByPersonIdTest() throws SQLException {
        Analyst analyst1 = analystService.getAnalystByPersonId(
                person.getId()).get();
        assertTrue(analyst1.getPerson().getId().equals(person.getId()));
        assertTrue(analyst1.getSpecialty().getId().equals(specialty.getId()));
    }
    @Test
    @Order(6)
    void updateAnalystByPersonIdTest() throws SQLException {
        updatedAnalyst = analystService.updateAnalyst(
                updatedAnalyst);
    }
    @Test
    @Order(7)
    void selectUpdatedAnalystByPersonIdTest() throws SQLException {
        Analyst analyst1 = analystService.getAnalystByPersonId(
                person.getId()).get();
        assertTrue(analyst1.getPerson().getId().equals(person.getId()));
        assertTrue(analyst1.getSpecialty().getId().equals(specialty.getId()));
    }
    @Test
    @Order(8)
    void deleteAnalystByPersonIdTest() throws SQLException {
        analystService.deleteAnalyst(updatedAnalyst.getId());

        personService.deletePerson(person.getId());

        specialtyService.deleteSpecialty(specialty.getId());
    }
}
