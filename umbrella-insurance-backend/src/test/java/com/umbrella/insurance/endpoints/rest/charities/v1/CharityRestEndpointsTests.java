package com.umbrella.insurance.endpoints.rest.charities.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.charities.v1.db.CharityPrivilege;
import com.umbrella.insurance.core.models.databases.v1.Database;
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
public class CharityRestEndpointsTests {

    @Autowired
    private CharityRestEndpoints charityRestEndpoints;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(charityRestEndpoints).isNotNull();
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
    private static Long charityId;
    private static String charityName = "1234";
    private static String updatedCharityName = "12345";
    private static Charity charity = new Charity();
    private static Charity updatedCharity = new Charity();
    private static Session session = new Session();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
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

        charity.setCharityName(charityName);

        updatedCharity.setCharityName(updatedCharityName);
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
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;


    @Test
    @Order(1)
    void createCharity() throws Exception {
        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        CreateUserResponse createUserResponse = this.restTemplate.postForObject(
                createUserUrl, createUserRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Charity> request = new HttpEntity<>(charity, headers);
        String url = hostname + port
                + CharityPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Charity> charity1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Charity.class);
        assertTrue(charity1.getStatusCode().is2xxSuccessful());
        assertTrue(charity1.getBody().getCharityName().equals(charityName));
        charityId = charity1.getBody().getId();
        updatedCharity.setId(charityId);
    }

    @Test
    @Order(2)
    void readCharityByCharityName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        String url = hostname + port
                + CharityPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&charityName=" + charityName;
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Charity[]> charityList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Charity[].class);
        assertTrue(charityList.getStatusCode().is2xxSuccessful());
        Charity charity1 = charityList.getBody()[0];
        assertTrue(charity1.getCharityName().equals(charityName));
    }

    @Test
    @Order(3)
    void readCharityByCharityId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Charity[]> charityList = this.restTemplate.exchange(
                hostname + port + CharityPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&charityId=" + charityId, HttpMethod.GET, request, Charity[].class);
        Charity charity1 = charityList.getBody()[0];
        assertTrue(charity1.getCharityName().equals(charityName));
    }

    @Test
    @Order(4)
    void updateCharityByCharityId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Charity> request = new HttpEntity<>(updatedCharity, headers);
        String url = hostname + port
                + CharityPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&charityId=" + charityId;
        URI uri = new URI(url);
        ResponseEntity<Charity[]> charityList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Charity[].class);
        assertTrue(charityList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedCharityByCharityId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Charity[]> charityList = this.restTemplate.exchange(
                hostname + port
                        + CharityPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&charityId=" + charityId, HttpMethod.GET, request, Charity[].class);
        assertTrue(charityList.getStatusCode().is2xxSuccessful());
        Charity charity1 = charityList.getBody()[0];
        assertTrue(charity1.getCharityName().equals(updatedCharityName));
        charityId = charity1.getId();
    }

    @Test
    @Order(6)
    void deleteCharityByCharityId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + CharityPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&charityId=" + charityId;
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

        personService.deletePersonBySsn(ssn);
    }
}
