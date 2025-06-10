package com.umbrella.insurance.core.models.users.userAgreements.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.entities.UserAgreement;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.userAgreements.v1.db.jpa.UserAgreementService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAgreementsTableTests {
    private static String userAgreementName = "2";
    private static String userAgreementText = "3";
    private static Timestamp updatedDateTime = Timestamp.valueOf("2100-11-11 11:11:11");
    private static Boolean didAgree = true;
    private static String updatedUserAgreementName = "5";
    private static String updatedUserAgreementText = "6";
    private static Timestamp updatedUpdatedDateTime = Timestamp.valueOf("1999-12-12 12:12:12");
    private static Boolean updatedDidAgree = false;
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static UserAgreement userAgreement = new UserAgreement();
    private static UserAgreement updatedUserAgreement = new UserAgreement();
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

        userAgreement.setUserAgreementName(userAgreementName);
        userAgreement.setUpdatedDateTime(updatedDateTime);
        userAgreement.setDidAgree(didAgree);
        userAgreement.setUserAgreementText(userAgreementText);

        updatedUserAgreement.setUserAgreementName(updatedUserAgreementName);
        updatedUserAgreement.setUpdatedDateTime(updatedUpdatedDateTime);
        updatedUserAgreement.setDidAgree(updatedDidAgree);
        updatedUserAgreement.setUserAgreementText(updatedUserAgreementText);
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
    private UserAgreementService userAgreementService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Test
    @Order(2)
    void insertUserAgreementTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        userAgreement.setUser(user);
        updatedUserAgreement.setUser(user);

        userAgreement = userAgreementService.saveUserAgreement(userAgreement);
        updatedUserAgreement.setId(userAgreement.getId());
    }
    @Test
    @Order(3)
    void selectUserAgreementByUserIdAndUserAgreementNameTest() throws SQLException {
        UserAgreement userAgreement1 =
                userAgreementService.getUserAgreementByUserIdAndUserAgreementName(
                        user.getId(), userAgreementName).get();
        assertTrue(userAgreement1.getUserAgreementName().equals(userAgreementName));
    }
    @Test
    @Order(4)
    void updateUserAgreementByUserIdAndUserAgreementNameTest() throws SQLException {
        updatedUserAgreement = userAgreementService.updateUserAgreement(
                updatedUserAgreement);
    }
    @Test
    @Order(5)
    void selectUpdatedUserAgreementByUserIdAndUserAgreementNameTest() throws SQLException {
        UserAgreement userAgreement1 =
                userAgreementService.getUserAgreementByUserIdAndUserAgreementName(
                        user.getId(), updatedUserAgreementName).get();
        assertTrue(userAgreement1.getUserAgreementName().equals(updatedUserAgreementName));
    }
    @Test
    @Order(6)
    void deleteUserAgreementByUserIdAndUserAgreementNameTest() throws SQLException {
        userAgreementService.deleteUserAgreement(
                updatedUserAgreement.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
