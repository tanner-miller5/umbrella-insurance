package com.umbrella.insurance.endpoints.rest.users.userAgreements.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa.CardOnFileService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.transfers.v1.db.jpa.TransferService;
import com.umbrella.insurance.core.models.users.userAgreements.v1.db.UserAgreementPrivilege;
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
public class UserAgreementRestEndpointsTests {
    @Autowired
    private UserAgreementRestEndpoints userAgreementRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(userAgreementRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress2 = "133";
    private static String phoneNumber2 = "233";
    private static String username2 = "333";
    private static String password2 = "533";
    private static String ssn2 = "12333";
    private static String surname2 = "12233";
    private static String middle2 = "middle33";
    private static String first2 = "first33";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-16");

    private static Long userAgreementId;
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
    private static Person person = new Person();
    private static User user = new User();
    private static UserAgreement userAgreement = new UserAgreement();
    private static UserAgreement updatedUserAgreement = new UserAgreement();
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

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(false);

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
    private PasswordService passwordService;

    @Autowired
    private CardOnFileService cardOnFileService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createUserAgreement() throws Exception {
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
        assertTrue(session != null);
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        userAgreement.setUser(user);
        updatedUserAgreement.setUser(user);

        headers.set("session", session.getSessionCode());
        HttpEntity<UserAgreement> request = new HttpEntity<>(userAgreement, headers);

        String url = hostname + port
                + UserAgreementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<UserAgreement> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, UserAgreement.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserAgreement userAgreement1 = response.getBody();
        assertTrue(userAgreement1.getUserAgreementName().equals(userAgreementName));
        userAgreementId = userAgreement1.getId();
        updatedUserAgreement.setId(userAgreementId);
    }

    @Test
    @Order(2)
    void readUserAgreementByUserIdAndUserAgreementName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + UserAgreementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + user.getId()
                + "&userAgreementName=" + userAgreementName;
        ResponseEntity<UserAgreement[]> userAgreementList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, UserAgreement[].class);
        assertTrue(userAgreementList.getStatusCode().is2xxSuccessful());
        UserAgreement userAgreement1 = userAgreementList.getBody()[0];
        assertTrue(userAgreement1.getUserAgreementName().equals(userAgreementName));
    }

    @Test
    @Order(3)
    void readUserAgreementByUserAgreementId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<UserAgreement[]> userAgreementList = this.restTemplate.exchange(
                hostname + port + UserAgreementPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&userAgreementId=" + userAgreementId, HttpMethod.GET, request, UserAgreement[].class);
        assertTrue(userAgreementList.getStatusCode().is2xxSuccessful());
        UserAgreement userAgreement1 = userAgreementList.getBody()[0];
        assertTrue(userAgreement1.getUserAgreementName().equals(userAgreementName));
    }

    @Test
    @Order(4)
    void updateUserAgreementByUserAgreementId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<UserAgreement> request = new HttpEntity<>(updatedUserAgreement, headers);

        String url = hostname + port
                + UserAgreementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userAgreementId=" + userAgreementId;
        URI uri = new URI(url);
        ResponseEntity<UserAgreement[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, UserAgreement[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedUserAgreementByUserAgreementId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<UserAgreement[]> userAgreementList = this.restTemplate.exchange(
                hostname + port
                        + UserAgreementPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&userAgreementId=" + userAgreementId, HttpMethod.GET, request, UserAgreement[].class);
        assertTrue(userAgreementList.getStatusCode().is2xxSuccessful());
        UserAgreement userAgreement1 = userAgreementList.getBody()[0];
        assertTrue(userAgreement1.getUserAgreementName().equals(updatedUserAgreementName));
        userAgreementId = userAgreement1.getId();
    }

    @Test
    @Order(6)
    void deleteUserAgreementByUserAgreementId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + UserAgreementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userAgreementId=" + userAgreementId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        userService.deleteUserByPersonId(
                person.getId());

        personService.deletePersonBySsn(
                person.getSsn());

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
