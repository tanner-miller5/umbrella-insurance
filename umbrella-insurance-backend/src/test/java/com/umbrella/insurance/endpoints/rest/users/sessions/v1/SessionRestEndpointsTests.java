package com.umbrella.insurance.endpoints.rest.users.sessions.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.ApplicationRole;
import com.umbrella.insurance.core.models.entities.Session;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.SessionPrivilege;
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

import static com.umbrella.insurance.core.utils.Security.generateSessionId;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.umbrella.insurance.core.constants.Http.SESSION_TIMEOUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SessionRestEndpointsTests {
    @Autowired
    private SessionRestEndpoints sessionRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(sessionRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Long sessionId;

    private static String emailAddress2 = "133";
    private static String phoneNumber2 = "233";
    private static String username2 = "333";
    private static String password2 = "533";
    private static String ssn2 = "12333";
    private static String surname2 = "12233";
    private static String middle2 = "middle33";
    private static String first2 = "first33";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-16");
    public static String password = "1111";
    public static String username = "1234";

    public static String sessionCode = generateSessionId();
    public static Timestamp startDateTime = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
    public static Timestamp endDateTime = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())
            .plus(SESSION_TIMEOUT, ChronoUnit.SECONDS));
    public static Long minutesToExpire = SESSION_TIMEOUT / 60;

    public static String updatedSessionCode = generateSessionId();
    public static Timestamp updatedStartDateTime = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
    public static Timestamp updatedEndDateTime = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())
            .plus(SESSION_TIMEOUT, ChronoUnit.SECONDS));
    public static Long updatedMinutesToExpire = SESSION_TIMEOUT / 60;


    private static User user = new User();
    private static Session session1 = new Session();
    private static Session session = new Session();
    private static Session updatedSession = new Session();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();

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

        startDateTime.setNanos(0);
        endDateTime.setNanos(0);
        updatedStartDateTime.setNanos(0);
        updatedEndDateTime.setNanos(0);


        session.setSessionCode(sessionCode);
        session.setEndDateTime(endDateTime);
        session.setStartDateTime(startDateTime);
        session.setMinutesToExpire(minutesToExpire);

        updatedSession.setSessionCode(updatedSessionCode);
        updatedSession.setEndDateTime(updatedEndDateTime);
        updatedSession.setStartDateTime(updatedStartDateTime);
        updatedSession.setMinutesToExpire(updatedMinutesToExpire);
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
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PasswordService passwordService;

    @Test
    @Order(1)
    void createSession() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<CreateUserRequest> cuRequest = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, cuRequest, CreateUserResponse.class);

        session1.setSessionCode(createUserResponse.getBody().getSessionCode());

        session1 = sessionService.getSessionBySessionCode(session1.getSessionCode()).get();
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        headers.set("session", session1.getSessionCode());
        HttpEntity<Session> request = new HttpEntity<>(session, headers);

        String url = hostname + port
                + SessionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Session> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Session.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Session session2 = response.getBody();
        assertTrue(session2.getEndDateTime().equals(endDateTime));
        assertTrue(session2.getStartDateTime().equals(startDateTime));
        assertTrue(session2.getSessionCode().equals(sessionCode));
        sessionId = session2.getId();
        System.out.println("sessionId:" + sessionId);
        updatedSession.setId(sessionId);
    }

    @Test
    @Order(2)
    void readSessionBySessionCode() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session1.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SessionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sessionCode=" + sessionCode;
        ResponseEntity<Session[]> sessionList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Session[].class);
        assertTrue(sessionList.getStatusCode().is2xxSuccessful());
        Session session2 = sessionList.getBody()[0];
        assertTrue(session2.getEndDateTime().equals(endDateTime));
        assertTrue(session2.getStartDateTime().equals(startDateTime));
        assertTrue(session2.getSessionCode().equals(sessionCode));
    }

    @Test
    @Order(3)
    void readSessionBySessionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session1.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Session[]> sessionList = this.restTemplate.exchange(
                hostname + port + SessionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&sessionId=" + sessionId, HttpMethod.GET, request, Session[].class);
        Session session2 = sessionList.getBody()[0];
        assertTrue(session2.getEndDateTime().equals(endDateTime));
        assertTrue(session2.getStartDateTime().equals(startDateTime));
        assertTrue(session2.getSessionCode().equals(sessionCode));
        assertTrue(session2.getMinutesToExpire().equals(minutesToExpire));
    }

    @Test
    @Order(4)
    void updateSessionBySessionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session1.getSessionCode());
        HttpEntity<Session> request = new HttpEntity<>(updatedSession, headers);

        String url = hostname + port
                + SessionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sessionId=" + sessionId;
        URI uri = new URI(url);
        ResponseEntity<Session[]> sessionList = this.restTemplate.exchange(
                uri, HttpMethod.PUT, request, Session[].class);
        assertTrue(sessionList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedSessionBySessionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session1.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Session[]> sessionList = this.restTemplate.exchange(
                hostname + port
                        + SessionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&sessionId=" + sessionId, HttpMethod.GET, request, Session[].class);
        assertTrue(sessionList.getStatusCode().is2xxSuccessful());
        Session session2 = sessionList.getBody()[0];
        assertTrue(session2.getEndDateTime().equals(updatedEndDateTime));
        assertTrue(session2.getStartDateTime().equals(updatedStartDateTime));
        assertTrue(session2.getSessionCode().equals(updatedSessionCode));
        assertTrue(session2.getMinutesToExpire().equals(updatedMinutesToExpire));
    }

    @Test
    @Order(6)
    void deleteSessionBySessionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session1.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SessionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sessionId=" + sessionId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        sessionService.deleteSession(
                session1.getId());


        passwordService.deletePasswordByUserId(session1.getUser().getId());


        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session1.getUser().getId(), applicationRole.getId());


        userService.deleteUser(session1.getUser().getId());


        personService.deletePersonBySsn(ssn2);


    }
}
