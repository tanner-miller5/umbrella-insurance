package com.umbrella.insurance.endpoints.rest.people.analysts.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
import com.umbrella.insurance.core.models.people.analysts.v1.db.AnalystPrivilege;
import com.umbrella.insurance.core.models.people.analysts.v1.db.jpa.AnalystService;
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
public class AnalystRestEndpointsTests {
    @Autowired
    private AnalystRestEndpoints analystRestEndpoints;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    void contextLoads() throws Exception {
        assertThat(analystRestEndpoints).isNotNull();
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

    private static Long analystId;
    private static Person person = new Person();
    private static Specialty specialty = new Specialty();
    private static Analyst analyst = new Analyst();
    private static Analyst updatedAnalyst = new Analyst();
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


        specialty.setSpecialtyName("test");
        person.setFirstName("test1");
        person.setMiddleName("test2");
        person.setSurname("test3");
        person.setDateOfBirth(Date.valueOf("2000-01-01"));
        person.setSsn("123");
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
    private SpecialtyService specialtyService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnalystService analystService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PasswordService passwordService;



    @Test
    @Order(1)
    void createAnalyst() throws Exception {
        specialty = specialtyService.saveSpecialty(specialty);
        analyst.setSpecialty(specialty);
        updatedAnalyst.setSpecialty(specialty);

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

        Person pr = personService.getPersonBySsn(ssn).get();

        analyst.setPerson(pr);
        updatedAnalyst.setPerson(pr);

        headers.set("session", session.getSessionCode());
        HttpEntity<Analyst> request = new HttpEntity<>(analyst, headers);

        String url = hostname + port
                + AnalystPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Analyst> analyst1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Analyst.class);
        assertTrue(analyst1.getStatusCode().is2xxSuccessful());
        assertTrue(analyst1.getBody().getSpecialty().getId().equals(analyst.getSpecialty().getId()));
        assertTrue(analyst1.getBody().getPerson().getId().equals(analyst.getPerson().getId()));
        analystId = analyst1.getBody().getId();
        updatedAnalyst.setId(analystId);
    }

    @Test
    @Order(2)
    void readAnalystByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + AnalystPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&personId=" + analyst.getPerson().getId();
        ResponseEntity<Analyst[]> analystList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Analyst[].class);
        assertTrue(analystList.getStatusCode().is2xxSuccessful());
        Analyst analyst1 = analystList.getBody()[0];
        assertTrue(analyst1.getSpecialty().getId().equals(analyst.getSpecialty().getId()));
        assertTrue(analyst1.getPerson().getId().equals(analyst.getPerson().getId()));
    }

    @Test
    @Order(3)
    void readAnalystByAnalystId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Analyst[]> analystList = this.restTemplate.exchange(
                hostname + port + AnalystPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&analystId=" + analystId, HttpMethod.GET, request, Analyst[].class);
        assertTrue(analystList.getStatusCode().is2xxSuccessful());
        Analyst analyst1 = analystList.getBody()[0];
        assertTrue(analyst1.getSpecialty().getId().equals(analyst.getSpecialty().getId()));
        assertTrue(analyst1.getPerson().getId().equals(analyst.getPerson().getId()));
    }

    @Test
    @Order(4)
    void updateAnalystByAnalystId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Analyst> request = new HttpEntity<>(updatedAnalyst, headers);

        String url = hostname + port
                + AnalystPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&analystId=" + analystId;
        URI uri = new URI(url);
        ResponseEntity<Analyst[]> analystList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Analyst[].class);
        assertTrue(analystList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedAnalystByAnalystId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Analyst[]> analystList = this.restTemplate.exchange(
                hostname + port
                        + AnalystPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&analystId=" + analystId, HttpMethod.GET, request, Analyst[].class);
        assertTrue(analystList.getStatusCode().is2xxSuccessful());
        Analyst analyst1 = analystList.getBody()[0];
        assertTrue(analyst1.getSpecialty().getId().equals(updatedAnalyst.getSpecialty().getId()));
        assertTrue(analyst1.getPerson().getId().equals(updatedAnalyst.getPerson().getId()));
        analystId = analyst1.getId();
    }

    @Test
    @Order(6)
    void deleteAnalystByAnalystId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + AnalystPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&analystId=" + analystId;
        URI uri = new URI(url);
        ResponseEntity<Analyst[]> analystList = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Analyst[].class);
        assertTrue(analystList.getStatusCode().is2xxSuccessful());

        specialtyService.deleteBySpecialtyName(
                specialty.getSpecialtyName());

        sessionService.deleteSession(
                session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn);
    }
}
