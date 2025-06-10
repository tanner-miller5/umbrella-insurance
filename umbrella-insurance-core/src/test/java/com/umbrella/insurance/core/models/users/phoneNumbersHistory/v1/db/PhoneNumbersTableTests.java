package com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.PhoneNumberHistory;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db.jpa.PhoneNumberHistoryService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhoneNumbersTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String updatedPhoneNumber = "4";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1111-11-11 11:11:11");
    private static Person person = new Person();
    private static User user = new User();
    private static PhoneNumberHistory phoneNumberHistory = new PhoneNumberHistory();
    private static PhoneNumberHistory updatedPhoneNumberHistory = new PhoneNumberHistory();
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

        phoneNumberHistory.setPhoneNumber(phoneNumber);
        phoneNumberHistory.setCreatedDateTime(createdDateTime);

        updatedPhoneNumberHistory.setPhoneNumber(updatedPhoneNumber);
        updatedPhoneNumberHistory.setCreatedDateTime(updatedCreatedDateTime);
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
    private PhoneNumberHistoryService phoneNumberHistoryService;

    @Test
    @Order(2)
    void insertPhoneNumberHistoryTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        phoneNumberHistory.setUser(user);
        updatedPhoneNumberHistory.setUser(user);

        phoneNumberHistory = phoneNumberHistoryService.savePhoneNumberHistory(
                phoneNumberHistory);
        updatedPhoneNumberHistory.setId(phoneNumberHistory.getId());
    }
    @Test
    @Order(3)
    void selectLatestPhoneNumberHistoryByUserIdTest() throws SQLException {
        PhoneNumberHistory phoneNumberHistory1 =
                phoneNumberHistoryService.getPhoneNumberHistoryByUserId(
                        user.getId()).get();
        assertTrue(phoneNumberHistory1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(phoneNumberHistory1.getPhoneNumber().equals(phoneNumber));
        assertTrue(phoneNumberHistory1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(4)
    void updateLatestPhoneNumberHistoryByUserIdTest() throws SQLException {
        updatedPhoneNumberHistory = phoneNumberHistoryService.updatePhoneNumberHistory(
                updatedPhoneNumberHistory);
    }
    @Test
    @Order(5)
    void selectUpdatedLatestPhoneNumberHistoryByUserIdTest() throws SQLException {
        PhoneNumberHistory phoneNumberHistory1 =
                phoneNumberHistoryService.getPhoneNumberHistoryByUserId(
                        user.getId()).get();
        assertTrue(phoneNumberHistory1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(phoneNumberHistory1.getPhoneNumber().equals(updatedPhoneNumber));
        assertTrue(phoneNumberHistory1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(6)
    void deleteLatestPhoneNumberHistoryByUserIdTest() throws SQLException {
        phoneNumberHistoryService.deletePhoneNumberHistory(
                updatedPhoneNumberHistory.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
