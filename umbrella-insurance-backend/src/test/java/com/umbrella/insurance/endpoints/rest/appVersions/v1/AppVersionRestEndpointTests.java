package com.umbrella.insurance.endpoints.rest.appVersions.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.appVersions.v1.db.AppVersionPrivilege;
import com.umbrella.insurance.core.models.appVersions.v1.db.jpa.AppVersionService;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
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
public class AppVersionRestEndpointTests {
    @Autowired
    private AppVersionRestEndpoints appVersionRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(appVersionRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long appVersionId;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");
    private static String appName = "test";
    private static String appVersionText = "test123";
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-12-11 12:12:12");
    private static String updatedAppName = "12345";
    private static String updatedAppVersionText = "abc";
    private static Session session = new Session();
    private static AppVersion appVersion = new AppVersion();
    private static AppVersion updatedAppVersion = new AppVersion();
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
        createUserRequest.setTwoFactorMethod("none");

        appVersion.setAppName(appName);
        appVersion.setAppVersion(appVersionText);

        updatedAppVersion.setAppName(updatedAppName);
        updatedAppVersion.setAppVersion(updatedAppVersionText);
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
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createAppVersion() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest, headers);
        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, request, CreateUserResponse.class);
        session.setSessionCode(createUserResponse.getBody().getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);
        appVersion.setSession(session);
        updatedAppVersion.setSession(session);
        System.out.println("SessionCode:" + session.getSessionCode());
        headers.set("session", session.getSessionCode());
        String url = hostname + port + AppVersionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<AppVersion> appVersionRequest = new HttpEntity<>(appVersion, headers);
        ResponseEntity<AppVersion> appVersionResponse = this.restTemplate.exchange(
                url, HttpMethod.POST, appVersionRequest, AppVersion.class);
        assertTrue(appVersionResponse.getStatusCode().is2xxSuccessful());

        //int count = AppVersionsTable.insertAppVersion(appVersion, connection);
        //assertTrue(count > 0);
        AppVersion appVersion1 = appVersionService.getAppVersionByAppName(appVersion.getAppName()).get();
        assertTrue(appVersion1.getAppName().equals(appName));
        assertTrue(appVersion1.getAppVersion().equals(appVersionText));
        assertTrue(appVersion1.getSession().getId().equals(session.getId()));
        appVersionId = appVersion1.getId();
        updatedAppVersion.setId(appVersionId);
    }

    @Test
    @Order(2)
    void readAppVersionByAppVersionName() throws Exception {
        String url = hostname + port + AppVersionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&appName=" + appName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<AppVersion> request = new HttpEntity<>(appVersion, headers);

        ResponseEntity<AppVersion[]> appVersionList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, AppVersion[].class);
        AppVersion appVersion1 = appVersionList.getBody()[0];
        assertTrue(appVersion1.getAppName().equals(appName));
        assertTrue(appVersion1.getAppVersion().equals(appVersionText));
        assertTrue(appVersion1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(3)
    void readAppVersionByAppVersionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port + AppVersionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&appVersionId=" + appVersionId;
        ResponseEntity<AppVersion[]> appVersionList = this.restTemplate.exchange(url
                , HttpMethod.GET, request, AppVersion[].class);
        AppVersion appVersion1 = appVersionList.getBody()[0];
        assertTrue(appVersion1.getAppVersion().equals(appVersionText));
        assertTrue(appVersion1.getAppName().equals(appName));
        assertTrue(appVersion1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(4)
    void updateAppVersionByAppVersionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<AppVersion> request = new HttpEntity<>(updatedAppVersion, headers);
        String url = hostname + port
                + AppVersionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&appVersionId=" + appVersionId;
        URI uri = new URI(url);
        ResponseEntity<AppVersion[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request,
                AppVersion[].class);
    }

    @Test
    @Order(5)
    void readUpdatedAppVersionByAppVersionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        String url = hostname + port + AppVersionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&appVersionId=" + appVersionId;
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<AppVersion[]> appVersionList = this.restTemplate.exchange(url
                , HttpMethod.GET, request, AppVersion[].class);
        AppVersion appVersion1 = appVersionList.getBody()[0];
        assertTrue(appVersion1.getAppName().equals(updatedAppName));
        assertTrue(appVersion1.getAppVersion().equals(updatedAppVersionText));
        assertTrue(appVersion1.getSession().getId().equals(session.getId()));
        appVersionId = appVersion1.getId();
    }

    @Test
    @Order(6)
    void deleteAppVersionByAppVersionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + AppVersionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&appVersionId=" + appVersionId;
        ResponseEntity<Object> response = this.restTemplate.exchange(url
                , HttpMethod.DELETE, request, Object.class);
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
