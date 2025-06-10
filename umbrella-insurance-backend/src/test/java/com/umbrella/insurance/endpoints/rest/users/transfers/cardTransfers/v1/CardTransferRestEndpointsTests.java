package com.umbrella.insurance.endpoints.rest.users.transfers.cardTransfers.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa.CardOnFileService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.CardTransferPrivilege;
import com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.jpa.CardTransferService;
import com.umbrella.insurance.core.models.users.transfers.v1.db.jpa.TransferService;
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
import java.math.BigInteger;
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
public class CardTransferRestEndpointsTests {
    @Autowired
    private CardTransferRestEndpoints cardTransferRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(cardTransferRestEndpoints).isNotNull();
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

    private static Long cardTransferId;
    private static Double amount = 20.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("2023-10-12 11:11:11");
    private static Boolean isVoided = true;
    private static Timestamp voidedDateTime = Timestamp.valueOf("2024-11-12 10:09:11");
    private static Timestamp postedDateTime = Timestamp.valueOf("2000-04-05 07:08:09");
    private static String transferName = "1234";
    private static String cardNumber = "1";
    private static Date expirationDate = Date.valueOf("2022-10-09");
    private static String cvv = "123";
    private static BigInteger userId = BigInteger.valueOf(2);
    private static String phoneNumber = "11111111";
    private static BigInteger locationId = BigInteger.valueOf(3);//billingAddress;
    private static Timestamp deletedDateTime = Timestamp.valueOf("2992-08-24 01:13:11");
    private static Boolean isDeleted = true;
    private static CardOnFile cardOnFile = new CardOnFile();
    private static Transfer transfer = new Transfer();
    private static CardTransfer cardTransfer = new CardTransfer();
    private static CardTransfer updatedCardTransfer = new CardTransfer();
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

        transfer.setAmount(amount);
        transfer.setCreatedDateTime(createdDateTime);
        transfer.setIsVoided(isVoided);
        transfer.setVoidedDateTime(voidedDateTime);
        transfer.setPostedDateTime(postedDateTime);
        transfer.setTransferName(transferName);

        cardOnFile.setCardNumber(cardNumber);
        cardOnFile.setExpirationDate(expirationDate);
        cardOnFile.setCvv(cvv);
        //cardOnFile.setUser(user);
        cardOnFile.setPhoneNumber(phoneNumber);
        //cardOnFile.setLocation(location);
        cardOnFile.setCreatedDateTime(createdDateTime);
        cardOnFile.setDeletedDateTime(deletedDateTime);
        cardOnFile.setIsDeleted(isDeleted);
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
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CardTransferService cardTransferService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private CardOnFileService cardOnFileService;

    @Autowired
    private PersonService personService;

    @Test
    @Order(1)
    void createCardTransfer() throws Exception {
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

        transfer = transferService.saveTransfer(transfer);
        cardTransfer.setTransfer(transfer);
        updatedCardTransfer.setTransfer(transfer);


        cardOnFile = cardOnFileService.saveCardOnFile(cardOnFile);
        cardTransfer.setCardOnFile(cardOnFile);
        updatedCardTransfer.setCardOnFile(cardOnFile);

        headers.set("session", session.getSessionCode());
        HttpEntity<CardTransfer> request = new HttpEntity<>(cardTransfer, headers);

        String url = hostname + port
                + CardTransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<CardTransfer> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, CardTransfer.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        CardTransfer cardTransfer1 = response.getBody();
        assertTrue(cardTransfer1.getCardOnFile().getId().equals(cardTransfer.getCardOnFile().getId()));
        assertTrue(cardTransfer1.getTransfer().getId().equals(transfer.getId()));
        cardTransferId = cardTransfer1.getId();
        updatedCardTransfer.setId(cardTransferId);
    }

    @Test
    @Order(2)
    void readCardTransferByCardTransferName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + CardTransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&transferId=" + transfer.getId();
        ResponseEntity<CardTransfer[]> cardTransferList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, CardTransfer[].class);
        assertTrue(cardTransferList.getStatusCode().is2xxSuccessful());
        CardTransfer cardTransfer1 = cardTransferList.getBody()[0];
        assertTrue(cardTransfer1.getCardOnFile().getId().equals(cardTransfer.getCardOnFile().getId()));
        assertTrue(cardTransfer1.getTransfer().getId().equals(transfer.getId()));
    }

    @Test
    @Order(3)
    void readCardTransferByCardTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<CardTransfer[]> cardTransferList = this.restTemplate.exchange(
                hostname + port + CardTransferPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&cardTransferId=" + cardTransferId, HttpMethod.GET, request, CardTransfer[].class);
        assertTrue(cardTransferList.getStatusCode().is2xxSuccessful());
        CardTransfer cardTransfer1 = cardTransferList.getBody()[0];
        assertTrue(cardTransfer1.getCardOnFile().getId().equals(cardTransfer.getCardOnFile().getId()));
        assertTrue(cardTransfer1.getTransfer().getId().equals(transfer.getId()));
    }

    @Test
    @Order(4)
    void updateCardTransferByCardTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<CardTransfer> request = new HttpEntity<>(updatedCardTransfer, headers);

        String url = hostname + port
                + CardTransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&cardTransferId=" + cardTransferId;
        URI uri = new URI(url);
        ResponseEntity<CardTransfer[]> response = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, CardTransfer[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedCardTransferByCardTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<CardTransfer[]> cardTransferList = this.restTemplate.exchange(
                hostname + port
                        + CardTransferPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&cardTransferId=" + cardTransferId, HttpMethod.GET, request, CardTransfer[].class);
        assertTrue(cardTransferList.getStatusCode().is2xxSuccessful());
        CardTransfer cardTransfer1 = cardTransferList.getBody()[0];
        assertTrue(cardTransfer1.getCardOnFile().getId().equals(cardOnFile.getId()));
        assertTrue(cardTransfer1.getTransfer().getId().equals(transfer.getId()));
        cardTransferId = cardTransfer1.getId();
    }

    @Test
    @Order(6)
    void deleteCardTransferByCardTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + CardTransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&cardTransferId=" + cardTransferId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri,
                HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        transferService.deleteCardTransferByTransferName(
                transfer.getTransferName());


        cardOnFileService.deleteCardOnFileByCardNumber(
                cardOnFile.getCardNumber());


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
