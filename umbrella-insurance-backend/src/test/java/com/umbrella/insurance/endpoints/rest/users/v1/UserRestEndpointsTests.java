package com.umbrella.insurance.endpoints.rest.users.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRestEndpointsTests {
    @Autowired
    private UserRestEndpoints userRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(userRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long userId;

    private static String emailAddress2 = "randomtest1234567@gmail.com";
    private static String phoneNumber2 = "9255411111";
    private static String username2 = "333";
    private static String password2 = "533";
    private static String ssn2 = "12333";
    private static String surname2 = "12233";
    private static String middle2 = "middle33";
    private static String first2 = "first33";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-16");

    private static Timestamp createdDateTime = Timestamp.valueOf("2001-11-11 11:11:11");
    private static String emailAddress = "randomtest1234567@icloud.com";
    private static String phoneNumber = "7021111111";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;

    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2001-11-11 11:11:11");
    private static String updatedEmailAddress = "randomtest1234567@icloud.com";
    private static String updatedPhoneNumber = "702741111";
    private static String updatedUsername = "3";
    private static Boolean updatedIsPhoneNumberVerified = false;
    private static Boolean updatedIsEmailAddressVerified = false;

    private static Person person = new Person();
    private static Person person2 = new Person();
    private static User user = new User();
    private static User updatedUser = new User();

    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    static {
        createUserRequest.setPassword(password2);
        createUserRequest.setUsername(username2);
        createUserRequest.setSsn(ssn2);
        createUserRequest.setPhoneNumber(phoneNumber2);
        createUserRequest.setSurname(surname2);
        createUserRequest.setMiddleName(middle2);
        createUserRequest.setFirstName(first2);
        createUserRequest.setEmailAddress(emailAddress2);
        createUserRequest.setDateOfBirth(dateOfBirth2);
        createUserRequest.setConsentedToEmails(false);
        createUserRequest.setConsentedToTexts(false);
        createUserRequest.setConsentedToTermsAndConditions(true);

        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        person2.setSsn("1234");
        person2.setDateOfBirth(Date.valueOf("1111-11-12"));
        person2.setSurname("last2");
        person2.setMiddleName("middle2");
        person2.setFirstName("first2");

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(false);

        updatedUser.setCreatedDateTime(updatedCreatedDateTime);
        updatedUser.setEmailAddress(updatedEmailAddress);
        updatedUser.setPhoneNumber(updatedPhoneNumber);
        updatedUser.setUsername(updatedUsername);
        updatedUser.setIsEmailAddressVerified(updatedIsEmailAddressVerified);
        updatedUser.setIsPhoneNumberVerified(updatedIsPhoneNumberVerified);
        updatedUser.setIsAuthAppVerified(true);
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

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private PasswordService passwordService;

    @Test
    @Order(1)
    void createUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<CreateUserRequest> cuRequest = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, cuRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getBody().getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(userApplicationRoleRelationship2);

        person2 = personService.savePerson(person2);
        user.setPerson(person2);
        updatedUser.setPerson(person2);

        headers.set("session", session.getSessionCode());
        HttpEntity<User> request = new HttpEntity<>(user, headers);


        String url = hostname + port
                + UserPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<User> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, User.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        User user1 = response.getBody();
        assertTrue(user1.getEmailAddress().equals(emailAddress));
        assertTrue(user1.getPhoneNumber().equals(phoneNumber));
        assertTrue(user1.getUsername().equals(username));
        assertTrue(user1.getIsEmailAddressVerified().equals(isEmailAddressVerified));
        assertTrue(user1.getIsPhoneNumberVerified().equals(isPhoneNumberVerified));
        userId = user1.getId();
        updatedUser.setId(userId);
    }

    @Test
    @Order(2)
    void readUserByUserName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + UserPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&personId=" + person2.getId();
        ResponseEntity<User[]> userList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, User[].class);
        assertTrue(userList.getStatusCode().is2xxSuccessful());
        User user1 = userList.getBody()[0];
        assertTrue(user1.getPerson().getId().equals(person2.getId()));
        assertTrue(user1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(user1.getEmailAddress().equals(emailAddress));
        assertTrue(user1.getPhoneNumber().equals(phoneNumber));
        assertTrue(user1.getUsername().equals(username));
        assertTrue(user1.getIsEmailAddressVerified().equals(isEmailAddressVerified));
        assertTrue(user1.getIsPhoneNumberVerified().equals(isPhoneNumberVerified));
    }

    @Test
    @Order(3)
    void readUserByUserId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<User[]> userList = this.restTemplate.exchange(
                hostname + port + UserPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&userId=" + userId, HttpMethod.GET, request, User[].class);
        assertTrue(userList.getStatusCode().is2xxSuccessful());
        User user1 = userList.getBody()[0];
        assertTrue(user1.getPerson().getId().equals(person2.getId()));
        assertTrue(user1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(user1.getEmailAddress().equals(emailAddress));
        assertTrue(user1.getPhoneNumber().equals(phoneNumber));
        assertTrue(user1.getUsername().equals(username));
        assertTrue(user1.getIsEmailAddressVerified().equals(isEmailAddressVerified));
        assertTrue(user1.getIsPhoneNumberVerified().equals(isPhoneNumberVerified));
    }

    @Test
    @Order(4)
    void updateUserByUserId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<User> request = new HttpEntity<>(updatedUser, headers);

        String url = hostname + port
                + UserPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + userId;
        URI uri = new URI(url);
        ResponseEntity<User[]> userList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, User[].class);
        assertTrue(userList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedUserByUserId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<User[]> userList = this.restTemplate.exchange(
                hostname + port
                        + UserPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&userId=" + userId, HttpMethod.GET, request, User[].class);
        assertTrue(userList.getStatusCode().is2xxSuccessful());
        User user1 = userList.getBody()[0];
        assertTrue(user1.getPerson().getId().equals(person2.getId()));
        assertTrue(user1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(user1.getEmailAddress().equals(updatedEmailAddress));
        assertTrue(user1.getPhoneNumber().equals(updatedPhoneNumber));
        assertTrue(user1.getUsername().equals(updatedUsername));
        assertTrue(user1.getIsEmailAddressVerified().equals(updatedIsEmailAddressVerified));
        assertTrue(user1.getIsPhoneNumberVerified().equals(updatedIsPhoneNumberVerified));
    }

    @Test
    @Order(6)
    void deleteUserByUserId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Object> response = this.restTemplate.exchange(
                hostname + port
                        + UserPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&userId=" + userId, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        personService.deletePersonBySsn(person2.getSsn());


        sessionService.deleteSession(session.getId());


        passwordService.deletePasswordByUserId(session.getUser().getId());


        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());


        userService.deleteUser(session.getUser().getId());


        personService.deletePersonBySsn(ssn2);
    }
}
