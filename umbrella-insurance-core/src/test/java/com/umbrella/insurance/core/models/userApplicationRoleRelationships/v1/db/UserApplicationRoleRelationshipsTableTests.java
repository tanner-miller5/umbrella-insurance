package com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db;

import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.entities.ApplicationRole;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.entities.UserApplicationRoleRelationship;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApplicationRoleRelationshipsTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static ApplicationRole applicationRole =
            new ApplicationRole();
    private static User user =
            new User();
    private static UserApplicationRoleRelationship userApplicationRoleRelationship =
            new UserApplicationRoleRelationship();
    private static UserApplicationRoleRelationship updatedUserApplicationRoleRelationship =
            new UserApplicationRoleRelationship();

    static {
        applicationRole.setApplicationRoleName("customer1");

        person.setSsn("123e");
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
    ApplicationRoleService applicationRoleService;

    @Autowired
    UserService userService;

    @Autowired
    PersonService personService;

    @Autowired
    UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;


    @Test
    @Order(4)
    void insertUserApplicationRoleRelationshipTest() throws SQLException {
        applicationRole = applicationRoleService.saveApplicationRole(
                applicationRole);
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        updatedUserApplicationRoleRelationship.setApplicationRole(applicationRole);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        userApplicationRoleRelationship.setUser(user);
        updatedUserApplicationRoleRelationship.setUser(user);

        userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);
        updatedUserApplicationRoleRelationship.setId(userApplicationRoleRelationship.getId());
    }
    @Test
    @Order(5)
    void selectUserApplicationRoleRelationshipByUserIdAndApplicationRoleIdTest() throws SQLException {
        UserApplicationRoleRelationship userApplicationRoleRelationship1 =
                userApplicationRoleRelationshipService
                        .getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                        user.getId(),
                        applicationRole.getId()).get();
        assertTrue(userApplicationRoleRelationship1
                .getApplicationRole().getId().equals(applicationRole.getId()));
        assertTrue(userApplicationRoleRelationship1
                .getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(6)
    void updateUserApplicationRoleRelationshipByUserIdAndApplicationRoleIdTest() throws SQLException {
        updatedUserApplicationRoleRelationship = userApplicationRoleRelationshipService
                .updateUserApplicationRoleRelationship(
                        updatedUserApplicationRoleRelationship);
    }
    @Test
    @Order(7)
    void selectUpdatedUserApplicationRoleRelationshipByUserIdAndApplicationRoleIdTest() throws SQLException {
        UserApplicationRoleRelationship userApplicationRoleRelationship1 =
                userApplicationRoleRelationshipService
                        .getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                        user.getId(),
                        applicationRole.getId()).get();
        assertTrue(userApplicationRoleRelationship1
                .getApplicationRole().getId().equals(applicationRole.getId()));
        assertTrue(userApplicationRoleRelationship1
                .getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(8)
    void deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleIdTest() throws SQLException {
        userApplicationRoleRelationshipService
                .deleteUserApplicationRoleRelationship(
                        userApplicationRoleRelationship.getId());

        applicationRoleService.deleteApplicationRole(
                applicationRole.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
