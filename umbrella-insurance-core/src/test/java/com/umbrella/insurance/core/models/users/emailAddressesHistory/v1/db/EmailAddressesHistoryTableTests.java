package com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.EmailAddressHistory;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db.jpa.EmailAddressHistoryService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailAddressesHistoryTableTests {
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Timestamp createdDateTime = Timestamp.valueOf("1992-09-08 04:23:23");
    private static String updatedEmailAddress = "3";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1993-11-08 23:23:23");
    private static Person person = new Person();
    private static User user = new User();
    private static EmailAddressHistory emailAddressHistory = new EmailAddressHistory();
    private static EmailAddressHistory updatedEmailAddressHistory = new EmailAddressHistory();
    static {
        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(isAuthAppVerified);

        emailAddressHistory.setEmailAddress(emailAddress);
        emailAddressHistory.setCreatedDateTime(createdDateTime);

        updatedEmailAddressHistory.setEmailAddress(updatedEmailAddress);
        updatedEmailAddressHistory.setCreatedDateTime(updatedCreatedDateTime);
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
    private UserService userService;

    @Autowired
    private EmailAddressHistoryService emailAddressHistoryService;

    @Test
    @Order(2)
    void insertEmailAddressHistoryTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        emailAddressHistory.setUser(user);
        updatedEmailAddressHistory.setUser(user);

        emailAddressHistory = emailAddressHistoryService.saveEmailAddressHistory(
                emailAddressHistory);
        updatedEmailAddressHistory.setId(emailAddressHistory.getId());
    }
    @Test
    @Order(3)
    void selectLatestEmailAddressHistoryByUserIdTest() throws SQLException {
        EmailAddressHistory emailAddressHistory1 =
                emailAddressHistoryService.getEmailAddressHistoryByUserId(
                        user.getId()).get();
        assertTrue(emailAddressHistory1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(emailAddressHistory1.getUser().getId().equals(user.getId()));
        assertTrue(emailAddressHistory1.getEmailAddress().equals(emailAddress));
    }
    @Test
    @Order(4)
    void updateLatestEmailAddressHistoryByUserIdTest() throws SQLException {
        updatedEmailAddressHistory = emailAddressHistoryService.updateEmailAddressHistory(
                updatedEmailAddressHistory);
    }
    @Test
    @Order(5)
    void selectUpdatedLatestEmailAddressHistoryByUserIdTest() throws SQLException {
        EmailAddressHistory emailAddressHistory1 =
                emailAddressHistoryService.getEmailAddressHistoryByUserId(
                        user.getId()).get();
        assertTrue(emailAddressHistory1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(emailAddressHistory1.getUser().getId().equals(user.getId()));
        assertTrue(emailAddressHistory1.getEmailAddress().equals(updatedEmailAddress));
    }
    @Test
    @Order(6)
    void deleteLatestEmailAddressHistoryByUserIdTest() throws SQLException {
        emailAddressHistoryService.deleteEmailAddressHistory(
                updatedEmailAddressHistory.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
