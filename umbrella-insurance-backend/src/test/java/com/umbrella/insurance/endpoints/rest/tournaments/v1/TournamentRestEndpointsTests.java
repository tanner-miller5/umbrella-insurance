package com.umbrella.insurance.endpoints.rest.tournaments.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa.TournamentTypeService;
import com.umbrella.insurance.core.models.tournaments.v1.db.TournamentPrivilege;
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
public class TournamentRestEndpointsTests {
    @Autowired
    private TournamentRestEndpoints tournamentRestEndpoints;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(tournamentRestEndpoints).isNotNull();
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

    private static Long tournamentId;
    private static String tournamentName = "1234";
    private static String updatedTournamentName = "12345";
    private static String tournamentTypeName = "1234";
    private static TournamentType tournamentType = new TournamentType();
    private static Tournament tournament = new Tournament();
    private static Tournament updatedTournament = new Tournament();
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

        tournamentType.setTournamentTypeName(tournamentTypeName);
        tournament.setTournamentName(tournamentName);

        updatedTournament.setTournamentName(updatedTournamentName);
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
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private TournamentTypeService tournamentTypeService;

    @Test
    @Order(1)
    void createTournament() throws Exception {
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
        User user = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        tournamentTypeService.saveTournamentType(tournamentType);
        tournament.setTournamentType(tournamentType);
        updatedTournament.setTournamentType(tournamentType);

        headers.set("session", session.getSessionCode());
        HttpEntity<Tournament> request = new HttpEntity<>(tournament, headers);

        String url = hostname + port
                + TournamentPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Tournament> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Tournament.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Tournament tournament1 = response.getBody();
        assertTrue(tournament1.getTournamentName().equals(tournamentName));
        tournamentId = tournament1.getId();
        updatedTournament.setId(tournamentId);
    }

    @Test
    @Order(2)
    void readTournamentByTournamentName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TournamentPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&tournamentName=" + tournamentName;
        ResponseEntity<Tournament[]> tournamentList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Tournament[].class);
        assertTrue(tournamentList.getStatusCode().is2xxSuccessful());
        Tournament tournament1 = tournamentList.getBody()[0];
        assertTrue(tournament1.getTournamentName().equals(tournamentName));
    }

    @Test
    @Order(3)
    void readTournamentByTournamentId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Tournament[]> tournamentList = this.restTemplate.exchange(
                hostname + port + TournamentPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&tournamentId=" + tournamentId, HttpMethod.GET, request, Tournament[].class);
        assertTrue(tournamentList.getStatusCode().is2xxSuccessful());
        Tournament tournament1 = tournamentList.getBody()[0];
        assertTrue(tournament1.getTournamentName().equals(tournamentName));
    }

    @Test
    @Order(4)
    void updateTournamentByTournamentId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Tournament> request = new HttpEntity<>(updatedTournament, headers);

        String url = hostname + port
                + TournamentPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&tournamentId=" + tournamentId;
        URI uri = new URI(url);
        ResponseEntity<Tournament[]> tournamentList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Tournament[].class);
        assertTrue(tournamentList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedTournamentByTournamentId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Tournament[]> tournamentList = this.restTemplate.exchange(
                hostname + port
                        + TournamentPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&tournamentId=" + tournamentId, HttpMethod.GET, request, Tournament[].class);
        assertTrue(tournamentList.getStatusCode().is2xxSuccessful());
        Tournament tournament1 = tournamentList.getBody()[0];
        assertTrue(tournament1.getTournamentName().equals(updatedTournamentName));
        tournamentId = tournament1.getId();
    }

    @Test
    @Order(6)
    void deleteTournamentByTournamentId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TournamentPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&tournamentId=" + tournamentId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        tournamentTypeService.deleteTournamentTypeByTournamentTypeName(
                tournamentType.getTournamentTypeName());

        sessionService.deleteSession(
                session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn2);
    }
}
