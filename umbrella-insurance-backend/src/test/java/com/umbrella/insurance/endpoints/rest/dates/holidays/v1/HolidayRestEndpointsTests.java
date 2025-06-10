package com.umbrella.insurance.endpoints.rest.dates.holidays.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.dates.holidays.v1.db.HolidayPrivilege;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
public class HolidayRestEndpointsTests {
    @Autowired
    private HolidayRestEndpoints holidayRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(holidayRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Long holidayId;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static String holidayName = "1234";
    private static Date holidayDate = Date.valueOf("1111-11-11");
    private static String updatedHolidayName = "12345";
    private static Date updatedHolidayDate = Date.valueOf("1111-11-11");

    private static Holiday holiday = new Holiday();
    private static Holiday updatedHoliday = new Holiday();
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
        
        holiday.setHolidayName(holidayName);
        holiday.setHolidayDate(holidayDate);
        updatedHoliday.setHolidayName(updatedHolidayName);
        updatedHoliday.setHolidayDate(updatedHolidayDate);
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
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PersonService personService;


    @Test
    @Order(1)
    void createHoliday() throws Exception {
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
        String url = hostname + port
                + HolidayPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<Holiday> request = new HttpEntity<>(holiday, headers);
        ResponseEntity<Holiday> holiday1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Holiday.class);
        assertTrue(holiday1.getStatusCode().is2xxSuccessful());
        assertTrue(holiday1.getBody().getHolidayName().equals(holidayName));
        holidayId = holiday1.getBody().getId();
        updatedHoliday.setId(holidayId);
    }

    @Test
    @Order(2)
    void readHolidayByHolidayName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + HolidayPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&holidayName=" + holidayName;

        ResponseEntity<Holiday[]> holidayList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Holiday[].class);
        assertTrue(holidayList.getStatusCode().is2xxSuccessful());
        Holiday holiday1 = holidayList.getBody()[0];
        assertTrue(holiday1.getHolidayName().equals(holidayName));
    }

    @Test
    @Order(3)
    void readHolidayByHolidayId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Holiday[]> holidayList = this.restTemplate.exchange(
                hostname + port + HolidayPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&holidayId=" + holidayId, HttpMethod.GET, request, Holiday[].class);
        assertTrue(holidayList.getStatusCode().is2xxSuccessful());
        Holiday holiday1 = holidayList.getBody()[0];
        assertTrue(holiday1.getHolidayName().equals(holidayName));
    }

    @Test
    @Order(4)
    void updateHolidayByHolidayId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Holiday> request = new HttpEntity<>(updatedHoliday, headers);
        String url = hostname + port
                + HolidayPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&holidayId=" + holidayId;
        URI uri = new URI(url);
        ResponseEntity<Holiday[]> holidayList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, Holiday[].class);
        assertTrue(holidayList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedHolidayByHolidayId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Holiday[]> holidayList = this.restTemplate.exchange(
                hostname + port
                        + HolidayPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&holidayId=" + holidayId, HttpMethod.GET, request, Holiday[].class);
        assertTrue(holidayList.getStatusCode().is2xxSuccessful());
        Holiday holiday1 = holidayList.getBody()[0];
        assertTrue(holiday1.getHolidayName().equals(updatedHolidayName));
        holidayId = holiday1.getId();
    }

    @Test
    @Order(6)
    void deleteHolidayByHolidayId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + HolidayPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&holidayId=" + holidayId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri,
                HttpMethod.DELETE, request, Object.class);
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
