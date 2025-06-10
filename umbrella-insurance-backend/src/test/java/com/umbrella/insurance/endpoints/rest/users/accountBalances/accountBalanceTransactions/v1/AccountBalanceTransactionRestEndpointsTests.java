package com.umbrella.insurance.endpoints.rest.users.accountBalances.accountBalanceTransactions.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.AccountBalanceTransactionStatusEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa.AccountBalanceTransactionStatusService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.AccountBalanceTransactionTypeEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa.AccountBalanceTransactionTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.AccountBalanceTransactionPrivilege;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa.AccountBalanceTransactionService;
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
public class AccountBalanceTransactionRestEndpointsTests {
    @Autowired
    private AccountBalanceTransactionRestEndpoints accountBalanceTransactionRestEndpoints;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(accountBalanceTransactionRestEndpoints).isNotNull();
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

    private static Long accountBalanceTransactionId;
    private static String accountBalanceTransactionName = "1234";
    private static Double amount = 3.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("1111-11-11 11:11:11");
    private static String updatedAccountBalanceTransactionName = "12345";
    private static Double updatedAmount = 5.0;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1113-11-11 11:11:11");
    private static Unit unit = new Unit();
    private static AccountBalanceTransactionType accountBalanceTransactionType = new AccountBalanceTransactionType();
    private static AccountBalanceTransactionStatus accountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    private static AccountBalanceTransaction accountBalanceTransaction = new AccountBalanceTransaction();
    private static AccountBalanceTransaction updatedAccountBalanceTransaction = new AccountBalanceTransaction();
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

        unit.setUnitName("dollars");

        accountBalanceTransactionType.setAccountBalanceTransactionTypeName(AccountBalanceTransactionTypeEnum.ESCROW_IN.toString());

        accountBalanceTransactionStatus.setAccountBalanceTransactionStatusName(AccountBalanceTransactionStatusEnum.COMPLETED.toString());

        accountBalanceTransaction.setAccountBalanceTransactionName(accountBalanceTransactionName);
        accountBalanceTransaction.setAmount(amount);
        accountBalanceTransaction.setCreatedDateTime(createdDateTime);

        updatedAccountBalanceTransaction.setAccountBalanceTransactionName(updatedAccountBalanceTransactionName);
        updatedAccountBalanceTransaction.setAmount(updatedAmount);
        updatedAccountBalanceTransaction.setCreatedDateTime(updatedCreatedDateTime);
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
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private AccountBalanceTransactionService accountBalanceTransactionService;

    @Autowired
    private AccountBalanceTransactionStatusService accountBalanceTransactionStatusService;

    @Autowired
    private UserService userService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private AccountBalanceTransactionTypeService accountBalanceTransactionTypeService;

    @Test
    @Order(1)
    void createAccountBalanceTransaction() throws Exception {
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

        //count = AccountBalanceTransactionTypesTable.insertAccountBalanceTransactionType(
        //        accountBalanceTransactionType, connection);
        //assertTrue(count == 1);
        accountBalanceTransactionType = accountBalanceTransactionTypeService.getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                accountBalanceTransactionType.getAccountBalanceTransactionTypeName()).get();
        accountBalanceTransaction.setAccountBalanceTransactionType(accountBalanceTransactionType);
        updatedAccountBalanceTransaction.setAccountBalanceTransactionType(accountBalanceTransactionType);


        //count = AccountBalanceTransactionStatusesTable.insertAccountBalanceTransactionStatus(
        //        accountBalanceTransactionStatus, connection);
        //assertTrue(count == 1);
        accountBalanceTransactionStatus = accountBalanceTransactionStatusService
                .getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                        accountBalanceTransactionStatus.getAccountBalanceTransactionStatusName()).get();
        accountBalanceTransaction.setAccountBalanceTransactionStatus(accountBalanceTransactionStatus);
        updatedAccountBalanceTransaction.setAccountBalanceTransactionStatus(accountBalanceTransactionStatus);

        unit = unitService.saveUnit(unit);
        accountBalanceTransaction.setUnit(unit);
        updatedAccountBalanceTransaction.setUnit(unit);

        headers.set("session", session.getSessionCode());
        HttpEntity<AccountBalanceTransaction> request = new HttpEntity<>(accountBalanceTransaction, headers);

        String url = hostname + port
                + AccountBalanceTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<AccountBalanceTransaction> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, AccountBalanceTransaction.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        AccountBalanceTransaction accountBalanceTransaction1 = response.getBody();
        assertTrue(accountBalanceTransaction1.getAccountBalanceTransactionName().equals(accountBalanceTransactionName));
        accountBalanceTransactionId = accountBalanceTransaction1.getId();
        updatedAccountBalanceTransaction.setId(accountBalanceTransactionId);
    }

    @Test
    @Order(2)
    void readAccountBalanceTransactionByAccountBalanceTransactionName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + AccountBalanceTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&accountBalanceTransactionName=" + accountBalanceTransactionName;
        ResponseEntity<AccountBalanceTransaction[]> accountBalanceTransactionList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, AccountBalanceTransaction[].class);
        assertTrue(accountBalanceTransactionList.getStatusCode().is2xxSuccessful());
        AccountBalanceTransaction accountBalanceTransaction1 = accountBalanceTransactionList.getBody()[0];
        assertTrue(accountBalanceTransaction1.getAccountBalanceTransactionName().equals(accountBalanceTransactionName));
    }

    @Test
    @Order(3)
    void readAccountBalanceTransactionByAccountBalanceTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<AccountBalanceTransaction[]> accountBalanceTransactionList = this.restTemplate.exchange(
                hostname + port + AccountBalanceTransactionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&accountBalanceTransactionId=" + accountBalanceTransactionId, HttpMethod.GET, request, AccountBalanceTransaction[].class);
        assertTrue(accountBalanceTransactionList.getStatusCode().is2xxSuccessful());
        AccountBalanceTransaction accountBalanceTransaction1 = accountBalanceTransactionList.getBody()[0];
        assertTrue(accountBalanceTransaction1.getAccountBalanceTransactionName().equals(accountBalanceTransactionName));
    }

    @Test
    @Order(4)
    void updateAccountBalanceTransactionByAccountBalanceTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(updatedAccountBalanceTransaction, headers);

        String url = hostname + port
                + AccountBalanceTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&accountBalanceTransactionId=" + accountBalanceTransactionId;
        URI uri = new URI(url);
        ResponseEntity<AccountBalanceTransaction[]> accountBalanceTransactionList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, AccountBalanceTransaction[].class);
        assertTrue(accountBalanceTransactionList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedAccountBalanceTransactionByAccountBalanceTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<AccountBalanceTransaction[]> accountBalanceTransactionList = this.restTemplate.exchange(
                hostname + port
                        + AccountBalanceTransactionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&accountBalanceTransactionId=" + accountBalanceTransactionId, HttpMethod.GET, request,
                AccountBalanceTransaction[].class);
        assertTrue(accountBalanceTransactionList.getStatusCode().is2xxSuccessful());
        AccountBalanceTransaction accountBalanceTransaction1 = accountBalanceTransactionList.getBody()[0];
        assertTrue(accountBalanceTransaction1.getAccountBalanceTransactionName().equals(updatedAccountBalanceTransactionName));
        accountBalanceTransactionId = accountBalanceTransaction1.getId();
    }

    @Test
    @Order(6)
    void deleteAccountBalanceTransactionByAccountBalanceTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + AccountBalanceTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&accountBalanceTransactionId=" + accountBalanceTransactionId;
        URI uri = new URI(url);
        ResponseEntity<Object> accountBalanceTransactionList = this.restTemplate.exchange(uri, HttpMethod.DELETE,
                request, Object.class);

        assertTrue(accountBalanceTransactionList.getStatusCode().is2xxSuccessful());

        //int count = AccountBalanceTransactionTypesTable.deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
        //        accountBalanceTransactionType.getAccountBalanceTransactionTypeName(), connection);
        //assertTrue(count == 1);

        //count = AccountBalanceTransactionStatusesTable.deleteAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
        //        accountBalanceTransactionStatus.getAccountBalanceTransactionStatusName(), connection);
        //assertTrue(count == 1);

        unitService.deleteUnitByUnitName(unit.getUnitName());

        sessionService.deleteSession(session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();
        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn2);

    }
}
