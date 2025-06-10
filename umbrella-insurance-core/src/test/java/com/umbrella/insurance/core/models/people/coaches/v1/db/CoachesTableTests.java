package com.umbrella.insurance.core.models.people.coaches.v1.db;

import com.umbrella.insurance.core.models.entities.Coach;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.coaches.v1.db.jpa.CoachService;
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
public class CoachesTableTests {
    private static Person person = new Person();
    private static Coach coach = new Coach();
    private static Coach updatedCoach = new Coach();
    static {
        person.setFirstName("test1");
        person.setMiddleName("test2");
        person.setSurname("test3");
        person.setDateOfBirth(Date.valueOf("2000-01-01"));
        person.setSsn("12345678901");
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
    private PersonService personService;

    @Autowired
    private CoachService coachService;


    @Test
    @Order(3)
    void insertCoachTest() throws SQLException {
        person = personService.savePerson(person);
        coach.setPerson(person);
        updatedCoach.setPerson(person);

        coach = coachService.saveCoach(coach);
        updatedCoach.setId(coach.getId());
    }
    @Test
    @Order(4)
    void selectCoachByCoachNameTest() throws SQLException {
        Coach coach1 = coachService.getCoachById(
                coach.getId()).get();
        assertTrue(coach1.getPerson().getId().equals(person.getId()));
    }
    @Test
    @Order(5)
    void updateCoachByCoachNameTest() throws SQLException {
        updatedCoach = coachService.updateCoach(
                updatedCoach);
    }
    @Test
    @Order(6)
    void selectUpdatedCoachByCoachNameTest() throws SQLException {
        Coach coach1 = coachService.getCoachById(
                updatedCoach.getId()).get();
        assertTrue(coach1.getPerson().getId().equals(person.getId()));
    }
    @Test
    @Order(7)
    void deleteCoachByCoachNameTest() throws SQLException {
        coachService.deleteCoach(
                updatedCoach.getId());

        personService.deletePerson(person.getId());
    }
}
