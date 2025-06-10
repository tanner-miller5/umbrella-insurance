package com.umbrella.insurance.endpoints.rest.people.referees.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.referees.v1.db.RefereePrivilege;
import com.umbrella.insurance.core.models.people.referees.v1.db.jpa.RefereeService;
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
public class RefereeRestEndpointsTests {
    @Autowired
    private RefereeRestEndpoints refereeRestEndpoints;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(refereeRestEndpoints).isNotNull();
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

    private static String weight = "1";
    private static String college = "2";
    private static String height = "3";
    private static String draftInfo = "4";
    private static String refereeStatus = "5";
    private static String experience = "6";
    private static String birthPlace = "7";
    private static String refereePosition = "8";
    private static String jerseyNumber = "9";
    private static String updatedWeight = " 10";
    private static String updatedCollege = "11";
    private static String updatedHeight = "12";
    private static String updatedDraftInfo = "13";
    private static String updatedRefereeStatus = "14";
    private static String updatedExperience = "15";
    private static String updatedBirthPlace = "16";
    private static String updatedRefereePosition = "17";
    private static String updatedJerseyNumber = "18";
    private static String gameTypeName = "19";
    private static Long refereeId;
    private static GameType gameType = new GameType();
    private static Person person = new Person();
    private static Referee referee = new Referee();
    private static Referee updatedReferee = new Referee();
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

        gameType.setGameTypeName(gameTypeName);
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
    private RefereeService refereeService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Test
    @Order(1)
    void createReferee() throws Exception {
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
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<Referee> request = new HttpEntity<>(referee, headers);

        person = personService.getPersonBySsn(
                person.getSsn()).get();
        referee.setPerson(person);
        updatedReferee.setPerson(person);

        String url = hostname + port
                + RefereePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Referee> referee1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Referee.class);
        assertTrue(referee1.getStatusCode().is2xxSuccessful());
        assertTrue(referee1.getBody().getPerson().getId().equals(referee.getPerson().getId()));
        refereeId = referee1.getBody().getId();
        updatedReferee.setId(refereeId);
    }

    @Test
    @Order(2)
    void readRefereeByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + RefereePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&personId=" + referee.getPerson().getId();
        ResponseEntity<Referee[]> refereeList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Referee[].class);
        assertTrue(refereeList.getStatusCode().is2xxSuccessful());
        Referee referee1 = refereeList.getBody()[0];
        assertTrue(referee1.getPerson().getId().equals(referee.getPerson().getId()));
    }

    @Test
    @Order(3)
    void readRefereeByRefereeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Referee[]> refereeList = this.restTemplate.exchange(
                hostname + port + RefereePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&refereeId=" + refereeId, HttpMethod.GET, request, Referee[].class);
        assertTrue(refereeList.getStatusCode().is2xxSuccessful());
        Referee referee1 = refereeList.getBody()[0];
        assertTrue(referee1.getPerson().getId().equals(referee.getPerson().getId()));
    }

    @Test
    @Order(4)
    void updateRefereeByRefereeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Referee> request = new HttpEntity<>(updatedReferee, headers);

        String url = hostname + port
                + RefereePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&refereeId=" + refereeId;
        URI uri = new URI(url);
        ResponseEntity<Referee[]> refereeList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Referee[].class);
        assertTrue(refereeList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedRefereeByRefereeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Referee[]> refereeList = this.restTemplate.exchange(
                hostname + port
                        + RefereePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&refereeId=" + refereeId, HttpMethod.GET, request, Referee[].class);
        assertTrue(refereeList.getStatusCode().is2xxSuccessful());
        Referee referee1 = refereeList.getBody()[0];
        assertTrue(referee1.getPerson().getId().equals(updatedReferee.getPerson().getId()));
        refereeId = referee1.getId();
    }

    @Test
    @Order(6)
    void deleteRefereeByRefereeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + RefereePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&refereeId=" + refereeId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

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
