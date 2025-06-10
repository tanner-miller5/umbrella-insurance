package com.umbrella.insurance.endpoints.rest.weeks.v1;

import com.umbrella.insurance.config.TestConfig;
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
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
import com.umbrella.insurance.core.models.weeks.v1.db.WeekPrivilege;
import com.umbrella.insurance.core.models.weeks.v1.db.jpa.WeekService;
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
public class WeeksRestEndpointsTests {
    @Autowired
    private WeekRestEndpoints weekRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(weekRestEndpoints).isNotNull();
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

    private static Long weekId;
    private static Long weekNumber = 1l;
    private static String weekTitle = "1";
    private static Date weekStartDate = Date.valueOf("2023-12-12");
    private static Date weekEndDate = Date.valueOf("2024-05-20");
    private static Long updatedWeekNumber = 2l;
    private static String updatedWeekTitle = "2";
    private static Date updatedWeekStartDate = Date.valueOf("2021-12-12");
    private static Date updatedWeekEndDate = Date.valueOf("2020-12-12");
    private static Week week = new Week();
    private static Week updatedWeek = new Week();
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

        week.setWeekNumber(weekNumber);
        week.setWeekTitle(weekTitle);
        week.setWeekStartDate(weekStartDate);
        week.setWeekEndDate(weekEndDate);

        updatedWeek.setWeekNumber(updatedWeekNumber);
        updatedWeek.setWeekTitle(updatedWeekTitle);
        updatedWeek.setWeekStartDate(updatedWeekStartDate);
        updatedWeek.setWeekEndDate(updatedWeekEndDate);
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
    private PersonService personService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private WeekService weekService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;



    @Test
    @Order(1)
    void createWeek() throws Exception {
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
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        headers.set("session", session.getSessionCode());
        HttpEntity<Week> request = new HttpEntity<>(week, headers);
        String url = hostname + port
                + WeekPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Week> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Week.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Week week1 = response.getBody();
        assertTrue(week1.getWeekTitle().equals(weekTitle));
        assertTrue(week1.getWeekNumber().equals(weekNumber));
        assertTrue(week1.getWeekStartDate().equals(weekStartDate));
        assertTrue(week1.getWeekEndDate().equals(weekEndDate));
        weekId = week1.getId();
        updatedWeek.setId(weekId);
    }

    @Test
    @Order(2)
    void readWeekByWeekName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + WeekPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&weekTitle=" + weekTitle;
        ResponseEntity<Week[]> weekList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Week[].class);
        assertTrue(weekList.getStatusCode().is2xxSuccessful());
        Week week1 = weekList.getBody()[0];
        assertTrue(week1.getWeekTitle().equals(weekTitle));
        assertTrue(week1.getWeekNumber().equals(weekNumber));
        assertTrue(week1.getWeekStartDate().equals(weekStartDate));
        assertTrue(week1.getWeekEndDate().equals(weekEndDate));
    }

    @Test
    @Order(3)
    void readWeekByWeekId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Week[]> weekList = this.restTemplate.exchange(
                hostname + port + WeekPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&weekId=" + weekId, HttpMethod.GET, request, Week[].class);
        assertTrue(weekList.getStatusCode().is2xxSuccessful());
        Week week1 = weekList.getBody()[0];
        assertTrue(week1.getWeekTitle().equals(weekTitle));
        assertTrue(week1.getWeekNumber().equals(weekNumber));
        assertTrue(week1.getWeekStartDate().equals(weekStartDate));
        assertTrue(week1.getWeekEndDate().equals(weekEndDate));
    }

    @Test
    @Order(4)
    void updateWeekByWeekId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Week> request = new HttpEntity<>(updatedWeek, headers);

        String url = hostname + port
                + WeekPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&weekId=" + weekId;
        URI uri = new URI(url);
        ResponseEntity<Week[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Week[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedWeekByWeekId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Week[]> weekList = this.restTemplate.exchange(
                hostname + port
                        + WeekPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&weekId=" + weekId, HttpMethod.GET, request, Week[].class);
        assertTrue(weekList.getStatusCode().is2xxSuccessful());
        Week week1 = weekList.getBody()[0];
        assertTrue(week1.getWeekTitle().equals(updatedWeekTitle));
        assertTrue(week1.getWeekNumber().equals(updatedWeekNumber));
        assertTrue(week1.getWeekStartDate().equals(updatedWeekStartDate));
        assertTrue(week1.getWeekEndDate().equals(updatedWeekEndDate));
        weekId = week1.getId();
    }

    @Test
    @Order(6)
    void deleteWeekByWeekId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + WeekPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&weekId=" + weekId;
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


        personService.deletePersonBySsn(ssn2);

    }
}
