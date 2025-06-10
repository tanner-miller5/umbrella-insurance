package com.umbrella.insurance.core.models.people.referees.v1.db;

import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.Referee;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.referees.v1.db.jpa.RefereeService;
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
public class RefereesTableTests {
    private static Person person = new Person();
    private static Referee referee = new Referee();
    private static Referee updatedReferee = new Referee();
    static {
        person.setFirstName("first");
        person.setMiddleName("middle");
        person.setSurname("last");
        person.setDateOfBirth(Date.valueOf("2000-11-11"));
        person.setSsn("1234567");

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
    private RefereeService refereeService;

    @Test
    @Order(3)
    void insertRefereeTest() throws SQLException {
        person = personService.savePerson(person);
        referee.setPerson(person);
        updatedReferee.setPerson(person);
        referee = refereeService.saveReferee(referee);
        updatedReferee.setId(referee.getId());
    }
    @Test
    @Order(4)
    void selectRefereeByPersonIdTest() throws SQLException {
        Referee referee1 = refereeService
                .getRefereeByPersonId(person.getId()).get();
        assertTrue(referee1.getPerson().getId().equals(person.getId()));
    }
    @Test
    @Order(5)
    void updateRefereeByPersonIdTest() throws SQLException {
        updatedReferee = refereeService.updateReferee(
                updatedReferee);
    }
    @Test
    @Order(6)
    void selectUpdatedRefereeByPersonIdTest() throws SQLException {
        Referee referee1 = refereeService.getRefereeByPersonId(
                person.getId()).get();
        assertTrue(referee1.getPerson().getId().equals(person.getId()));
    }
    @Test
    @Order(7)
    void deleteRefereeByPersonIdTest() throws SQLException {
        refereeService.deleteReferee(
                updatedReferee.getId());

        personService.deletePerson(
                person.getId());
    }

}
