package com.umbrella.insurance.endpoints.rest.ads.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.ads.v1.db.AdPrivilege;
import com.umbrella.insurance.core.models.ads.v1.db.jpa.AdService;
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

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.sql.*;
import java.util.Optional;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdRestEndpointsTests {

    @Autowired
    private AdRestEndpoints adRestEndpoints;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(adRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long adId;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");
    private static String adName = "test";
    private static String adData = "test123";
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-12-11 12:12:12");
    private static String updatedAdName = "12345";
    private static String updatedData = "abc";
    private static Timestamp updatedAdCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
    private static Optional<Session> session = Optional.of(new Session());
    private static Optional<User> user = Optional.of(new User());
    private static Ad ad = new Ad();
    private static Ad updatedAd = new Ad();
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

        ad.setAdName(adName);
        ad.setCreatedDateTime(createdDateTime);
        ad.setAdData(adData);

        updatedAd.setAdData(updatedData);
        updatedAd.setAdName(updatedAdName);
        updatedAd.setCreatedDateTime(updatedAdCreateDateTime);
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
    private AdService adService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createAd() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest, headers);
        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, request, CreateUserResponse.class);
        session.get().setSessionCode(createUserResponse.getBody().getSessionCode());

        session = sessionService.getSessionBySessionCode(session.get().getSessionCode());
        assertTrue(session.isPresent());
        user = userService.getUserByUsername(username);
        Optional<ApplicationRole> applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString());
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole.get());
        userApplicationRoleRelationship.setUser(user.get());
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);
        assertTrue(userApplicationRoleRelationship != null);
        ad.setUser(user.get());
        updatedAd.setUser(user.get());
        System.out.println("SessionCode:" + session.get().getSessionCode());
        headers.set("session", session.get().getSessionCode());
        String url = hostname + port + AdPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<Ad> adRequest = new HttpEntity<>(ad, headers);
        ResponseEntity<Ad> adResponse = this.restTemplate.exchange(
                url, HttpMethod.POST, adRequest, Ad.class);
        assertTrue(adResponse.getStatusCode().is2xxSuccessful());

        //int count = AdsTable.insertAd(ad, connection);
        //assertTrue(count > 0);
        Ad ad1 = adService.findByAdName(ad.getAdName()).get();
        assertTrue(ad1.getAdName().equals(adName));
        assertTrue(ad1.getAdData().equals(adData));
        assertTrue(ad1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(ad1.getUser().getId().equals(user.get().getId()));
        adId = ad1.getId();
        updatedAd.setId(adId);
    }

    @Test
    @Order(2)
    void readAdByAdName() throws Exception {
        String url = hostname + port + AdPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&adName=" + adName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.get().getSessionCode());
        HttpEntity<Ad> request = new HttpEntity<>(ad, headers);

        ResponseEntity<Ad[]> adList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Ad[].class);
        Ad ad1 = adList.getBody()[0];
        assertTrue(ad1.getAdName().equals(adName));
        assertTrue(ad1.getAdData().equals(adData));
        assertTrue(ad1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(ad1.getUser().getId().equals(user.get().getId()));
    }

    @Test
    @Order(3)
    void readAdByAdId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.get().getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port + AdPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&adId=" + adId;
        ResponseEntity<Ad[]> adList = this.restTemplate.exchange(url
                , HttpMethod.GET, request, Ad[].class);
        Ad ad1 = adList.getBody()[0];
        assertTrue(ad1.getAdName().equals(adName));
        assertTrue(ad1.getAdData().equals(adData));
        assertTrue(ad1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(ad1.getUser().getId().equals(user.get().getId()));
    }

    @Test
    @Order(4)
    void updateAdByAdId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.get().getSessionCode());
        HttpEntity<Ad> request = new HttpEntity<>(updatedAd, headers);
        String url = hostname + port
                + AdPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&adId=" + adId;
        URI uri = new URI(url);
        ResponseEntity<Ad[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Ad[].class);
    }

    @Test
    @Order(5)
    void readUpdatedAdByAdId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.get().getSessionCode());
        String url = hostname + port + AdPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&adId=" + adId;
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Ad[]> adList = this.restTemplate.exchange(url
                , HttpMethod.GET, request, Ad[].class);
        Ad ad1 = adList.getBody()[0];
        assertTrue(ad1.getAdName().equals(updatedAdName));
        assertTrue(ad1.getAdData().equals(updatedData));
        assertTrue(ad1.getCreatedDateTime().equals(updatedAdCreateDateTime));
        assertTrue(ad1.getUser().getId().equals(user.get().getId()));
        adId = ad1.getId();
        updatedAd.setId(adId);
    }

    @Test
    @Order(6)
    void deleteAdByAdId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.get().getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + AdPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&adId=" + adId;
        ResponseEntity<Object> response = this.restTemplate.exchange(url
                , HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());


        sessionService.deleteSession(
                session.get().getId());
        User user = session.get().getUser();
        passwordService.deletePasswordByUserId(user.getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                user.getId(), applicationRole.getId());

        userService.deleteUser(user.getId());

        personService.deletePersonBySsn(ssn);
    }
}