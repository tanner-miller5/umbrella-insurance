package com.umbrella.insurance.endpoints.rest.faqs.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.faqs.v1.db.FaqPrivilege;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
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

import java.io.*;
import java.net.URI;
import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FaqRestEndpointsTests {

    @Autowired
    private FaqRestEndpoints faqRestEndpoints;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(faqRestEndpoints).isNotNull();
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

    private static Long faqId;
    private static String faqName = "1234";
    private static String queston = "123";
    private static String answer = "123";
    private static Timestamp faqCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String updatedFaqName = "12345";
    private static String updatedQuestion = "abc";
    private static String updatedAnswer = "abc";
    private static Timestamp updatedFaqCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");

    private static Faq faq = new Faq();
    private static Faq updatedFaq = new Faq();

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

        faq.setQuestion(queston);
        faq.setAnswer(answer);
        faq.setFaqName(faqName);
        faq.setCreatedDateTime(faqCreateDateTime);
        updatedFaq.setQuestion(updatedQuestion);
        updatedFaq.setAnswer(updatedAnswer);
        updatedFaq.setFaqName(updatedFaqName);
        updatedFaq.setCreatedDateTime(updatedFaqCreateDateTime);
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
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createFaq() throws Exception {
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
        faq.setSession(session);
        updatedFaq.setSession(session);
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<Faq> request = new HttpEntity<>(faq, headers);

        String url = hostname + port
                + FaqPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Faq> faq1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Faq.class);

        assertTrue(faq1.getStatusCode().is2xxSuccessful());
        assertTrue(faq1.getBody().getFaqName().equals(faqName));
        assertTrue(faq1.getBody().getQuestion().equals(queston));
        assertTrue(faq1.getBody().getAnswer().equals(answer));
        assertTrue(faq1.getBody().getCreatedDateTime().equals(faqCreateDateTime));
        assertTrue(faq1.getBody().getSession().getId().equals(session.getId()));
        faqId = faq1.getBody().getId();
        updatedFaq.setId(faqId);
    }

    @Test
    @Order(2)
    void readFaqByFaqName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + FaqPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&faqName=" + faqName;
        ResponseEntity<Faq[]> faqList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Faq[].class);
        assertTrue(faqList.getStatusCode().is2xxSuccessful());
        Faq faq1 = faqList.getBody()[0];
        assertTrue(faq1.getFaqName().equals(faqName));
        assertTrue(faq1.getQuestion().equals(queston));
        assertTrue(faq1.getAnswer().equals(answer));
        assertTrue(faq1.getCreatedDateTime().equals(faqCreateDateTime));
        assertTrue(faq1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(3)
    void readFaqByFaqId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port + FaqPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&faqId=" + faqId;

        ResponseEntity<Faq[]> faqList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Faq[].class);
        assertTrue(faqList.getStatusCode().is2xxSuccessful());
        Faq faq1 = faqList.getBody()[0];
        assertTrue(faq1.getFaqName().equals(faqName));
        assertTrue(faq1.getQuestion().equals(queston));
        assertTrue(faq1.getAnswer().equals(answer));
        assertTrue(faq1.getCreatedDateTime().equals(faqCreateDateTime));
        assertTrue(faq1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(4)
    void updateFaqByFaqId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Faq> request = new HttpEntity<>(updatedFaq, headers);
        String url = hostname + port
                + FaqPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&faqId=" + faqId;
        URI uri = new URI(url);
        ResponseEntity<Faq[]> faqList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Faq[].class);
        assertTrue(faqList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedFaqByFaqId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Faq[]> faqList = this.restTemplate.exchange(
                hostname + port
                        + FaqPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&faqId=" + faqId, HttpMethod.GET, request, Faq[].class);
        assertTrue(faqList.getStatusCode().is2xxSuccessful());
        Faq faq1 = faqList.getBody()[0];
        assertTrue(faq1.getFaqName().equals(updatedFaqName));
        assertTrue(faq1.getQuestion().equals(updatedQuestion));
        assertTrue(faq1.getAnswer().equals(updatedAnswer));
        assertTrue(faq1.getCreatedDateTime().equals(updatedFaqCreateDateTime));
        assertTrue(faq1.getSession().getId().equals(session.getId()));
        faqId = faq1.getId();
    }

    @Test
    @Order(6)
    void deleteFaqByFaqId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + FaqPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&faqId=" + faqId;
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