package com.umbrella.insurance.endpoints.rest.applicationPrivileges.v1;


import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.ApplicationPrivilegePrivilege;
import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa.ApplicationPrivilegeService;
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
public class ApplicationPrivilegeRestEndpointsTests {
    @Autowired
    private ApplicationPrivilegeRestEndpoints applicationPrivilegeRestEndpoints;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(applicationPrivilegeRestEndpoints).isNotNull();
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
    private static ApplicationPrivilege applicationPrivilege = new ApplicationPrivilege();
    private static ApplicationPrivilege updatedApplicationPrivilege = new ApplicationPrivilege();
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

        applicationPrivilege.setApplicationPrivilegeName(applicationPrivilegeName);
        applicationPrivilege.setPath(path);
        applicationPrivilege.setMethod(method);
        applicationPrivilege.setAccess(access);

        updatedApplicationPrivilege.setApplicationPrivilegeName(updatedApplicationPrivilegeName);
        updatedApplicationPrivilege.setPath(updatedPath);
        updatedApplicationPrivilege.setMethod(updatedMethod);
        updatedApplicationPrivilege.setAccess(updatedAccess);
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
    private ApplicationPrivilegeService applicationPrivilegeService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SessionService sessionService;

    @Test
    @Order(1)
    void createApplicationPrivilege() throws Exception {
        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        CreateUserResponse createUserResponse = this.restTemplate.postForObject(
                createUserUrl, createUserRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        //applicationPrivilege.setSessionId(session.getSessionId());
        //updatedApplicationPrivilege.setSessionId(session.getSessionId());
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        String url = hostname + port
                + ApplicationPrivilegePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<ApplicationPrivilege> request = new HttpEntity<>(applicationPrivilege, headers);
        ResponseEntity<ApplicationPrivilege> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, ApplicationPrivilege.class);
        assertTrue(response.getBody().getApplicationPrivilegeName().equals(applicationPrivilegeName));
        assertTrue(response.getBody().getPath().equals(path));
        assertTrue(response.getBody().getMethod().equals(method));
        assertTrue(response.getBody().getAccess().equals(access));
        applicationPrivilegeId = response.getBody().getId();
        updatedApplicationPrivilege.setId(applicationPrivilegeId);
    }

    @Test
    @Order(2)
    void readApplicationPrivilegeByApplicationPrivilegeName() throws Exception {
        String url = hostname + port
                + ApplicationPrivilegePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&applicationPrivilegeName=" + applicationPrivilegeName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<ApplicationPrivilege[]> applicationPrivilegeList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, ApplicationPrivilege[].class);
        ApplicationPrivilege applicationPrivilege1 = applicationPrivilegeList.getBody()[0];
        assertTrue(applicationPrivilege1.getApplicationPrivilegeName().equals(applicationPrivilegeName));
        assertTrue(applicationPrivilege1.getPath().equals(path));
        assertTrue(applicationPrivilege1.getMethod().equals(method));
        assertTrue(applicationPrivilege1.getAccess().equals(access));
    }

    @Test
    @Order(3)
    void readApplicationPrivilegeByApplicationPrivilegeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<ApplicationPrivilege[]> applicationPrivilegeList = this.restTemplate.exchange(
                hostname + port + ApplicationPrivilegePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&applicationPrivilegeId=" + applicationPrivilegeId, HttpMethod.GET, request, ApplicationPrivilege[].class);
        assertTrue(applicationPrivilegeList.getStatusCode().is2xxSuccessful());
        ApplicationPrivilege applicationPrivilege1 = applicationPrivilegeList.getBody()[0];
        assertTrue(applicationPrivilege1.getApplicationPrivilegeName().equals(applicationPrivilegeName));
        assertTrue(applicationPrivilege1.getPath().equals(path));
        assertTrue(applicationPrivilege1.getMethod().equals(method));
        assertTrue(applicationPrivilege1.getAccess().equals(access));
    }

    @Test
    @Order(4)
    void updateApplicationPrivilegeByApplicationPrivilegeId() throws Exception {
        String url = hostname + port
                + ApplicationPrivilegePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&applicationPrivilegeId=" + applicationPrivilegeId;
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<ApplicationPrivilege> request = new HttpEntity<>(updatedApplicationPrivilege, headers);
        ResponseEntity<ApplicationPrivilege[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, ApplicationPrivilege[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedApplicationPrivilegeByApplicationPrivilegeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<ApplicationPrivilege[]> applicationPrivilegeList = this.restTemplate.exchange(
                hostname + port
                        + ApplicationPrivilegePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&applicationPrivilegeId=" + applicationPrivilegeId,
                HttpMethod.GET, request, ApplicationPrivilege[].class);
        assertTrue(applicationPrivilegeList.getStatusCode().is2xxSuccessful());
        ApplicationPrivilege applicationPrivilege1 = applicationPrivilegeList.getBody()[0];
        assertTrue(applicationPrivilege1.getApplicationPrivilegeName().equals(updatedApplicationPrivilegeName));
        assertTrue(applicationPrivilege1.getPath().equals(updatedPath));
        assertTrue(applicationPrivilege1.getMethod().equals(updatedMethod));
        assertTrue(applicationPrivilege1.getAccess().equals(updatedAccess));
        applicationPrivilegeId = applicationPrivilege1.getId();
    }

    @Test
    @Order(6)
    void deleteApplicationPrivilegeByApplicationPrivilegeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + ApplicationPrivilegePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&applicationPrivilegeId=" + applicationPrivilegeId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

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
