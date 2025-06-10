package com.umbrella.insurance.endpoints.rest.people.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.PersonPrivilege;
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
public class PersonRestEndpointsTests {
    @Autowired
    private PersonRestEndpoints personRestEndpoints;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Test
    void contextLoads() throws Exception {
        assertThat(personRestEndpoints).isNotNull();
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

    private static Long personId;

    private static String emailAddress2 = "1";
    private static String phoneNumber2 = "2";
    private static String username2 = "3";
    private static String password2 = "5";
    private static String ssn2 = "1234";
    private static String surname2 = "1224";
    private static String middle2 = "middle4";
    private static String first2 = "first4";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-12");

    private static String firstName = "t";
    private static String middleName = "a";
    private static Date date = Date.valueOf("2023-12-12");

    private static String updatedSsn = "12345";
    private static String updatedFirstName = "ta";
    private static String updatedMiddleName = "aa";
    private static String updatedSurname = "na";
    private static Date updatedDate = Date.valueOf("2025-11-11");
    private static Person person = new Person();
    private static Person person2 = new Person();
    private static Person updatedPerson = new Person();
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

        person.setSsn(ssn);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setSurname(surname);
        person.setDateOfBirth(date);

        person2.setSsn(ssn2);
        person2.setFirstName(first2);
        person2.setMiddleName(middle2);
        person2.setSurname(surname2);
        person2.setDateOfBirth(dateOfBirth2);

        updatedPerson.setSsn(updatedSsn);
        updatedPerson.setFirstName(updatedFirstName);
        updatedPerson.setMiddleName(updatedMiddleName);
        updatedPerson.setSurname(updatedSurname);
        updatedPerson.setDateOfBirth(updatedDate);
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

    @Test
    @Order(1)
    void createPerson() throws Exception {
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
        HttpEntity<Person> request = new HttpEntity<>(person2, headers);
        String url = hostname + port
                + PersonPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Person> person1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Person.class);
        assertTrue(person1.getStatusCode().is2xxSuccessful());
        assertTrue(person1.getBody().getSurname().equals(surname2));
        assertTrue(person1.getBody().getMiddleName().equals(middle2));
        assertTrue(person1.getBody().getFirstName().equals(first2));
        assertTrue(person1.getBody().getDateOfBirth().equals(dateOfBirth2));
        assertTrue(person1.getBody().getSsn().equals(ssn2));
        personId = person1.getBody().getId();
        updatedPerson.setId(personId);
    }

    @Test
    @Order(2)
    void readPersonBySsn() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PersonPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&ssn=" + ssn2;
        ResponseEntity<Person[]> personList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Person[].class);
        assertTrue(personList.getStatusCode().is2xxSuccessful());
        Person person1 = personList.getBody()[0];
        assertTrue(person1.getSurname().equals(surname2));
        assertTrue(person1.getMiddleName().equals(middle2));
        assertTrue(person1.getFirstName().equals(first2));
        assertTrue(person1.getDateOfBirth().equals(dateOfBirth2));
        assertTrue(person1.getSsn().equals(ssn2));
    }

    @Test
    @Order(3)
    void readPersonByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Person[]> personList = this.restTemplate.exchange(
                hostname + port + PersonPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&personId=" + personId, HttpMethod.GET, request, Person[].class);
        assertTrue(personList.getStatusCode().is2xxSuccessful());
        Person person1 = personList.getBody()[0];
        assertTrue(person1.getSurname().equals(surname2));
        assertTrue(person1.getMiddleName().equals(middle2));
        assertTrue(person1.getFirstName().equals(first2));
        assertTrue(person1.getDateOfBirth().equals(dateOfBirth2));
        assertTrue(person1.getSsn().equals(ssn2));
    }

    @Test
    @Order(4)
    void updatePersonByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Person> request = new HttpEntity<>(updatedPerson, headers);

        String url = hostname + port
                + PersonPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&personId=" + personId;
        URI uri = new URI(url);
        ResponseEntity<Person[]> personList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Person[].class);
        assertTrue(personList.getStatusCode().is2xxSuccessful());

    }

    @Test
    @Order(5)
    void readUpdatedPersonByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Person[]> personList = this.restTemplate.exchange(
                hostname + port
                        + PersonPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&personId=" + personId, HttpMethod.GET, request, Person[].class);
        assertTrue(personList.getStatusCode().is2xxSuccessful());
        Person person1 = personList.getBody()[0];
        assertTrue(person1.getSurname().equals(updatedSurname));
        assertTrue(person1.getMiddleName().equals(updatedMiddleName));
        assertTrue(person1.getFirstName().equals(updatedFirstName));
        assertTrue(person1.getDateOfBirth().equals(updatedDate));
        assertTrue(person1.getSsn().equals(updatedSsn));
        personId = person1.getId();
    }

    @Test
    @Order(6)
    void deletePersonByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PersonPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&personId=" + personId;
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

        personService.deletePersonBySsn(ssn);
    }
}
