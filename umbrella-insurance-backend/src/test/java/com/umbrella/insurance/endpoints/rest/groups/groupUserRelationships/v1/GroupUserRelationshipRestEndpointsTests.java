package com.umbrella.insurance.endpoints.rest.groups.groupUserRelationships.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.GroupUserRelationshipPrivilege;
import com.umbrella.insurance.core.models.groups.v1.db.jpa.GroupService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.endpoints.rest.users.v1.requests.CreateUserRequest;
import com.umbrella.insurance.endpoints.rest.users.v1.responses.CreateUserResponse;
import com.umbrella.insurance.website.WebsiteApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupUserRelationshipRestEndpointsTests {
    @Autowired
    private GroupUserRelationshipRestEndpoints groupUserRelationshipRestEndpoints;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    void contextLoads() throws Exception {
        assertThat(groupUserRelationshipRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static Long groupUserRelationshipId;

    private static String groupName = "123";

    private static Person person = new Person();
    private static User user = new User();
    private static Group group = new Group();
    private static GroupUserRelationship groupUserRelationship = new GroupUserRelationship();
    private static GroupUserRelationship updatedGroupUserRelationship = new GroupUserRelationship();

    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();

    static {
        createUserRequest.setPassword(password);
        createUserRequest.setUsername(username);
        createUserRequest.setSsn(ssn);
        createUserRequest.setPhoneNumber(phoneNumber);
        createUserRequest.setSurname(surname);
        createUserRequest.setMiddleName(middle);
        createUserRequest.setFirstName(first);
        createUserRequest.setEmailAddress(emailAddress);
        createUserRequest.setDateOfBirth(dateOfBirth);
        createUserRequest.setConsentedToEmails(false);
        createUserRequest.setConsentedToTexts(false);
        createUserRequest.setConsentedToTermsAndConditions(true);

        group.setGroupName(groupName);
    }

    private static Connection connection;
    @BeforeEach
    public void beforeEach() throws SQLException {
    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        Database.closeConnection(connection);
    }

    @Test
    @Order(1)
    void createGroupUserRelationship() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<CreateUserRequest> cuRequest = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, cuRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getBody().getSessionCode());
        user = createUserResponse.getBody().getUser();
        groupUserRelationship.setUser(user);
        updatedGroupUserRelationship.setUser(user);


        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<GroupUserRelationship> request = new HttpEntity<>(groupUserRelationship, headers);

        group = groupService.saveGroup(group);
        groupUserRelationship.setGroup(group);
        updatedGroupUserRelationship.setGroup(group);

        String url = hostname + port
                + GroupUserRelationshipPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<GroupUserRelationship> groupUserRelationship1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, GroupUserRelationship.class);
        assertTrue(groupUserRelationship1.getStatusCode().is2xxSuccessful());
        assertTrue(groupUserRelationship1.getBody().getGroup().getId().equals(group.getId()));
        assertTrue(groupUserRelationship1.getBody().getUser().getId().equals(user.getId()));
        groupUserRelationshipId = groupUserRelationship1.getBody().getId();
        updatedGroupUserRelationship.setId(groupUserRelationshipId);
    }

    @Test
    @Order(2)
    void readGroupUserRelationshipByGroupUserRelationshipName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + GroupUserRelationshipPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&groupId=" + group.getId()
                + "&userId=" + user.getId();
        ResponseEntity<GroupUserRelationship[]> groupUserRelationshipList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, GroupUserRelationship[].class);
        assertTrue(groupUserRelationshipList.getStatusCode().is2xxSuccessful());
        GroupUserRelationship groupUserRelationship1 = groupUserRelationshipList.getBody()[0];
        assertTrue(groupUserRelationship1.getGroup().getId().equals(group.getId()));
        assertTrue(groupUserRelationship1.getUser().getId().equals(user.getId()));
    }

    @Test
    @Order(3)
    void readGroupUserRelationshipByGroupUserRelationshipId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<GroupUserRelationship[]> groupUserRelationshipList = this.restTemplate.exchange(
                hostname + port + GroupUserRelationshipPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&groupUserRelationshipId=" + groupUserRelationshipId,
                HttpMethod.GET, request, GroupUserRelationship[].class);
        assertTrue(groupUserRelationshipList.getStatusCode().is2xxSuccessful());
        GroupUserRelationship groupUserRelationship1 = groupUserRelationshipList.getBody()[0];
        assertTrue(groupUserRelationship1.getGroup().getId().equals(group.getId()));
        assertTrue(groupUserRelationship1.getUser().getId().equals(user.getId()));
    }

    @Test
    @Order(4)
    void updateGroupUserRelationshipByGroupUserRelationshipId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<GroupUserRelationship> request = new HttpEntity<>(updatedGroupUserRelationship, headers);

        String url = hostname + port
                + GroupUserRelationshipPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&groupUserRelationshipId=" + groupUserRelationshipId;
        URI uri = new URI(url);
        ResponseEntity<GroupUserRelationship[]> groupUserRelationshipList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, GroupUserRelationship[].class);
        assertTrue(groupUserRelationshipList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedGroupUserRelationshipByGroupUserRelationshipId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<GroupUserRelationship[]> groupUserRelationshipList = this.restTemplate.exchange(
                hostname + port
                        + GroupUserRelationshipPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&groupUserRelationshipId=" + groupUserRelationshipId,
                HttpMethod.GET, request, GroupUserRelationship[].class);
        assertTrue(groupUserRelationshipList.getStatusCode().is2xxSuccessful());
        GroupUserRelationship groupUserRelationship1 = groupUserRelationshipList.getBody()[0];
        assertTrue(groupUserRelationship1.getGroup().getId().equals(group.getId()));
        assertTrue(groupUserRelationship1.getUser().getId().equals(user.getId()));
        groupUserRelationshipId = groupUserRelationship1.getId();
    }

    @Test
    @Order(6)
    void deleteGroupUserRelationshipByGroupUserRelationshipId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + GroupUserRelationshipPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&groupUserRelationshipId=" + groupUserRelationshipId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        groupService.deleteGroupByGroupName(groupName);

        sessionService.deleteSession(session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn);
    }
}
