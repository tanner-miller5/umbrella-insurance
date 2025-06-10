package com.umbrella.insurance.endpoints.rest.users.accountBalances.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.units.v1.UnitEnum;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa.AccountBalanceTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.v1.db.AccountBalancePrivilege;
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
public class AccountBalanceRestEndpointsTests {
    @Autowired
    private AccountBalanceRestEndpoints accountBalanceRestEndpoints;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private AccountBalanceTypeService accountBalanceTypeService;
    @Autowired
    private UnitService unitService;

    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(accountBalanceRestEndpoints).isNotNull();
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

    private static Long accountBalanceId;
    private static Double accountBalanceValue = 1.0;
    private static Timestamp updatedDateTime = Timestamp.valueOf("2024-12-12 12:00:00");
    private static Double updatedAccountBalanceValue = 2.0;
    private static Timestamp updatedUpdatedDateTime = Timestamp.valueOf("2021-12-12 12:00:00");
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static AccountBalanceType accountBalanceType = new AccountBalanceType();
    private static AccountBalance accountBalance = new AccountBalance();
    private static AccountBalance updatedAccountBalance = new AccountBalance();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    private static Unit unit = new Unit();
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

        unit.setUnitName(UnitEnum.butter_bucks.name());

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

        accountBalanceType.setAccountBalanceTypeName("3333");

        accountBalance.setAccountBalanceValue(accountBalanceValue);
        accountBalance.setUpdatedDateTime(updatedDateTime);

        updatedAccountBalance.setAccountBalanceValue(updatedAccountBalanceValue);
        updatedAccountBalance.setUpdatedDateTime(updatedUpdatedDateTime);
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
    void createAccountBalance() throws Exception {
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
        accountBalance.setUser(user);
        updatedAccountBalance.setUser(user);

        accountBalanceType = accountBalanceTypeService.saveAccountBalanceType(accountBalanceType);
        accountBalance.setAccountBalanceType(accountBalanceType);
        updatedAccountBalance.setAccountBalanceType(accountBalanceType);

        unit = unitService.getUnitByUnitName(unit.getUnitName()).get();
        accountBalance.setUnit(unit);
        updatedAccountBalance.setUnit(unit);

        headers.set("session", session.getSessionCode());
        HttpEntity<AccountBalance> request = new HttpEntity<>(accountBalance, headers);

        String url = hostname + port
                + AccountBalancePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<AccountBalance> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, AccountBalance.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        AccountBalance accountBalance1 = response.getBody();
        assertTrue(accountBalance1.getAccountBalanceValue().equals(accountBalanceValue));
        assertTrue(accountBalance1.getUser().getId().equals(user.getId()));
        assertTrue(accountBalance1.getUpdatedDateTime().equals(updatedDateTime));
        assertTrue(accountBalance1.getAccountBalanceType().getId().equals(
                accountBalance.getAccountBalanceType().getId()));
        accountBalanceId = accountBalance1.getId();
        updatedAccountBalance.setId(accountBalanceId);
    }

    @Test
    @Order(2)
    void readAccountBalanceByAccountBalanceName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + AccountBalancePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + user.getId()
                + "&accountBalanceTypeId=" + accountBalanceType.getId()
                + "&unitId=" + unit.getId();
        ResponseEntity<AccountBalance[]> accountBalanceList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, AccountBalance[].class);
        assertTrue(accountBalanceList.getStatusCode().is2xxSuccessful());
        AccountBalance accountBalance1 = accountBalanceList.getBody()[0];
        assertTrue(accountBalance1.getAccountBalanceValue().equals(accountBalanceValue));
        assertTrue(accountBalance1.getUser().getId().equals(user.getId()));
        assertTrue(accountBalance1.getUpdatedDateTime().equals(updatedDateTime));
        assertTrue(accountBalance1.getAccountBalanceType().getId().equals(accountBalance.getAccountBalanceType().getId()));
    }

    @Test
    @Order(3)
    void readAccountBalanceByAccountBalanceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<AccountBalance[]> accountBalanceList = this.restTemplate.exchange(
                hostname + port + AccountBalancePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&accountBalanceId=" + accountBalanceId, HttpMethod.GET, request, AccountBalance[].class);
        assertTrue(accountBalanceList.getStatusCode().is2xxSuccessful());
        AccountBalance accountBalance1 = accountBalanceList.getBody()[0];
        assertTrue(accountBalance1.getAccountBalanceValue().equals(accountBalanceValue));
        assertTrue(accountBalance1.getUser().getId().equals(user.getId()));
        assertTrue(accountBalance1.getUpdatedDateTime().equals(updatedDateTime));
        assertTrue(accountBalance1.getAccountBalanceType().getId().equals(accountBalance.getAccountBalanceType().getId()));
    }

    @Test
    @Order(4)
    void updateAccountBalanceByAccountBalanceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<AccountBalance> request = new HttpEntity<>(updatedAccountBalance, headers);

        String url = hostname + port
                + AccountBalancePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&accountBalanceId=" + accountBalanceId;
        URI uri = new URI(url);
        ResponseEntity<AccountBalance[]> accountBalanceList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, AccountBalance[].class);
        assertTrue(accountBalanceList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedAccountBalanceByAccountBalanceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<AccountBalance[]> accountBalanceList = this.restTemplate.exchange(
                hostname + port
                        + AccountBalancePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&accountBalanceId=" + accountBalanceId, HttpMethod.GET, request, AccountBalance[].class);
        assertTrue(accountBalanceList.getStatusCode().is2xxSuccessful());
        AccountBalance accountBalance1 = accountBalanceList.getBody()[0];
        assertTrue(accountBalance1.getAccountBalanceValue().equals(updatedAccountBalanceValue));
        assertTrue(accountBalance1.getUser().getId().equals(user.getId()));
        assertTrue(accountBalance1.getUpdatedDateTime().equals(updatedUpdatedDateTime));
        assertTrue(accountBalance1.getAccountBalanceType().getId().equals(
                accountBalance.getAccountBalanceType().getId()));
        accountBalanceId = accountBalance1.getId();
    }

    @Test
    @Order(6)
    void deleteAccountBalanceByAccountBalanceId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + AccountBalancePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&accountBalanceId=" + accountBalanceId;
        URI uri = new URI(url);
        ResponseEntity<Object> accountBalanceList = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(accountBalanceList.getStatusCode().is2xxSuccessful());

        accountBalanceTypeService.deleteAccountBalanceTypeByAccountBalanceTypeName(
                accountBalanceType.getAccountBalanceTypeName());

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
