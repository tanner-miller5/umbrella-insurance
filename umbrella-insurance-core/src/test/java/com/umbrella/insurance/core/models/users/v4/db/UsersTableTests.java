package com.umbrella.insurance.core.models.users.v4.db;

import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2111-11-11 11:11:11");
    private static String updatedEmailAddress = "4";
    private static String updatedPhoneNumber = "5";
    private static String updatedUsername = "6";
    private static Boolean updatedIsPhoneNumberVerified = true;
    private static Boolean updatedIsEmailAddressVerified = true;

    private static Person person = new Person();
    private static User user = new User();
    private static User updatedUser = new User();
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

        updatedUser.setCreatedDateTime(updatedCreatedDateTime);
        updatedUser.setEmailAddress(updatedEmailAddress);
        updatedUser.setPhoneNumber(updatedPhoneNumber);
        updatedUser.setUsername(updatedUsername);
        updatedUser.setIsEmailAddressVerified(updatedIsEmailAddressVerified);
        updatedUser.setIsPhoneNumberVerified(updatedIsPhoneNumberVerified);
        updatedUser.setIsAuthAppVerified(isAuthAppVerified);
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

    @Test
    @Order(3)
    void insertUserTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);
        updatedUser.setPerson(person);

        user = userService.saveUser(user);
        updatedUser.setId(user.getId());
    }
    @Test
    @Order(4)
    void selectUserByPersonIdTest() throws SQLException {
        User user1 = userService.getUserByPersonId(person.getId()).get();
        assertTrue(user1.getPerson().getId().equals(person.getId()));
        assertTrue(user1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(user1.getEmailAddress().equals(emailAddress));
        assertTrue(user1.getPhoneNumber().equals(phoneNumber));
        assertTrue(user1.getUsername().equals(username));
        assertTrue(user1.getIsEmailAddressVerified().equals(isEmailAddressVerified));
        assertTrue(user1.getIsPhoneNumberVerified().equals(isPhoneNumberVerified));
    }
    @Test
    @Order(5)
    void updateUserByPersonIdTest() throws SQLException {
        updatedUser = userService.updateUser(
                updatedUser);
    }
    @Test
    @Order(6)
    void selectUpdatedUserByPersonIdTest() throws SQLException {
        User user1 = userService.getUserByPersonId(
                person.getId()).get();
        assertTrue(user1.getPerson().getId().equals(person.getId()));
        assertTrue(user1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(user1.getEmailAddress().equals(updatedEmailAddress));
        assertTrue(user1.getPhoneNumber().equals(updatedPhoneNumber));
        assertTrue(user1.getUsername().equals(updatedUsername));
        assertTrue(user1.getIsEmailAddressVerified().equals(updatedIsEmailAddressVerified));
        assertTrue(user1.getIsPhoneNumberVerified().equals(updatedIsPhoneNumberVerified));
    }
    @Test
    @Order(7)
    void deleteUserByUserIdTest() throws SQLException {
        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
