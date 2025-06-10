package com.umbrella.insurance.endpoints.rest.users.encryptionKeys.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.EncryptionKeyPrivilege;
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
public class EncryptionKeyRestEndpointsTests {
    @Autowired
    private EncryptionKeyRestEndpoints encryptionKeyRestEndpoints;
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
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(encryptionKeyRestEndpoints).isNotNull();
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

    private static Long encryptionKeyId;
    private static String encryptionKeyPrivateKey = "1";
    private static String encryptionKeyPublicKey = "2";
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-10-09 10:10:01");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static String updatedEncryptionKeyPrivateKey = "3";
    private static String updatedEncryptionKeyPublicKey = "4";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2022-10-09 10:10:01");
    private static Person person = new Person();
    private static User user = new User();
    private static EncryptionKey encryptionKey = new EncryptionKey();
    private static EncryptionKey updatedEncryptionKey = new EncryptionKey();
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

        encryptionKey.setEncryptionKeyPublicKey(encryptionKeyPublicKey);
        encryptionKey.setEncryptionKeyPrivateKey(encryptionKeyPrivateKey);
        encryptionKey.setCreatedDateTime(createdDateTime);

        updatedEncryptionKey.setEncryptionKeyPublicKey(updatedEncryptionKeyPublicKey);
        updatedEncryptionKey.setEncryptionKeyPrivateKey(updatedEncryptionKeyPrivateKey);
        updatedEncryptionKey.setCreatedDateTime(updatedCreatedDateTime);
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
    void createEncryptionKey() throws Exception {
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

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        encryptionKey.setUser(user);
        updatedEncryptionKey.setUser(user);

        headers.set("session", session.getSessionCode());
        HttpEntity<EncryptionKey> request = new HttpEntity<>(encryptionKey, headers);

        String url = hostname + port
                + EncryptionKeyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<EncryptionKey> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, EncryptionKey.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        EncryptionKey encryptionKey1 = response.getBody();
        assertTrue(encryptionKey1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(encryptionKey1.getUser().getId().equals(user.getId()));
        assertTrue(encryptionKey1.getEncryptionKeyPrivateKey().equals(encryptionKeyPrivateKey));
        assertTrue(encryptionKey1.getEncryptionKeyPublicKey().equals(encryptionKeyPublicKey));
        encryptionKeyId = encryptionKey1.getId();
        updatedEncryptionKey.setId(encryptionKeyId);
    }

    @Test
    @Order(2)
    void readEncryptionKeyByUserId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + EncryptionKeyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + user.getId();
        ResponseEntity<EncryptionKey[]> encryptionKeyList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, EncryptionKey[].class);
        assertTrue(encryptionKeyList.getStatusCode().is2xxSuccessful());
        EncryptionKey encryptionKey1 = encryptionKeyList.getBody()[0];
        assertTrue(encryptionKey1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(encryptionKey1.getUser().getId().equals(user.getId()));
        assertTrue(encryptionKey1.getEncryptionKeyPrivateKey().equals(encryptionKeyPrivateKey));
        assertTrue(encryptionKey1.getEncryptionKeyPublicKey().equals(encryptionKeyPublicKey));
    }

    @Test
    @Order(3)
    void readEncryptionKeyByEncryptionKeyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<EncryptionKey[]> encryptionKeyList = this.restTemplate.exchange(
                hostname + port + EncryptionKeyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&encryptionKeyId=" + encryptionKeyId, HttpMethod.GET, request, EncryptionKey[].class);
        assertTrue(encryptionKeyList.getStatusCode().is2xxSuccessful());
        EncryptionKey encryptionKey1 = encryptionKeyList.getBody()[0];
        assertTrue(encryptionKey1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(encryptionKey1.getUser().getId().equals(user.getId()));
        assertTrue(encryptionKey1.getEncryptionKeyPrivateKey().equals(encryptionKeyPrivateKey));
        assertTrue(encryptionKey1.getEncryptionKeyPublicKey().equals(encryptionKeyPublicKey));
    }

    @Test
    @Order(4)
    void updateEncryptionKeyByEncryptionKeyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<EncryptionKey> request = new HttpEntity<>(
                updatedEncryptionKey, headers);

        String url = hostname + port
                + EncryptionKeyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&encryptionKeyId=" + encryptionKeyId;
        URI uri = new URI(url);
        ResponseEntity<EncryptionKey[]> encryptionKeyList = this.restTemplate.exchange
                (uri, HttpMethod.PUT, request, EncryptionKey[].class);
        assertTrue(encryptionKeyList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedEncryptionKeyByEncryptionKeyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<EncryptionKey[]> encryptionKeyList = this.restTemplate.exchange(
                hostname + port
                        + EncryptionKeyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&encryptionKeyId=" + encryptionKeyId,
                HttpMethod.GET, request, EncryptionKey[].class);
        assertTrue(encryptionKeyList.getStatusCode().is2xxSuccessful());
        EncryptionKey encryptionKey1 = encryptionKeyList.getBody()[0];
        assertTrue(encryptionKey1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(encryptionKey1.getUser().getId().equals(user.getId()));
        assertTrue(encryptionKey1.getEncryptionKeyPrivateKey().equals(updatedEncryptionKeyPrivateKey));
        assertTrue(encryptionKey1.getEncryptionKeyPublicKey().equals(updatedEncryptionKeyPublicKey));
        encryptionKeyId = encryptionKey1.getId();
    }

    @Test
    @Order(6)
    void deleteEncryptionKeyByEncryptionKeyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + EncryptionKeyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&encryptionKeyId=" + encryptionKeyId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri,
                HttpMethod.DELETE, request, Object.class);
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
