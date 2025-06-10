package com.umbrella.insurance.endpoints.rest.leagues.conferences.divisions.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.DivisionPrivilege;
import com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.jpa.DivisionService;
import com.umbrella.insurance.core.models.leagues.conferences.v1.db.jpa.ConferenceService;
import com.umbrella.insurance.core.models.leagues.v1.db.jpa.LeagueService;
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
public class DivisionRestEndpointsTests {
    @Autowired
    private DivisionRestEndpoints divisionRestEndpoints;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(divisionRestEndpoints).isNotNull();
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

    private static Long divisionId = null;
    private static Long gameTypeId = null;
    private static Long conferenceId = null;
    private static String divisionName = "west";
    private static String leagueName = "soccer";
    private static String conferenceName = "NCAA";
    private static String updatedDivisionName = "yen";
    private static Long leagueId = null;
    private static String gameTypeName = "soccer";

    private static GameType gameType = new GameType();
    private static Conference conference = new Conference();
    private static League league = new League();
    private static Division division = new Division();
    private static Division updatedDivision = new Division();

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
        conference.setConferenceName(conferenceName);
        league.setLeagueName(leagueName);
        division.setDivisionName(divisionName);
        updatedDivision.setDivisionName(updatedDivisionName);
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
    private GameTypeService gameTypeService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ApplicationRoleService applicationRoleService;


    @Test
    @Order(1)
    void createDivision() throws Exception {
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
        HttpEntity<Division> request = new HttpEntity<>(division, headers);

        gameType = gameTypeService.saveGameType(gameType);
        gameTypeId = gameType.getId();
        league.setGameType(gameType);

        league = leagueService.saveLeague(league);
        leagueId = league.getId();
        conference.setLeague(league);

        conference = conferenceService.saveConference(conference);
        conferenceId = conference.getId();
        division.setConference(conference);
        updatedDivision.setConference(conference);

        String url = hostname + port
                + DivisionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Division> division1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Division.class);
        assertTrue(division1.getBody().getDivisionName().equals(divisionName));
        assertTrue(division1.getBody().getConference().getId().equals(conferenceId));
        divisionId = division1.getBody().getId();
        updatedDivision.setId(divisionId);
    }

    @Test
    @Order(2)
    void readDivisionByDivisionNameAndLeagueId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + DivisionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&divisionName=" + divisionName
                + "&conferenceId=" + conferenceId;
        ResponseEntity<Division[]> divisionList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Division[].class);
        assertTrue(divisionList.getStatusCode().is2xxSuccessful());
        Division division1 = divisionList.getBody()[0];
        assertTrue(division1.getDivisionName().equals(divisionName));
        assertTrue(division1.getConference().getId().equals(conferenceId));
    }

    @Test
    @Order(3)
    void readDivisionByDivisionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Division[]> divisionList = this.restTemplate.exchange(
                hostname + port + DivisionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&divisionId=" + divisionId, HttpMethod.GET, request, Division[].class);
        assertTrue(divisionList.getStatusCode().is2xxSuccessful());
        Division division1 = divisionList.getBody()[0];
        assertTrue(division1.getDivisionName().equals(divisionName));
        assertTrue(division1.getConference().getId().equals(conferenceId));
    }

    @Test
    @Order(4)
    void updateDivisionByDivisionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Division> request = new HttpEntity<>(updatedDivision, headers);
        String url = hostname + port
                + DivisionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&divisionId=" + divisionId;
        URI uri = new URI(url);
        ResponseEntity<Division[]> divisionList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, Division[].class);
        assertTrue(divisionList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedDivisionByDivisionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(updatedDivision, headers);
        ResponseEntity<Division[]> divisionList = this.restTemplate.exchange(
                hostname + port
                        + DivisionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&divisionId=" + divisionId,
                HttpMethod.GET, request, Division[].class);
        assertTrue(divisionList.getStatusCode().is2xxSuccessful());
        Division division1 = divisionList.getBody()[0];
        assertTrue(division1.getDivisionName().equals(updatedDivisionName));
        assertTrue(division1.getConference().getId().equals(conferenceId));
        divisionId = division1.getId();
    }

    @Test
    @Order(6)
    void deleteDivisionByDivisionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + DivisionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&divisionId=" + divisionId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        conferenceService
                .deleteConference(conferenceId);

        leagueService
                .deleteLeague(leagueId);

        gameTypeService
                .deleteByGameTypeName(gameTypeName);

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
