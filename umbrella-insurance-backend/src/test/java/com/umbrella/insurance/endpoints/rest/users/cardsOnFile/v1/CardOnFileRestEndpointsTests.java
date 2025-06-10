package com.umbrella.insurance.endpoints.rest.users.cardsOnFile.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.CardOnFilePrivilege;
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
public class CardOnFileRestEndpointsTests {
    @Autowired
    private CardOnFileRestEndpoints cardOnFileRestEndpoints;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(cardOnFileRestEndpoints).isNotNull();
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

    private static Long cardOnFileId;
    private static String cardNumber = "1";
    private static Date expirationDate = Date.valueOf("2022-10-09");
    private static String cvv = "123";
    private static Long userId = null;
    private static String phoneNumber = "11111111";
    private static Long locationId = null;//billingAddress;
    private static Timestamp createdDateTime = Timestamp.valueOf("1992-07-24 07:11:11");
    private static Timestamp deletedDateTime = Timestamp.valueOf("2992-08-24 01:13:11");
    private static Boolean isDeleted = true;
    private static String updatedCardNumber = "5433";
    private static Date updatedExpirationDate = Date.valueOf("1111-10-11");
    private static String updatedCvv = "543";
    private static Long updatedUserId = null;
    private static String updatedPhoneNumber = "9999";
    private static Long updatedLocationId = null;//billingAddress;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1000-12-12 13:13:13");
    private static Timestamp updatedDeletedDateTime = Timestamp.valueOf("1999-1-2 14:15:16");
    private static Boolean updatedIsDeleted = false;
    private static CardOnFile cardOnFile = new CardOnFile();
    private static CardOnFile updatedCardOnFile = new CardOnFile();
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

        cardOnFile.setCardNumber(cardNumber);
        cardOnFile.setExpirationDate(expirationDate);
        cardOnFile.setCvv(cvv);
        //cardOnFile.setUser(user);
        cardOnFile.setPhoneNumber(phoneNumber);
        //cardOnFile.setLocation(location);
        cardOnFile.setCreatedDateTime(createdDateTime);
        cardOnFile.setDeletedDateTime(deletedDateTime);
        cardOnFile.setIsDeleted(isDeleted);

        updatedCardOnFile.setCardNumber(updatedCardNumber);
        updatedCardOnFile.setExpirationDate(updatedExpirationDate);
        updatedCardOnFile.setCvv(updatedCvv);
        //updatedCardOnFile.setUserId(updatedUserId);
        updatedCardOnFile.setPhoneNumber(updatedPhoneNumber);
        //updatedCardOnFile.setLocationId(updatedLocationId);
        updatedCardOnFile.setCreatedDateTime(updatedCreatedDateTime);
        updatedCardOnFile.setDeletedDateTime(updatedDeletedDateTime);
        updatedCardOnFile.setIsDeleted(updatedIsDeleted);
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
    void createCardOnFile() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<CreateUserRequest> cuRequest = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, cuRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getBody().getSessionCode());
        userId = createUserResponse.getBody().getUser().getId();
        User user2 = userService.getUserByUserId(userId).get();
        cardOnFile.setUser(user2);
        updatedCardOnFile.setUser(user2);
        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        headers.set("session", session.getSessionCode());
        HttpEntity<CardOnFile> request = new HttpEntity<>(cardOnFile, headers);

        String url = hostname + port
                + CardOnFilePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<CardOnFile> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, CardOnFile.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        CardOnFile cardOnFile1 = response.getBody();
        assertTrue(cardOnFile1.getCardNumber().equals(cardNumber));
        assertTrue(cardOnFile1.getExpirationDate().equals(expirationDate));
        assertTrue(cardOnFile1.getCvv().equals(cvv));
        assertTrue(cardOnFile1.getUser().getId().equals(userId));
        assertTrue(cardOnFile1.getPhoneNumber().equals(phoneNumber));
        //assertTrue(cardOnFile1.getLocation().getId().equals(locationId));
        assertTrue(cardOnFile1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(cardOnFile1.getDeletedDateTime().equals(deletedDateTime));
        assertTrue(cardOnFile1.getIsDeleted().equals(isDeleted));
        cardOnFileId = cardOnFile1.getId();
        updatedCardOnFile.setId(cardOnFileId);
    }

    @Test
    @Order(2)
    void readCardOnFileByCardOnFileName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + CardOnFilePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&cardNumber=" + cardNumber;
        ResponseEntity<CardOnFile[]> cardOnFileList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, CardOnFile[].class);
        assertTrue(cardOnFileList.getStatusCode().is2xxSuccessful());
        CardOnFile cardOnFile1 = cardOnFileList.getBody()[0];
        assertTrue(cardOnFile1.getCardNumber().equals(cardNumber));
        assertTrue(cardOnFile1.getExpirationDate().equals(expirationDate));
        assertTrue(cardOnFile1.getCvv().equals(cvv));
        assertTrue(cardOnFile1.getUser().getId().equals(userId));
        assertTrue(cardOnFile1.getPhoneNumber().equals(phoneNumber));
        //assertTrue(cardOnFile1.getLocation().getId().equals(locationId));
        assertTrue(cardOnFile1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(cardOnFile1.getDeletedDateTime().equals(deletedDateTime));
        assertTrue(cardOnFile1.getIsDeleted().equals(isDeleted));
    }

    @Test
    @Order(3)
    void readCardOnFileByCardOnFileId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<CardOnFile[]> cardOnFileList = this.restTemplate.exchange(
                hostname + port + CardOnFilePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&cardOnFileId=" + cardOnFileId, HttpMethod.GET, request, CardOnFile[].class);
        assertTrue(cardOnFileList.getStatusCode().is2xxSuccessful());
        CardOnFile cardOnFile1 = cardOnFileList.getBody()[0];
        assertTrue(cardOnFile1.getCardNumber().equals(cardNumber));
        assertTrue(cardOnFile1.getExpirationDate().equals(expirationDate));
        assertTrue(cardOnFile1.getCvv().equals(cvv));
        assertTrue(cardOnFile1.getUser().getId().equals(userId));
        assertTrue(cardOnFile1.getPhoneNumber().equals(phoneNumber));
        //assertTrue(cardOnFile1.getLocation().getId().equals(locationId));
        assertTrue(cardOnFile1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(cardOnFile1.getDeletedDateTime().equals(deletedDateTime));
        assertTrue(cardOnFile1.getIsDeleted().equals(isDeleted));
    }

    @Test
    @Order(4)
    void updateCardOnFileByCardOnFileId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<CardOnFile> request = new HttpEntity<>(updatedCardOnFile, headers);

        String url = hostname + port
                + CardOnFilePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&cardOnFileId=" + cardOnFileId;
        URI uri = new URI(url);
        ResponseEntity<CardOnFile[]> cardOnFileList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, CardOnFile[].class);
        assertTrue(cardOnFileList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedCardOnFileByCardOnFileId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<CardOnFile[]> cardOnFileList = this.restTemplate.exchange(
                hostname + port
                        + CardOnFilePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&cardOnFileId=" + cardOnFileId, HttpMethod.GET, request, CardOnFile[].class);
        assertTrue(cardOnFileList.getStatusCode().is2xxSuccessful());
        CardOnFile cardOnFile1 = cardOnFileList.getBody()[0];
        assertTrue(cardOnFile1.getCardNumber().equals(updatedCardNumber));
        assertTrue(cardOnFile1.getExpirationDate().equals(updatedExpirationDate));
        assertTrue(cardOnFile1.getCvv().equals(updatedCvv));
        assertTrue(cardOnFile1.getUser().getId().equals(userId));
        assertTrue(cardOnFile1.getPhoneNumber().equals(updatedPhoneNumber));
        //assertTrue(cardOnFile1.getLocation().getId().equals(updatedLocationId));
        assertTrue(cardOnFile1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(cardOnFile1.getDeletedDateTime().equals(updatedDeletedDateTime));
        assertTrue(cardOnFile1.getIsDeleted().equals(updatedIsDeleted));
        cardOnFileId = cardOnFile1.getId();
    }

    @Test
    @Order(6)
    void deleteCardOnFileByCardOnFileId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + CardOnFilePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&cardOnFileId=" + cardOnFileId;
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
