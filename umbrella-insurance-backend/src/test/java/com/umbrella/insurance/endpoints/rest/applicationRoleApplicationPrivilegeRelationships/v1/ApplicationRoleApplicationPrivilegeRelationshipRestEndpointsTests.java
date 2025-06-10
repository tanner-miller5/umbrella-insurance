package com.umbrella.insurance.endpoints.rest.applicationRoleApplicationPrivilegeRelationships.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa.ApplicationPrivilegeService;
import com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.ApplicationRoleApplicationPrivilegeRelationshipPrivilege;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationRoleApplicationPrivilegeRelationshipRestEndpointsTests {
    @Autowired
    private ApplicationRoleApplicationPrivilegeRelationshipRestEndpoints applicationRoleApplicationPrivilegeRelationshipRestEndpoints;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    void contextLoads() throws Exception {
        assertThat(applicationRoleApplicationPrivilegeRelationshipRestEndpoints).isNotNull();
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
    private static Session session = new Session();
    private static Long applicationPrivilegeId;
    private static String applicationPrivilegeName = "1234";
    private static String path = "1234";
    private static String method = "1234";
    private static String access = "1234";
    private static String updatedApplicationPrivilegeName = "12345";
    private static String updatedPath = "1234";
    private static String updatedMethod = "1234";
    private static String updatedAccess = "1234";
    private static String roleName = "customer1";
    private static Long applicationRoleApplicationPrivilegeRelationshipId;
    private static ApplicationRole applicationRole =
            new ApplicationRole();
    private static ApplicationPrivilege applicationPrivilege =
            new ApplicationPrivilege();
    private static ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship =
            new ApplicationRoleApplicationPrivilegeRelationship();
    private static ApplicationRoleApplicationPrivilegeRelationship updatedApplicationRoleApplicationPrivilegeRelationship =
            new ApplicationRoleApplicationPrivilegeRelationship();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
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

        applicationRole.setApplicationRoleName(roleName);

        applicationPrivilege.setApplicationPrivilegeName(applicationPrivilegeName);
        applicationPrivilege.setMethod(method);
        applicationPrivilege.setAccess(access);
        applicationPrivilege.setPath(path);

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
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private ApplicationPrivilegeService applicationPrivilegeService;

    @Autowired
    private PasswordService passwordService;

    @Test
    @Order(1)
    void createApplicationRoleApplicationPrivilegeRelationship() throws Exception {
        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        CreateUserResponse createUserResponse = this.restTemplate.postForObject(
                createUserUrl, createUserRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();

        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRoleCS = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRoleCS);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        applicationRole = applicationRoleService.saveApplicationRole(applicationRole);

        applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                applicationRole.getApplicationRoleName()).get();
        applicationRoleApplicationPrivilegeRelationship.setApplicationRole(
                applicationRole);
        updatedApplicationRoleApplicationPrivilegeRelationship.setApplicationRole(
                applicationRole);

        applicationPrivilege = applicationPrivilegeService.saveApplicationPrivilege(
                applicationPrivilege);

        applicationRoleApplicationPrivilegeRelationship.setApplicationPrivilege(
                applicationPrivilege);
        updatedApplicationRoleApplicationPrivilegeRelationship.setApplicationPrivilege(
                applicationPrivilege);

        String url = hostname + port
                + ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH + "?env=" + TestConfig.testEnv.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<ApplicationRoleApplicationPrivilegeRelationship> request = new HttpEntity<>(applicationRoleApplicationPrivilegeRelationship, headers);
        ResponseEntity<ApplicationRoleApplicationPrivilegeRelationship> response =
                this.restTemplate.exchange(
                url, HttpMethod.POST, request, ApplicationRoleApplicationPrivilegeRelationship.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().getApplicationPrivilege().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationPrivilege().getId()));
        assertTrue(response.getBody().getApplicationRole().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationRole().getId()));
        applicationRoleApplicationPrivilegeRelationshipId = response.getBody()
                .getId();
        updatedApplicationRoleApplicationPrivilegeRelationship.setId(
                applicationRoleApplicationPrivilegeRelationshipId
        );
    }

    @Test
    @Order(2)
    void readApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeId() throws Exception {
        String url = hostname + port
                + ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH + "?env="
                + TestConfig.testEnv.toString() + "&applicationPrivilegeId="
                + applicationPrivilege.getId() + "&applicationRoleId="
                + applicationRole.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<ApplicationRoleApplicationPrivilegeRelationship[]> applicationRoleApplicationPrivilegeRelationshipList =
                this.restTemplate.exchange(
                url, HttpMethod.GET, request, ApplicationRoleApplicationPrivilegeRelationship[].class);
        assertTrue(applicationRoleApplicationPrivilegeRelationshipList.getStatusCode().is2xxSuccessful());
        ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship1 =
                applicationRoleApplicationPrivilegeRelationshipList.getBody()[0];
        assertTrue(applicationRoleApplicationPrivilegeRelationship1.getApplicationPrivilege().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationPrivilege().getId()));
        assertTrue(applicationRoleApplicationPrivilegeRelationship1.getApplicationRole().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationRole().getId()));
    }

    @Test
    @Order(3)
    void readApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<ApplicationRoleApplicationPrivilegeRelationship[]> applicationRoleApplicationPrivilegeRelationshipList =
                this.restTemplate.exchange(
                hostname + port + ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH + "?env="
                        + TestConfig.testEnv.toString() + "&applicationRoleApplicationPrivilegeRelationshipId="
                        + applicationRoleApplicationPrivilegeRelationshipId, HttpMethod.GET, request, ApplicationRoleApplicationPrivilegeRelationship[].class);
        ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship1 =
                applicationRoleApplicationPrivilegeRelationshipList.getBody()[0];
        assertTrue(applicationRoleApplicationPrivilegeRelationship1.getApplicationPrivilege().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationPrivilege().getId()));
        assertTrue(applicationRoleApplicationPrivilegeRelationship1.getApplicationRole().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationRole().getId()));
    }

    @Test
    @Order(4)
    void updateApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId() throws Exception {
        String url = hostname + port
                + ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH + "?env="
                + TestConfig.testEnv.toString() + "&applicationRoleApplicationPrivilegeRelationshipId=" + applicationRoleApplicationPrivilegeRelationshipId;
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<ApplicationRoleApplicationPrivilegeRelationship> request = new HttpEntity<>(updatedApplicationRoleApplicationPrivilegeRelationship, headers);
        ResponseEntity<ApplicationRoleApplicationPrivilegeRelationship[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, ApplicationRoleApplicationPrivilegeRelationship[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<ApplicationRoleApplicationPrivilegeRelationship[]> applicationRoleApplicationPrivilegeRelationshipList = this.restTemplate.exchange(
                hostname + port
                        + ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH + "?env="
                        + TestConfig.testEnv.toString() + "&applicationRoleApplicationPrivilegeRelationshipId="
                        + applicationRoleApplicationPrivilegeRelationshipId, HttpMethod.GET, request, ApplicationRoleApplicationPrivilegeRelationship[].class);
        assertTrue(applicationRoleApplicationPrivilegeRelationshipList.getStatusCode().is2xxSuccessful());
        ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship1 =
                applicationRoleApplicationPrivilegeRelationshipList.getBody()[0];
        assertTrue(applicationRoleApplicationPrivilegeRelationship1.getApplicationPrivilege().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationPrivilege().getId()));
        assertTrue(applicationRoleApplicationPrivilegeRelationship1.getApplicationRole().getId().equals(
                applicationRoleApplicationPrivilegeRelationship.getApplicationRole().getId()));
        applicationRoleApplicationPrivilegeRelationshipId = applicationRoleApplicationPrivilegeRelationship1
                .getId();
    }

    @Test
    @Order(6)
    void deleteApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId() throws Exception {
        String url = hostname + port
                + ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH + "?env="
                + TestConfig.testEnv.toString() + "&applicationRoleApplicationPrivilegeRelationshipId=" + applicationRoleApplicationPrivilegeRelationshipId;
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        applicationRoleService.deleteApplicationRoleByApplicationRoleName(
                applicationRole.getApplicationRoleName());

        applicationPrivilegeService.deleteApplicationPrivilegeByApplicationPrivilegeName(
                applicationPrivilege.getApplicationPrivilegeName());

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
