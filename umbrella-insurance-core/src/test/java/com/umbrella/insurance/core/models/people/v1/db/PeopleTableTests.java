package com.umbrella.insurance.core.models.people.v1.db;

import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeopleTableTests {
    private static String ssn = "1234";
    private static String firstName = "t";
    private static String middleName = "a";
    private static String surname = "n";
    private static Date date = Date.valueOf("2023-12-12");

    private static String updatedSsn = "12345";
    private static String updatedFirstName = "ta";
    private static String updatedMiddleName = "aa";
    private static String updatedSurname = "na";
    private static Date updatedDate = Date.valueOf("2025-11-11");

    private static Person person = new Person();
    private static Person updatedPerson = new Person();
    static {
        person.setSsn(ssn);
        person.setDateOfBirth(date);
        person.setSurname(surname);
        person.setMiddleName(middleName);
        person.setFirstName(firstName);

        updatedPerson.setSsn(updatedSsn);
        updatedPerson.setDateOfBirth(updatedDate);
        updatedPerson.setSurname(updatedSurname);
        updatedPerson.setMiddleName(updatedMiddleName);
        updatedPerson.setFirstName(updatedFirstName);

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
    PersonService personService;

    @Test
    @Order(2)
    void insertPersonTest() throws SQLException {
        person = personService.savePerson(person);
        updatedPerson.setId(person.getId());
    }
    @Test
    @Order(3)
    void selectPersonBySsnTest() throws SQLException {
        Person person1 = personService.getPersonBySsn(ssn).get();
        assertTrue(person1.getSsn().equals(ssn));
        assertTrue(person1.getDateOfBirth().equals(date));
        assertTrue(person1.getFirstName().equals(firstName));
        assertTrue(person1.getMiddleName().equals(middleName));
        assertTrue(person1.getSurname().equals(surname));
    }
    @Test
    @Order(4)
    void updatePersonBySsnTest() throws SQLException {
        updatedPerson = personService.updatePerson(updatedPerson);
    }
    @Test
    @Order(5)
    void selectUpdatedPersonBySsnTest() throws SQLException {
        Person person1 = personService.getPersonBySsn(updatedSsn).get();
        assertTrue(person1.getSsn().equals(updatedSsn));
        assertTrue(person1.getDateOfBirth().equals(updatedDate));
        assertTrue(person1.getFirstName().equals(updatedFirstName));
        assertTrue(person1.getMiddleName().equals(updatedMiddleName));
        assertTrue(person1.getSurname().equals(updatedSurname));
    }
    @Test
    @Order(6)
    void deletePersonBySsnTest() throws SQLException {
        personService.deletePerson(updatedPerson.getId());
    }


}
