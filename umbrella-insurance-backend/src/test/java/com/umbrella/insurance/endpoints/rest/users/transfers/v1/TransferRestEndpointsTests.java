package com.umbrella.insurance.endpoints.rest.users.transfers.v1;

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
import com.umbrella.insurance.core.models.users.transfers.v1.db.TransferPrivilege;
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
public class TransferRestEndpointsTests {
    @Autowired
    private TransferRestEndpoints transferRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(transferRestEndpoints).isNotNull();
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

    private static Long transferId;
    private static Double amount = 20.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("2023-10-12 11:11:11");
    private static Boolean isVoided = true;
    private static Timestamp voidedDateTime = Timestamp.valueOf("2024-11-12 10:09:11");
    private static Timestamp postedDateTime = Timestamp.valueOf("2000-04-05 07:08:09");
    private static String transferName = "1234";
    private static Double updatedAmount = 40.0;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2001-04-05 07:08:09");
    private static Boolean updatedIsVoided = false;
    private static Timestamp updatedVoidedDateTime = Timestamp.valueOf("2002-04-05 07:08:09");
    private static Timestamp updatedPostedDateTime = Timestamp.valueOf("2100-04-05 07:08:09");
    private static String updatedTransferName = "12345";
    private static Transfer transfer = new Transfer();
    private static Transfer updatedTransfer = new Transfer();
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

        updatedTransfer.setAmount(updatedAmount);
        updatedTransfer.setCreatedDateTime(updatedCreatedDateTime);
        updatedTransfer.setIsVoided(updatedIsVoided);
        updatedTransfer.setVoidedDateTime(updatedVoidedDateTime);
        updatedTransfer.setPostedDateTime(updatedPostedDateTime);
        updatedTransfer.setTransferName(updatedTransferName);
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
    private UserService userService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private CardOnFileService cardOnFileService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createTransfer() throws Exception {
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

        headers.set("session", session.getSessionCode());
        HttpEntity<Transfer> request = new HttpEntity<>(transfer, headers);

        String url = hostname + port
                + TransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Transfer> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Transfer.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Transfer transfer1 = response.getBody();
        assertTrue(transfer1.getTransferName().equals(transferName));
        transferId = transfer1.getId();
        updatedTransfer.setId(transferId);
    }

    @Test
    @Order(2)
    void readTransferByTransferName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&transferName=" + transferName;
        ResponseEntity<Transfer[]> transferList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Transfer[].class);
        assertTrue(transferList.getStatusCode().is2xxSuccessful());
        Transfer transfer1 = transferList.getBody()[0];
        assertTrue(transfer1.getTransferName().equals(transferName));
    }

    @Test
    @Order(3)
    void readTransferByTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Transfer[]> transferList = this.restTemplate.exchange(
                hostname + port + TransferPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&transferId=" + transferId, HttpMethod.GET, request, Transfer[].class);
        assertTrue(transferList.getStatusCode().is2xxSuccessful());
        Transfer transfer1 = transferList.getBody()[0];
        assertTrue(transfer1.getTransferName().equals(transferName));
    }

    @Test
    @Order(4)
    void updateTransferByTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Transfer> request = new HttpEntity<>(updatedTransfer, headers);

        String url = hostname + port
                + TransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&transferId=" + transferId;
        URI uri = new URI(url);
        ResponseEntity<Transfer[]> transferList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, Transfer[].class);
        assertTrue(transferList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedTransferByTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Transfer[]> transferList = this.restTemplate.exchange(
                hostname + port
                        + TransferPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&transferId=" + transferId, HttpMethod.GET, request, Transfer[].class);
        assertTrue(transferList.getStatusCode().is2xxSuccessful());
        Transfer transfer1 = transferList.getBody()[0];
        assertTrue(transfer1.getTransferName().equals(updatedTransferName));
        transferId = transfer1.getId();
    }

    @Test
    @Order(6)
    void deleteTransferByTransferId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TransferPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&transferId=" + transferId;
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
