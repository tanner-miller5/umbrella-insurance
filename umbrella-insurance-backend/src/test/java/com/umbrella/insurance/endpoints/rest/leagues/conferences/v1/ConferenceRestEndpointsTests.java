package com.umbrella.insurance.endpoints.rest.leagues.conferences.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.games.v1.db.jpa.GameService;
import com.umbrella.insurance.core.models.leagues.conferences.v1.db.ConferencePrivilege;
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
public class ConferenceRestEndpointsTests {
    @Autowired
    private ConferenceRestEndpoints conferenceRestEndpoints;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(conferenceRestEndpoints).isNotNull();
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

    private static Long conferenceId = null;
    private static Long gameTypeId = null;
    private static String conferenceName = "usd";
    private static String leagueName = "soccer";
    private static String updatedConferenceName = "yen";
    private static Long leagueId = null;
    private static String gameTypeName = "soccer";

    private static GameType gameType = new GameType();
    private static League league = new League();
    private static Conference conference = new Conference();
    private static Conference updatedConference = new Conference();

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
        league.setLeagueName(leagueName);
        conference.setConferenceName(conferenceName);
        updatedConference.setConferenceName(updatedConferenceName);
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
    private LeagueService leagueService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private GameTypeService gameTypeService;


    @Test
    @Order(1)
    void createConference() throws Exception {
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
        HttpEntity<Conference> request = new HttpEntity<>(conference, headers);

        gameType = gameTypeService.saveGameType(gameType);
        gameTypeId = gameType.getId();
        league.setGameType(gameType);

        league = leagueService.saveLeague(league);
        leagueId = league.getId();
        conference.setLeague(league);
        updatedConference.setLeague(league);

        String url = hostname + port
                + ConferencePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Conference> conference1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Conference.class);
        assertTrue(conference1.getStatusCode().is2xxSuccessful());
        assertTrue(conference1.getBody().getConferenceName().equals(conferenceName));
        assertTrue(conference1.getBody().getLeague().getId().equals(leagueId));
        conferenceId = conference1.getBody().getId();
        updatedConference.setId(conferenceId);
    }

    @Test
    @Order(2)
    void readConferenceByConferenceNameAndLeagueId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + ConferencePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&conferenceName=" + conferenceName
                + "&leagueId=" + leagueId;
        ResponseEntity<Conference[]> conferenceList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Conference[].class);
        assertTrue(conferenceList.getStatusCode().is2xxSuccessful());
        Conference conference1 = conferenceList.getBody()[0];
        assertTrue(conference1.getConferenceName().equals(conferenceName));
        assertTrue(conference1.getLeague().getId().equals(leagueId));
    }

    @Test
    @Order(3)
    void readConferenceByConferenceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Conference[]> conferenceList = this.restTemplate.exchange(
                hostname + port + ConferencePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&conferenceId=" + conferenceId, HttpMethod.GET, request, Conference[].class);
        assertTrue(conferenceList.getStatusCode().is2xxSuccessful());
        Conference conference1 = conferenceList.getBody()[0];
        assertTrue(conference1.getConferenceName().equals(conferenceName));
        assertTrue(conference1.getLeague().getId().equals(leagueId));
    }

    @Test
    @Order(4)
    void updateConferenceByConferenceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Conference> request = new HttpEntity<>(updatedConference, headers);

        String url = hostname + port
                + ConferencePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&conferenceId=" + conferenceId;
        URI uri = new URI(url);
        ResponseEntity<Conference[]> conferenceList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Conference[].class);
        assertTrue(conferenceList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedConferenceByConferenceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Conference[]> conferenceList = this.restTemplate.exchange(
                hostname + port
                        + ConferencePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&conferenceId=" + conferenceId, HttpMethod.GET, request, Conference[].class);
        assertTrue(conferenceList.getStatusCode().is2xxSuccessful());
        Conference conference1 = conferenceList.getBody()[0];
        assertTrue(conference1.getConferenceName().equals(updatedConferenceName));
        assertTrue(conference1.getLeague().getId().equals(leagueId));
        conferenceId = conference1.getId();
    }

    @Test
    @Order(6)
    void deleteConferenceByConferenceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + ConferencePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&conferenceId=" + conferenceId;
        URI uri = new URI(url);
        this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);

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
