package com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db;

import com.umbrella.insurance.core.models.entities.Group;
import com.umbrella.insurance.core.models.entities.GroupUserRelationship;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.jpa.GroupUserRelationshipService;
import com.umbrella.insurance.core.models.groups.v1.db.jpa.GroupService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupUserRelationshipsTableTests {
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static Group group = new Group();
    private static GroupUserRelationship groupUserRelationship = new GroupUserRelationship();
    private static GroupUserRelationship updatedGroupUserRelationship = new GroupUserRelationship();
    static {
        person.setFirstName("first");
        person.setMiddleName("middle");
        person.setSurname("last");
        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));

        user.setCreatedDateTime(Timestamp.valueOf("1992-11-08 11:05:59"));
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(isAuthAppVerified);

        group.setGroupName("test");
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

    @Autowired
    private GroupUserRelationshipService groupUserRelationshipService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Test
    @Order(4)
    void insertGroupUserRelationshipTest() throws SQLException {
        group = groupService.saveGroup(
                group);
        groupUserRelationship.setGroup(group);
        updatedGroupUserRelationship.setGroup(group);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        groupUserRelationship.setUser(user);
        updatedGroupUserRelationship.setUser(user);

        groupUserRelationship = groupUserRelationshipService
                .saveGroupUserRelationship(groupUserRelationship);
        updatedGroupUserRelationship.setId(groupUserRelationship.getId());
    }
    @Test
    @Order(5)
    void selectGroupUserRelationshipByGroupUserRelationshipNameTest() throws SQLException {
        GroupUserRelationship groupUserRelationship1 =
                groupUserRelationshipService.getGroupUserRelationshipByGroupIdAndUserId(
                        group.getId(), user.getId()).get();
        assertTrue(groupUserRelationship1.getGroup().getId().equals(group.getId()));
        assertTrue(groupUserRelationship1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(6)
    void updateGroupUserRelationshipByGroupUserRelationshipNameTest() throws SQLException {
        updatedGroupUserRelationship =
                groupUserRelationshipService
                        .updateGroupUserRelationship(
                                updatedGroupUserRelationship);
    }
    @Test
    @Order(7)
    void selectUpdatedGroupUserRelationshipByGroupUserRelationshipNameTest() throws SQLException {
        GroupUserRelationship groupUserRelationship1 =
                groupUserRelationshipService.getGroupUserRelationshipByGroupIdAndUserId(
                        group.getId(),
                        user.getId()).get();
        assertTrue(groupUserRelationship1.getGroup().getId().equals(group.getId()));
        assertTrue(groupUserRelationship1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(8)
    void deleteGroupUserRelationshipByGroupUserRelationshipNameTest() throws SQLException {
        groupUserRelationshipService
                .deleteGroupUserRelationship(
                groupUserRelationship.getId());

        groupService.deleteGroup(
                group.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
