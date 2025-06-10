package com.umbrella.insurance.endpoints.rest.users.verificationCodes.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.VerificationCodePrivilege;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
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
public class VerificationCodeRestEndpointsTests {
    @Autowired
    private VerificationCodeRestEndpoints verificationCodeRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(verificationCodeRestEndpoints).isNotNull();
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

    private static Long verificationCodeId;
    private static String verificationCodeText = "3";
    private static Boolean isVerified = true;
    private static Timestamp expirationDateTime = Timestamp.valueOf("1111-11-11 11:11:11");
    private static Timestamp verifiedDateTime = Timestamp.valueOf("2222-11-11 22:22:22");
    private static Long minutesToVerify = 3l;
    private static Long maxAttempts = 4l;
    private static Long currentAttempt = 5l;
    private static String updatedVerificationCodeText = "10";
    private static Boolean updatedIsVerified = false;
    private static Timestamp updatedExpirationDateTime = Timestamp.valueOf("3333-11-11 11:11:11");
    private static Timestamp updatedVerifiedDateTime = Timestamp.valueOf("4444-11-11 11:11:11");
    private static Long updatedMinutesToVerify = 11l;
    private static Long updatedMaxAttempts = 12l;
    private static Long updatedCurrentAttempt = 13l;
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = true;
    private static VerificationMethod verificationMethod = new VerificationMethod();
    private static Person person = new Person();
    //private static User user = new User();
    private static Device device = new Device();
    private static VerificationCode verificationCode = new VerificationCode();
    private static VerificationCode updatedVerificationCode = new VerificationCode();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    private static long userId;
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

        verificationMethod.setVerificationMethodName("t");

        device.setUserAgent("1");
        device.setCreatedDateTime(Timestamp.valueOf("1111-11-11 11:11:11"));
        device.setIpAddress("234.33.33.33");
        device.setDeviceName("test");

        //person.setSsn("123");
        //person.setDateOfBirth(Date.valueOf("1111-11-11"));
        //person.setSurname("last");
        //person.setMiddleName("middle");
        //person.setFirstName("first");

        //user.setCreatedDateTime(createdDateTime);
        //user.setEmailAddress(emailAddress);
        //user.setPhoneNumber(phoneNumber);
        //user.setUsername(username);
        //user.setIsEmailAddressVerified(isEmailAddressVerified);
        //user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        //user.setIsAuthAppVerified(true);

        verificationCode.setVerificationCode(verificationCodeText);
        verificationCode.setIsVerified(isVerified);
        verificationCode.setExpirationDateTime(expirationDateTime);
        verificationCode.setVerifiedDateTime(verifiedDateTime);
        verificationCode.setMinutesToVerify(minutesToVerify);
        verificationCode.setMaxAttempts(maxAttempts);
        verificationCode.setCurrentAttempt(currentAttempt);

        updatedVerificationCode.setVerificationCode(updatedVerificationCodeText);
        updatedVerificationCode.setIsVerified(updatedIsVerified);
        updatedVerificationCode.setExpirationDateTime(updatedExpirationDateTime);
        updatedVerificationCode.setVerifiedDateTime(updatedVerifiedDateTime);
        updatedVerificationCode.setMinutesToVerify(updatedMinutesToVerify);
        updatedVerificationCode.setMaxAttempts(updatedMaxAttempts);
        updatedVerificationCode.setCurrentAttempt(updatedCurrentAttempt);
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
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private VerificationMethodService verificationMethodService;

    @Test
    @Order(1)
    void createVerificationCode() throws Exception {
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
        userId = user2.getId();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        verificationMethod = verificationMethodService.saveVerificationMethod(verificationMethod);
        verificationCode.setVerificationMethod(verificationMethod);
        updatedVerificationCode.setVerificationMethod(verificationMethod);

        person = personService.getPersonBySsn(ssn2).get();
        //user.setPerson(person);

        //device = deviceService.saveDevice(device);

        //user = userService.getUserByUsername(username).get();
        verificationCode.setUser(user2);
        updatedVerificationCode.setUser(user2);

        headers.set("session", session.getSessionCode());
        HttpEntity<VerificationCode> request = new HttpEntity<>(verificationCode, headers);

        String url = hostname + port
                + VerificationCodePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<VerificationCode> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, VerificationCode.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        VerificationCode verificationCode1 = response.getBody();
        assertTrue(verificationCode1.getVerificationMethod().getId().equals(
                verificationMethod.getId()));
        assertTrue(verificationCode1.getVerificationCode().equals(verificationCode.getVerificationCode()));
        assertTrue(verificationCode1.getIsVerified().equals(isVerified));
        assertTrue(verificationCode1.getExpirationDateTime().equals(expirationDateTime));
        assertTrue(verificationCode1.getVerifiedDateTime().equals(verifiedDateTime));
        assertTrue(verificationCode1.getMinutesToVerify().equals(minutesToVerify));
        assertTrue(verificationCode1.getMaxAttempts().equals(maxAttempts));
        assertTrue(verificationCode1.getCurrentAttempt().equals(currentAttempt));
        verificationCodeId = verificationCode1.getId();
        updatedVerificationCode.setId(verificationCodeId);
    }

    @Test
    @Order(2)
    void readVerificationCodeBySessionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + VerificationCodePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + userId;
        ResponseEntity<VerificationCode[]> verificationCodeList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, VerificationCode[].class);
        assertTrue(verificationCodeList.getStatusCode().is2xxSuccessful());
        VerificationCode verificationCode1 = verificationCodeList.getBody()[0];
        assertTrue(verificationCode1.getVerificationMethod().getId().equals(
                verificationMethod.getId()));
        assertTrue(verificationCode1.getVerificationCode().equals(verificationCode.getVerificationCode()));
        assertTrue(verificationCode1.getIsVerified().equals(isVerified));
        assertTrue(verificationCode1.getExpirationDateTime().equals(expirationDateTime));
        assertTrue(verificationCode1.getVerifiedDateTime().equals(verifiedDateTime));
        assertTrue(verificationCode1.getMinutesToVerify().equals(minutesToVerify));
        assertTrue(verificationCode1.getMaxAttempts().equals(maxAttempts));
        assertTrue(verificationCode1.getCurrentAttempt().equals(currentAttempt));
    }

    @Test
    @Order(3)
    void readVerificationCodeByVerificationCodeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<VerificationCode[]> verificationCodeList = this.restTemplate.exchange(
                hostname + port + VerificationCodePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&verificationCodeId=" + verificationCodeId, HttpMethod.GET, request, VerificationCode[].class);
        VerificationCode verificationCode1 = verificationCodeList.getBody()[0];
        assertTrue(verificationCode1.getVerificationMethod().getId().equals(
                verificationMethod.getId()));
        assertTrue(verificationCode1.getVerificationCode().equals(verificationCode.getVerificationCode()));
        assertTrue(verificationCode1.getIsVerified().equals(isVerified));
        assertTrue(verificationCode1.getExpirationDateTime().equals(expirationDateTime));
        assertTrue(verificationCode1.getVerifiedDateTime().equals(verifiedDateTime));
        assertTrue(verificationCode1.getMinutesToVerify().equals(minutesToVerify));
        assertTrue(verificationCode1.getMaxAttempts().equals(maxAttempts));
        assertTrue(verificationCode1.getCurrentAttempt().equals(currentAttempt));
    }

    @Test
    @Order(4)
    void updateVerificationCodeByVerificationCodeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<VerificationCode> request = new HttpEntity<>(updatedVerificationCode, headers);

        String url = hostname + port
                + VerificationCodePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&verificationCodeId=" + verificationCodeId;
        URI uri = new URI(url);
        ResponseEntity<VerificationCode[]> response = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, VerificationCode[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedVerificationCodeByVerificationCodeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<VerificationCode[]> verificationCodeList = this.restTemplate.exchange(
                hostname + port
                        + VerificationCodePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&verificationCodeId=" + verificationCodeId, HttpMethod.GET, request,
                VerificationCode[].class);
        assertTrue(verificationCodeList.getStatusCode().is2xxSuccessful());
        VerificationCode verificationCode1 = verificationCodeList.getBody()[0];
        assertTrue(verificationCode1.getUser().getId().equals(userId));
        assertTrue(verificationCode1.getVerificationMethod().getId().equals(
                verificationMethod.getId()));
        assertTrue(verificationCode1.getVerificationCode().equals(updatedVerificationCode.getVerificationCode()));
        assertTrue(verificationCode1.getIsVerified().equals(updatedIsVerified));
        assertTrue(verificationCode1.getExpirationDateTime().equals(updatedExpirationDateTime));
        assertTrue(verificationCode1.getVerifiedDateTime().equals(updatedVerifiedDateTime));
        assertTrue(verificationCode1.getMinutesToVerify().equals(updatedMinutesToVerify));
        assertTrue(verificationCode1.getMaxAttempts().equals(updatedMaxAttempts));
        assertTrue(verificationCode1.getCurrentAttempt().equals(updatedCurrentAttempt));
        verificationCodeId = verificationCode1.getId();
    }

    @Test
    @Order(6)
    void deleteVerificationCodeByVerificationCodeId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + VerificationCodePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&verificationCodeId=" + verificationCodeId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri,
                HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        verificationMethodService.deleteVerificationMethodByVerificationMethodName(
                verificationMethod.getVerificationMethodName());

        deviceService.deleteDeviceByDeviceName(
                device.getDeviceName());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(userId);

        personService.deletePersonBySsn(
                person.getSsn());

        sessionService.deleteSession(session.getId());

        //userService.getUserByUserId(session.getUser().getId());

        //personService.deletePersonBySsn(ssn2);
    }
}
