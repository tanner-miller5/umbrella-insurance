package com.umbrella.insurance.endpoints.rest.users.bankAccounts.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.bankAccounts.v1.db.BankAccountPrivilege;
import com.umbrella.insurance.core.models.users.bankAccounts.v1.db.jpa.BankAccountService;
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
public class BankAccountRestEndpointsTests {
    @Autowired
    private BankAccountRestEndpoints bankAccountRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(bankAccountRestEndpoints).isNotNull();
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

    private static Long bankAccountId;
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-12-12 12:00:00");
    private static String accountNumber = "1";
    private static String routingNumber = "2";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2020-12-12 11:00:00");
    private static String updatedAccountNumber = "3";
    private static String updatedRoutingNumber = "4";
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static BankAccount bankAccount = new BankAccount();
    private static BankAccount updatedBankAccount = new BankAccount();
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

        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(false);

        bankAccount.setCreatedDateTime(createdDateTime);
        bankAccount.setRoutingNumber(routingNumber);
        bankAccount.setAccountNumber(accountNumber);

        updatedBankAccount.setCreatedDateTime(updatedCreatedDateTime);
        updatedBankAccount.setRoutingNumber(updatedRoutingNumber);
        updatedBankAccount.setAccountNumber(updatedAccountNumber);
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
    private BankAccountService bankAccountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PasswordService passwordService;

    @Test
    @Order(1)
    void createBankAccount() throws Exception {
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

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        bankAccount.setUser(user);
        updatedBankAccount.setUser(user);

        headers.set("session", session.getSessionCode());
        HttpEntity<BankAccount> request = new HttpEntity<>(bankAccount, headers);

        String url = hostname + port
                + BankAccountPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<BankAccount> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, BankAccount.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        BankAccount bankAccount1 = response.getBody();
        assertTrue(bankAccount1.getAccountNumber().equals(accountNumber));
        assertTrue(bankAccount1.getRoutingNumber().equals(routingNumber));
        assertTrue(bankAccount1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(bankAccount1.getUser().getId().equals(bankAccount.getUser().getId()));
        bankAccountId = bankAccount1.getId();
        updatedBankAccount.setId(bankAccountId);
    }

    @Test
    @Order(2)
    void readBankAccountByBankAccountName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + BankAccountPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + user.getId();
        ResponseEntity<BankAccount[]> bankAccountList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, BankAccount[].class);
        assertTrue(bankAccountList.getStatusCode().is2xxSuccessful());
        BankAccount bankAccount1 = bankAccountList.getBody()[0];
        assertTrue(bankAccount1.getAccountNumber().equals(accountNumber));
        assertTrue(bankAccount1.getRoutingNumber().equals(routingNumber));
        assertTrue(bankAccount1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(bankAccount1.getUser().getId().equals(bankAccount.getUser().getId()));
    }

    @Test
    @Order(3)
    void readBankAccountByBankAccountId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<BankAccount[]> bankAccountList = this.restTemplate.exchange(
                hostname + port + BankAccountPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&bankAccountId=" + bankAccountId, HttpMethod.GET, request, BankAccount[].class);
        assertTrue(bankAccountList.getStatusCode().is2xxSuccessful());
        BankAccount bankAccount1 = bankAccountList.getBody()[0];
        assertTrue(bankAccount1.getAccountNumber().equals(accountNumber));
        assertTrue(bankAccount1.getRoutingNumber().equals(routingNumber));
        assertTrue(bankAccount1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(bankAccount1.getUser().getId().equals(bankAccount.getUser().getId()));
    }

    @Test
    @Order(4)
    void updateBankAccountByBankAccountId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<BankAccount> request = new HttpEntity<>(updatedBankAccount, headers);

        String url = hostname + port
                + BankAccountPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&bankAccountId=" + bankAccountId;
        URI uri = new URI(url);
        ResponseEntity<BankAccount[]> bankAccountList = this.restTemplate.exchange(uri, HttpMethod.PUT,
                request, BankAccount[].class);
        assertTrue(bankAccountList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedBankAccountByBankAccountId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<BankAccount[]> bankAccountList = this.restTemplate.exchange(
                hostname + port
                        + BankAccountPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&bankAccountId=" + bankAccountId, HttpMethod.GET, request, BankAccount[].class);
        assertTrue(bankAccountList.getStatusCode().is2xxSuccessful());
        BankAccount bankAccount1 = bankAccountList.getBody()[0];
        assertTrue(bankAccount1.getAccountNumber().equals(updatedAccountNumber));
        assertTrue(bankAccount1.getRoutingNumber().equals(updatedRoutingNumber));
        assertTrue(bankAccount1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(bankAccount1.getUser().getId().equals(updatedBankAccount.getUser().getId()));
        bankAccountId = bankAccount1.getId();
    }

    @Test
    @Order(6)
    void deleteBankAccountByBankAccountId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + BankAccountPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&bankAccountId=" + bankAccountId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        userService.deleteUserByPersonId(person.getId());

        personService.deletePersonBySsn(person.getSsn());

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
