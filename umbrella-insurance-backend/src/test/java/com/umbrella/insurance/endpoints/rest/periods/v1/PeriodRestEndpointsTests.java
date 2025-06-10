package com.umbrella.insurance.endpoints.rest.periods.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.games.v1.db.jpa.GameService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa.PeriodStatusService;
import com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa.PeriodTypeService;
import com.umbrella.insurance.core.models.periods.v1.db.PeriodPrivilege;
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
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeriodRestEndpointsTests {
    @Autowired
    private PeriodRestEndpoints periodRestEndpoints;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws Exception {
        assertThat(periodRestEndpoints).isNotNull();
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

    private static Long periodId;
    private static Long periodTypeId;
    private static Long periodStatusId;
    private static Long gameId;
    private static Long periodNumberValue = 1L;
    private static String gameName = "a vs b";
    private static Timestamp dateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static String periodTypeName = "1st quarter";
    private static String periodStatusName = "PENDING";
    private static PeriodStatus periodStatus = new PeriodStatus();
    private static PeriodType periodType = new PeriodType();
    private static Game game = new Game();
    private static Period period = new Period();
    private static Period updatedPeriod = new Period();
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

        game.setGameName(gameName);
        game.setDateTime(dateTime);
        periodType.setPeriodTypeName(periodTypeName);
        periodStatus.setPeriodStatusName(periodStatusName);

        period.setPeriodNumber(periodNumberValue);
        updatedPeriod.setPeriodNumber(periodNumberValue);
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
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PeriodTypeService periodTypeService;

    @Autowired
    private PeriodStatusService periodStatusService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PersonService personService;

    @Test
    @Order(1)
    void createPeriod() throws Exception {
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
        userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<Period> request = new HttpEntity<>(period, headers);

        periodTypeService.savePeriodType(periodType);
        period.setPeriodType(periodType);
        updatedPeriod.setPeriodType(periodType);
        periodTypeId = periodType.getId();

        periodStatusService.savePeriodStatus(periodStatus);
        period.setPeriodStatus(periodStatus);
        updatedPeriod.setPeriodStatus(periodStatus);
        periodStatusId = periodStatus.getId();

        game = gameService.saveGame(game);
        period.setGame(game);
        updatedPeriod.setGame(game);
        gameId = game.getId();

        String url = hostname + port
                + PeriodPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Period> period1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Period.class);
        assertTrue(period1.getStatusCode().is2xxSuccessful());
        assertTrue(period1.getBody().getGame().getId().equals(gameId));
        assertTrue(period1.getBody().getPeriodType().getId().equals(periodTypeId));
        assertTrue(period1.getBody().getPeriodStatus().getId().equals(periodStatusId));
        assertTrue(period1.getBody().getPeriodNumber().equals(periodNumberValue));
        periodId = period1.getBody().getId();
        updatedPeriod.setId(periodId);
    }

    @Test
    @Order(2)
    void readPeriodByPeriodName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PeriodPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&gameId=" + gameId
                + "&periodNumber=" + periodNumberValue;
        ResponseEntity<Period[]> periodList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Period[].class);
        assertTrue(periodList.getStatusCode().is2xxSuccessful());
        Period period1 = periodList.getBody()[0];
        assertTrue(period1.getGame().getId().equals(gameId));
        assertTrue(period1.getPeriodType().getId().equals(periodTypeId));
        assertTrue(period1.getPeriodStatus().getId().equals(periodStatusId));
        assertTrue(period1.getPeriodNumber().equals(periodNumberValue));
    }

    @Test
    @Order(3)
    void readPeriodByPeriodId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Period[]> periodList = this.restTemplate.exchange(
                hostname + port + PeriodPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&periodId=" + periodId, HttpMethod.GET, request, Period[].class);
        assertTrue(periodList.getStatusCode().is2xxSuccessful());
        Period period1 = periodList.getBody()[0];
        assertTrue(period1.getGame().getId().equals(gameId));
        assertTrue(period1.getPeriodType().getId().equals(periodTypeId));
        assertTrue(period1.getPeriodStatus().getId().equals(periodStatusId));
        assertTrue(period1.getPeriodNumber().equals(periodNumberValue));
    }

    @Test
    @Order(4)
    void updatePeriodByPeriodId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Period> request = new HttpEntity<>(updatedPeriod, headers);

        String url = hostname + port
                + PeriodPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&periodId=" + periodId;
        URI uri = new URI(url);
        ResponseEntity<Period[]> periodList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Period[].class);
        assertTrue(periodList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedPeriodByPeriodId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Period[]> periodList = this.restTemplate.exchange(
                hostname + port
                        + PeriodPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&periodId=" + periodId, HttpMethod.GET, request, Period[].class);
        assertTrue(periodList.getStatusCode().is2xxSuccessful());
        Period period1 = periodList.getBody()[0];
        assertTrue(period1.getGame().getId().equals(gameId));
        assertTrue(period1.getPeriodType().getId().equals(periodTypeId));
        assertTrue(period1.getPeriodStatus().getId().equals(periodStatusId));
        assertTrue(period1.getPeriodNumber().equals(periodNumberValue));
        periodId = period1.getId();
    }

    @Test
    @Order(6)
    void deletePeriodByPeriodId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PeriodPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&periodId=" + periodId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        periodStatusService.deletePeriodStatusByPeriodStatusName(periodStatusName);

        periodTypeService.deletePeriodTypeByPeriodTypeName(periodTypeName);

        gameService.deleteGameByGameName(gameName);

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
