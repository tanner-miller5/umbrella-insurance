package com.umbrella.insurance.endpoints.rest.communications.sentEmails.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.SentEmailPrivilege;
import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa.SentEmailService;
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
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SentEmailRestEndpointsTests {
    @Autowired
    private SentEmailRestEndpoints sentEmailRestEndpoints;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(sentEmailRestEndpoints).isNotNull();
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

    private static String recipientEmailAddress = "1";
    private static String senderEmailAddress = "2";
    private static String contentType = "3";
    private static String emailSubject = "4";
    private static String emailBody = "5";
    private static Timestamp sentDateTime = Timestamp.valueOf("2023-12-12 12:00:00");

    private static String updatedRecipientEmailAddress = "22";
    private static String updatedSenderEmailAddress = "33";
    private static String updatedContentType = "44";
    private static String updatedEmailSubject = "55";
    private static String updatedEmailBody = "66";
    private static Timestamp updatedSentDateTime = Timestamp.valueOf("2024-11-11 11:00:00");

    private static Session session = new Session();
    private static Long sentEmailId;
    private static SentEmail sentEmail = new SentEmail();
    private static SentEmail updatedSentEmail = new SentEmail();
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

        sentEmail.setRecipientEmailAddress(recipientEmailAddress);
        sentEmail.setSenderEmailAddress(senderEmailAddress);
        sentEmail.setContentType(contentType);
        sentEmail.setEmailSubject(emailSubject);
        sentEmail.setEmailBody(emailBody);
        sentEmail.setSentDateTime(sentDateTime);

        //
        updatedSentEmail.setRecipientEmailAddress(updatedRecipientEmailAddress);
        updatedSentEmail.setSenderEmailAddress(updatedSenderEmailAddress);
        updatedSentEmail.setContentType(updatedContentType);
        updatedSentEmail.setEmailSubject(updatedEmailSubject);
        updatedSentEmail.setEmailBody(updatedEmailBody);
        updatedSentEmail.setSentDateTime(updatedSentDateTime);
    }

    private static Connection connection;
    @BeforeEach
    public void beforeEach() throws SQLException {
    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @Autowired
    private SentEmailService sentEmailService;

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        //Database.copyOutManagerTest(connection, SentEmailsTable.SENT_EMAILS_TABLE_NAME);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        //sentEmailService.deleteAllSentEmails(connection);
        //Database.copyInManagerTest(connection, SentEmailsTable.SENT_EMAILS_TABLE_NAME);
        Database.closeConnection(connection);
    }

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createSentEmail() throws Exception {
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
        sentEmail.setUser(user);
        updatedSentEmail.setUser(user);
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);
        headers.set("session", session.getSessionCode());
        String url = hostname + port
                + SentEmailPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<SentEmail> request = new HttpEntity<>(sentEmail, headers);
        ResponseEntity<SentEmail> sentEmail1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, SentEmail.class);
        assertTrue(sentEmail1.getBody().getSentDateTime().equals(sentDateTime));
        assertTrue(sentEmail1.getBody().getEmailBody().equals(emailBody));
        assertTrue(sentEmail1.getBody().getSenderEmailAddress().equals(senderEmailAddress));
        assertTrue(sentEmail1.getBody().getRecipientEmailAddress().equals(recipientEmailAddress));
        assertTrue(sentEmail1.getBody().getContentType().equals(contentType));
        sentEmail1.getBody().getEmailSubject().equals(emailSubject);
        sentEmailId = sentEmail1.getBody().getId();
        updatedSentEmail.setId(sentEmailId);
    }

    @Test
    @Order(2)
    void readSentEmailByEmailSubject() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        String url = hostname + port
                + SentEmailPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&emailSubject=" + emailSubject;
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<SentEmail[]> sentEmailList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, SentEmail[].class);
        assertTrue(sentEmailList.getStatusCode().is2xxSuccessful());
        SentEmail sentEmail1 = sentEmailList.getBody()[0];
        assertTrue(sentEmail1.getSentDateTime().equals(sentDateTime));
        assertTrue(sentEmail1.getEmailBody().equals(emailBody));
        assertTrue(sentEmail1.getSenderEmailAddress().equals(senderEmailAddress));
        assertTrue(sentEmail1.getRecipientEmailAddress().equals(recipientEmailAddress));
        assertTrue(sentEmail1.getContentType().equals(contentType));
        sentEmail1.getEmailSubject().equals(emailSubject);
    }

    @Test
    @Order(3)
    void readSentEmailBySentEmailId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<SentEmail[]> sentEmailList = this.restTemplate.exchange(
                hostname + port + SentEmailPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&sentEmailId=" + sentEmailId, HttpMethod.GET, request, SentEmail[].class);

        SentEmail sentEmail1 = sentEmailList.getBody()[0];
        assertTrue(sentEmail1.getSentDateTime().equals(sentDateTime));
        assertTrue(sentEmail1.getEmailBody().equals(emailBody));
        assertTrue(sentEmail1.getSenderEmailAddress().equals(senderEmailAddress));
        assertTrue(sentEmail1.getRecipientEmailAddress().equals(recipientEmailAddress));
        assertTrue(sentEmail1.getContentType().equals(contentType));
        sentEmail1.getEmailSubject().equals(emailSubject);
    }

    @Test
    @Order(4)
    void updateSentEmailBySentEmailId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<SentEmail> request = new HttpEntity<>(updatedSentEmail, headers);
        String url = hostname + port
                + SentEmailPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sentEmailId=" + sentEmailId;
        URI uri = new URI(url);
        ResponseEntity<SentEmail[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, SentEmail[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedSentEmailBySentEmailId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<SentEmail[]> sentEmailList = this.restTemplate.exchange(
                hostname + port
                        + SentEmailPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&sentEmailId=" + sentEmailId, HttpMethod.GET, request, SentEmail[].class);
        assertTrue(sentEmailList.getStatusCode().is2xxSuccessful());
        SentEmail sentEmail1 = sentEmailList.getBody()[0];
        assertTrue(sentEmail1.getSentDateTime().equals(updatedSentDateTime));
        assertTrue(sentEmail1.getEmailBody().equals(updatedEmailBody));
        assertTrue(sentEmail1.getSenderEmailAddress().equals(updatedSenderEmailAddress));
        assertTrue(sentEmail1.getRecipientEmailAddress().equals(updatedRecipientEmailAddress));
        assertTrue(sentEmail1.getContentType().equals(updatedContentType));
        sentEmail1.getEmailSubject().equals(updatedEmailSubject);
        sentEmailId = sentEmail1.getId();
    }

    @Test
    @Order(6)
    void deleteSentEmailBySentEmailId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SentEmailPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sentEmailId=" + sentEmailId;
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
