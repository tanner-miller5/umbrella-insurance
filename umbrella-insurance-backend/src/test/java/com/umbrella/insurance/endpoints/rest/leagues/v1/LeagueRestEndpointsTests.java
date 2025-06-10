package com.umbrella.insurance.endpoints.rest.leagues.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.leagues.v1.db.LeaguePrivilege;
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
public class LeagueRestEndpointsTests {
    @Autowired
    private LeagueRestEndpoints leagueRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(leagueRestEndpoints).isNotNull();
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

    private static Long leagueId = null;
    private static String leagueName = "usd";
    private static String gameTypeName = "Soccer";
    private static String updatedLeagueName = "yen";
    private static Long gameTypeId = null;
    private static GameType gameType = new GameType();
    private static League league = new League();
    private static League updatedLeague = new League();
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
        updatedLeague.setLeagueName(updatedLeagueName);
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
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private GameTypeService gameTypeService;

    @Test
    @Order(1)
    void createLeague() throws Exception {
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
        HttpEntity<League> request = new HttpEntity<>(league, headers);

        gameType = gameTypeService.getGameTypeByGameTypeName(gameType.getGameTypeName()).get();
        gameTypeId = gameType.getId();
        league.setGameType(gameType);
        updatedLeague.setGameType(gameType);

        String url = hostname + port
                + LeaguePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<League> league1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, League.class);
        assertTrue(league1.getStatusCode().is2xxSuccessful());
        assertTrue(league1.getBody().getLeagueName().equals(leagueName));
        assertTrue(league1.getBody().getGameType().getId().equals(gameTypeId));
        leagueId = league1.getBody().getId();
        updatedLeague.setId(leagueId);
    }

    @Test
    @Order(2)
    void readLeagueByLeagueNameAndGameTypeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + LeaguePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&leagueName=" + leagueName
                + "&gameTypeId=" + gameTypeId;
        ResponseEntity<League[]> leagueList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, League[].class);
        assertTrue(leagueList.getStatusCode().is2xxSuccessful());
        League league1 = leagueList.getBody()[0];
        assertTrue(league1.getLeagueName().equals(leagueName));
        assertTrue(league1.getGameType().getId().equals(gameTypeId));
    }

    @Test
    @Order(3)
    void readLeagueByLeagueId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<League[]> leagueList = this.restTemplate.exchange(
                hostname + port + LeaguePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&leagueId=" + leagueId, HttpMethod.GET, request, League[].class);
        League league1 = leagueList.getBody()[0];
        assertTrue(league1.getLeagueName().equals(leagueName));
        assertTrue(league1.getGameType().getId().equals(gameTypeId));
    }

    @Test
    @Order(4)
    void updateLeagueByLeagueId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<League> request = new HttpEntity<>(updatedLeague, headers);

        String url = hostname + port
                + LeaguePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&leagueId=" + leagueId;
        URI uri = new URI(url);
        ResponseEntity<League[]> leagueList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, League[].class);
        assertTrue(leagueList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedLeagueByLeagueId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<League[]> leagueList = this.restTemplate.exchange(
                hostname + port
                        + LeaguePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&leagueId=" + leagueId, HttpMethod.GET, request, League[].class);
        assertTrue(leagueList.getStatusCode().is2xxSuccessful());
        League league1 = leagueList.getBody()[0];
        assertTrue(league1.getLeagueName().equals(updatedLeagueName));
        assertTrue(league1.getGameType().getId().equals(gameTypeId));
        leagueId = league1.getId();
    }

    @Test
    @Order(6)
    void deleteLeagueByLeagueId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + LeaguePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&leagueId=" + leagueId;
        URI uri = new URI(url);
        ResponseEntity<Object> leagueList = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(leagueList.getStatusCode().is2xxSuccessful());

        //gameTypeService
        //       .deleteByGameTypeName(gameTypeName);

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
