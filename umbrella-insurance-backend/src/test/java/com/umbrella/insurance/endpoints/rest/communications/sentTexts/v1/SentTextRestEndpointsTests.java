package com.umbrella.insurance.endpoints.rest.communications.sentTexts.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.SentTextPrivilege;
import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa.SentTextService;
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
public class SentTextRestEndpointsTests {
    @Autowired
    private SentTextRestEndpoints sentTextRestEndpoints;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(sentTextRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long sentTextId;

    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static String recipientPhoneNumber = "123";
    private static String senderPhoneNumber= "123456";
    private static String textMessage = "message";
    private static Timestamp sentDateTime = Timestamp.valueOf("2023-12-12 12:00:00");

    private static String updatedRecipientPhoneNumber = "123";
    private static String updatedSenderPhoneNumber= "123456";
    private static String updatedTextMessage = "message";
    private static Timestamp updatedSentDateTime = Timestamp.valueOf("2024-11-11 11:00:00");
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static Session session = new Session();
    private static User user = new User();
    private static SentText sentText = new SentText();
    private static SentText updatedSentText = new SentText();
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

        sentText.setRecipientPhoneNumber(recipientPhoneNumber);
        sentText.setSenderPhoneNumber(senderPhoneNumber);
        //sentText.setUser(user);
        sentText.setTextMessage(textMessage);
        sentText.setSentDateTime(sentDateTime);

        //
        updatedSentText.setRecipientPhoneNumber(updatedRecipientPhoneNumber);
        updatedSentText.setSenderPhoneNumber(updatedSenderPhoneNumber);
        //updatedSentText.setUser(user);
        updatedSentText.setTextMessage(updatedTextMessage);
        updatedSentText.setSentDateTime(updatedSentDateTime);
    }

    private static Connection connection;
    @BeforeEach
    public void beforeEach() throws SQLException {
    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private SentTextService sentTextService;

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        //Database.copyOutManagerTest(connection, SentTextsTable.SENT_TEXTS_TABLE_NAME);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        //sentTextService.deleteAllSentTexts(connection);
        //Database.copyInManagerTest(connection, SentTextsTable.SENT_TEXTS_TABLE_NAME);
        Database.closeConnection(connection);
    }

    @Test
    @Order(1)
    void createSentText() throws Exception {
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
        sentText.setUser(user);
        updatedSentText.setUser(user);
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);
        
        headers.set("session", session.getSessionCode());
        HttpEntity<SentText> request = new HttpEntity<>(sentText, headers);
        String url = hostname + port
                + SentTextPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<SentText> sentText1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, SentText.class);
        assertTrue(sentText1.getBody().getSentDateTime().equals(sentDateTime));
        assertTrue(sentText1.getBody().getTextMessage().equals(textMessage));
        assertTrue(sentText1.getBody().getSenderPhoneNumber().equals(senderPhoneNumber));
        //assertTrue(sentText1.getBody().getUserId() == null);
        assertTrue(sentText1.getBody().getRecipientPhoneNumber().equals(recipientPhoneNumber));
        sentTextId = sentText1.getBody().getId();
        updatedSentText.setId(sentTextId);
    }

    @Test
    @Order(2)
    void readSentTextByTextMessage() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        String url = hostname + port
                + SentTextPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&textMessage=" + textMessage;
        HttpEntity<SentText> request = new HttpEntity<>(sentText, headers);
        ResponseEntity<SentText[]> sentTextList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, SentText[].class);
        SentText sentText1 = sentTextList.getBody()[0];
        assertTrue(sentText1.getSentDateTime().equals(sentDateTime));
        assertTrue(sentText1.getTextMessage().equals(textMessage));
        assertTrue(sentText1.getSenderPhoneNumber().equals(senderPhoneNumber));
        //assertTrue(sentText1.getUserId() == null);
        assertTrue(sentText1.getRecipientPhoneNumber().equals(recipientPhoneNumber));
        assertTrue(sentText1.getId().equals(sentTextId));
    }

    @Test
    @Order(3)
    void readSentTextBySentTextId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<SentText[]> sentTextList = this.restTemplate.exchange(
                hostname + port + SentTextPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&sentTextId=" + sentTextId, HttpMethod.GET, request, SentText[].class);
        SentText sentText1 = sentTextList.getBody()[0];
        assertTrue(sentText1.getSentDateTime().equals(sentDateTime));
        assertTrue(sentText1.getTextMessage().equals(textMessage));
        assertTrue(sentText1.getSenderPhoneNumber().equals(senderPhoneNumber));
        //assertTrue(sentText1.getUserId() == null);
        assertTrue(sentText1.getRecipientPhoneNumber().equals(recipientPhoneNumber));
        assertTrue(sentText1.getId().equals(sentTextId));
    }

    @Test
    @Order(4)
    void updateSentTextBySentTextId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<SentText> request = new HttpEntity<>(updatedSentText, headers);

        String url = hostname + port
                + SentTextPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sentTextId=" + sentTextId;
        URI uri = new URI(url);
        ResponseEntity<SentText[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, SentText[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedSentTextBySentTextId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<SentText[]> sentTextList = this.restTemplate.exchange(
                hostname + port
                        + SentTextPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&sentTextId=" + sentTextId, HttpMethod.GET, request, SentText[].class);
        SentText sentText1 = sentTextList.getBody()[0];
        assertTrue(sentText1.getSentDateTime().equals(updatedSentDateTime));
        assertTrue(sentText1.getTextMessage().equals(updatedTextMessage));
        assertTrue(sentText1.getSenderPhoneNumber().equals(updatedSenderPhoneNumber));
        //assertTrue(sentText1.getUserId() == null);
        assertTrue(sentText1.getRecipientPhoneNumber().equals(updatedRecipientPhoneNumber));
        assertTrue(sentText1.getId().equals(sentTextId));
    }

    @Test
    @Order(6)
    void deleteSentTextBySentTextId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SentTextPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sentTextId=" + sentTextId;
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
