package com.umbrella.insurance.endpoints.rest.people.analysts.specialties.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.SpecialtyPrivilege;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
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
public class SpecialtyRestEndpointsTests {
    @Autowired
    private SpecialtyRestEndpoints specialtyRestEndpoints;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(specialtyRestEndpoints).isNotNull();
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

    private static Long specialtyId;
    private static String specialtyName = "usd";
    private static String updatedSpecialtyName = "yen";
    private static Specialty specialty = new Specialty();
    private static Specialty updatedSpecialty = new Specialty();

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

        specialty.setSpecialtyName(specialtyName);
        updatedSpecialty.setSpecialtyName(updatedSpecialtyName);
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
    private SpecialtyService specialtyService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private ApplicationRoleService applicationRoleService;



    @Test
    @Order(1)
    void createSpecialty() throws Exception {
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
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<Specialty> request = new HttpEntity<>(specialty, headers);


        String url = hostname + port
                + SpecialtyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Specialty> specialty1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Specialty.class);
        assertTrue(specialty1.getStatusCode().is2xxSuccessful());
        assertTrue(specialty1.getBody().getSpecialtyName().equals(specialtyName));
        specialtyId = specialty1.getBody().getId();
        updatedSpecialty.setId(specialtyId);
    }

    @Test
    @Order(2)
    void readSpecialtyBySpecialtyName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SpecialtyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&specialtyName=" + specialtyName;
        ResponseEntity<Specialty[]> specialtyList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Specialty[].class);
        assertTrue(specialtyList.getStatusCode().is2xxSuccessful());
        Specialty specialty1 = specialtyList.getBody()[0];
        assertTrue(specialty1.getSpecialtyName().equals(specialtyName));
    }

    @Test
    @Order(3)
    void readSpecialtyBySpecialtyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Specialty[]> specialtyList = this.restTemplate.exchange(
                hostname + port + SpecialtyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&specialtyId=" + specialtyId, HttpMethod.GET, request, Specialty[].class);
        assertTrue(specialtyList.getStatusCode().is2xxSuccessful());
        Specialty specialty1 = specialtyList.getBody()[0];
        assertTrue(specialty1.getSpecialtyName().equals(specialtyName));
    }

    @Test
    @Order(4)
    void updateSpecialtyBySpecialtyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Specialty> request = new HttpEntity<>(updatedSpecialty, headers);

        String url = hostname + port
                + SpecialtyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&specialtyId=" + specialtyId;
        URI uri = new URI(url);
        ResponseEntity<Specialty[]> specialtyList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Specialty[].class);
        assertTrue(specialtyList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedSpecialtyBySpecialtyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Specialty[]> specialtyList = this.restTemplate.exchange(
                hostname + port
                        + SpecialtyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&specialtyId=" + specialtyId, HttpMethod.GET, request, Specialty[].class);
        assertTrue(specialtyList.getStatusCode().is2xxSuccessful());
        Specialty specialty1 = specialtyList.getBody()[0];
        assertTrue(specialty1.getSpecialtyName().equals(updatedSpecialtyName));
        specialtyId = specialty1.getId();
    }

    @Test
    @Order(6)
    void deleteSpecialtyBySpecialtyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SpecialtyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&specialtyId=" + specialtyId;
        URI uri = new URI(url);
        ResponseEntity<Object> specialtyList = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(specialtyList.getStatusCode().is2xxSuccessful());

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
