package com.umbrella.insurance.core.models.users.passwords.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Password;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PasswordsTableTests {
    private static String salt = "1";
    private static String hashedPassword = "3";
    private static Timestamp createdDateTime = Timestamp.valueOf("2001-01-01 10:10:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String updatedSalt = "4";
    private static String updatedHashedPassword = "6";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2001-01-01 10:10:11");
    private static Person person = new Person();
    private static User user = new User();
    private static Password password = new Password();
    private static Password updatedPassword = new Password();
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

        password.setSalt(salt);
        password.setHashedPassword(hashedPassword);
        password.setCreatedDateTime(createdDateTime);

        updatedPassword.setSalt(updatedSalt);
        updatedPassword.setHashedPassword(updatedHashedPassword);
        updatedPassword.setCreatedDateTime(updatedCreatedDateTime);
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
    private PasswordService passwordService;

    @Test
    @Order(2)
    void insertPasswordTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        password.setUser(user);
        updatedPassword.setUser(user);

        password = passwordService.savePassword(password);
        updatedPassword.setId(password.getId());
    }
    @Test
    @Order(3)
    void selectPasswordByUserIdTest() throws SQLException {
        Password password1 = passwordService.getPasswordByUserId(
                user.getId()).get();
        assertTrue(password1.getUser().getId().equals(user.getId()));
        assertTrue(password1.getSalt().equals(salt));
        assertTrue(password1.getHashedPassword().equals(hashedPassword));
        assertTrue(password1.getCreatedDateTime().equals(createdDateTime));
    }

    @Test
    @Order(4)
    void updatePasswordByUserIdTest() throws SQLException {
        updatedPassword = passwordService.updatePassword(
                updatedPassword);
    }
    @Test
    @Order(5)
    void selectUpdatedPasswordByUserIdTest() throws SQLException {
        Password password1 = passwordService.getPasswordByUserId(
                user.getId()).get();
        assertTrue(password1.getUser().getId().equals(user.getId()));
        assertTrue(password1.getSalt().equals(updatedSalt));
        assertTrue(password1.getHashedPassword().equals(updatedHashedPassword));
        assertTrue(password1.getCreatedDateTime().equals(updatedCreatedDateTime));
    }
    @Test
    @Order(6)
    void deletePasswordByUserIdTest() throws SQLException {
        passwordService.deletePassword(
                password.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
