package com.umbrella.insurance.endpoints.rest.users.fees.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.fees.v1.db.FeePrivilege;
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
public class FeeRestEndpointsTests {
    @Autowired
    private FeeRestEndpoints feeRestEndpoints;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(feeRestEndpoints).isNotNull();
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

    private static Long feeId;
    private static String feeName = "1234";
    private static Double feePercent = 2.0;
    private static Double fixedFee = 3.0;
    private static Long unitId = null;
    private static String updatedFeeName = "12345";
    private static Double updatedFeePercent = 4.0;
    private static Double updatedFixedFee = 5.0;
    private static Long updatedUnitId = null;
    private static Fee fee = new Fee();
    private static Fee updatedFee = new Fee();
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

        fee.setFeeName(feeName);
        fee.setFeePercent(feePercent);
        fee.setFixedFee(fixedFee);
        //fee.setUnit(unitId);

        updatedFee.setFeeName(updatedFeeName);
        updatedFee.setFeePercent(updatedFeePercent);
        updatedFee.setFixedFee(updatedFixedFee);
        //updatedFee.setUnit(updatedUnitId);
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
    private SessionService sessionService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private UnitService unitService;

    @Test
    @Order(1)
    void createFee() throws Exception {
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
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);
        Unit unit = unitService.getUnitByUnitName("usd").get();
        unitId = unit.getId();
        updatedUnitId = unit.getId();
        fee.setUnit(unit);
        updatedFee.setUnit(unit);
        headers.set("session", session.getSessionCode());
        HttpEntity<Fee> request = new HttpEntity<>(fee, headers);

        String url = hostname + port
                + FeePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Fee> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Fee.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Fee fee1 = response.getBody();
        assertTrue(fee1.getFeeName().equals(feeName));
        feeId = fee1.getId();
        updatedFee.setId(feeId);
    }

    @Test
    @Order(2)
    void readFeeByFeeName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + FeePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&feeName=" + feeName;
        ResponseEntity<Fee[]> feeList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Fee[].class);
        assertTrue(feeList.getStatusCode().is2xxSuccessful());
        Fee fee1 = feeList.getBody()[0];
        assertTrue(fee1.getFeeName().equals(feeName));
        assertTrue(fee1.getFeePercent().equals(feePercent));
        assertTrue(fee1.getFixedFee().equals(fixedFee));
        assertTrue(fee1.getUnit().getId().equals(unitId));
    }

    @Test
    @Order(3)
    void readFeeByFeeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Fee[]> feeList = this.restTemplate.exchange(
                hostname + port + FeePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&feeId=" + feeId, HttpMethod.GET, request, Fee[].class);
        assertTrue(feeList.getStatusCode().is2xxSuccessful());
        Fee fee1 = feeList.getBody()[0];
        assertTrue(fee1.getFeeName().equals(feeName));
        assertTrue(fee1.getFeePercent().equals(feePercent));
        assertTrue(fee1.getFixedFee().equals(fixedFee));
        assertTrue(fee1.getUnit().getId().equals(unitId));
    }

    @Test
    @Order(4)
    void updateFeeByFeeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Fee> request = new HttpEntity<>(updatedFee, headers);

        String url = hostname + port
                + FeePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&feeId=" + feeId;
        URI uri = new URI(url);
        ResponseEntity<Fee[]> feeList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Fee[].class);
        assertTrue(feeList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedFeeByFeeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Fee[]> feeList = this.restTemplate.exchange(
                hostname + port
                        + FeePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&feeId=" + feeId, HttpMethod.GET, request, Fee[].class);
        assertTrue(feeList.getStatusCode().is2xxSuccessful());
        Fee fee1 = feeList.getBody()[0];
        assertTrue(fee1.getFeeName().equals(updatedFeeName));
        assertTrue(fee1.getFeePercent().equals(updatedFeePercent));
        assertTrue(fee1.getFixedFee().equals(updatedFixedFee));
        assertTrue(fee1.getUnit().getId().equals(updatedUnitId));
        feeId = fee1.getId();
    }

    @Test
    @Order(6)
    void deleteFeeByFeeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + FeePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&feeId=" + feeId;
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

        personService.deletePersonBySsn(ssn2);
    }
}
