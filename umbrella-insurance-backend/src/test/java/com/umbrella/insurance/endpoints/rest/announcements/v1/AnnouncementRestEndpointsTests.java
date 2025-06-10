package com.umbrella.insurance.endpoints.rest.announcements.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.announcements.v1.db.AnnouncementPrivilege;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Announcement;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.endpoints.rest.users.sessions.v1.SessionCreateRestRequest;
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

import java.io.*;
import java.net.URI;
import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnouncementRestEndpointsTests {

    @Autowired
    private AnnouncementRestEndpoints announcementRestEndpoints;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(announcementRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Long announcementId;
    private static String announcementName = "1234";
    private static String message = "123";
    private static Timestamp announcementCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String updatedAnnouncementName = "12345";
    private static String updatedMessage = "abc";
    private static Timestamp updatedAnnouncementCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");
    private static Session session = new Session();
    private static Announcement announcement = new Announcement();
    private static Announcement updatedAnnouncement = new Announcement();
    private static SessionCreateRestRequest sessionCreateRestRequest = new SessionCreateRestRequest();
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

        sessionCreateRestRequest.setPassword(password);
        sessionCreateRestRequest.setUsername(username);

        announcement.setMessage(message);
        announcement.setAnnouncementName(announcementName);
        announcement.setCreatedDateTime(announcementCreateDateTime);
        updatedAnnouncement.setMessage(updatedMessage);
        updatedAnnouncement.setAnnouncementName(updatedAnnouncementName);
        updatedAnnouncement.setCreatedDateTime(updatedAnnouncementCreateDateTime);
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
    private UserService userService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createAnnouncement() throws Exception {

        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        CreateUserResponse createUserResponse = this.restTemplate.postForObject(
                createUserUrl, createUserRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        announcement.setSession(session);
        updatedAnnouncement.setSession(session);
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        String url = hostname + port
                + AnnouncementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Announcement> request = new HttpEntity<>(announcement, headers);
        ResponseEntity<Announcement> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Announcement.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().getAnnouncementName().equals(announcementName));
        assertTrue(response.getBody().getMessage().equals(message));
        assertTrue(response.getBody().getCreatedDateTime().equals(announcementCreateDateTime));
        assertTrue(response.getBody().getSession().getId().equals(session.getId()));
        announcementId = response.getBody().getId();
        updatedAnnouncement.setId(announcementId);
    }

    @Test
    @Order(2)
    void readAnnouncementByAnnouncementName() throws Exception {
        String url = hostname + port
                + AnnouncementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&announcementName=" + announcementName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Announcement> request = new HttpEntity<>(announcement, headers);
        ResponseEntity<Announcement[]> announcementList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Announcement[].class);
        Announcement announcement1 = announcementList.getBody()[0];
        assertTrue(announcement1.getAnnouncementName().equals(announcementName));
        assertTrue(announcement1.getMessage().equals(message));
        assertTrue(announcement1.getCreatedDateTime().equals(announcementCreateDateTime));
        assertTrue(announcement1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(3)
    void readAnnouncementByAnnouncementId() throws Exception {
        String url = hostname + port + AnnouncementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&announcementId=" + announcementId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Announcement> request = new HttpEntity<>(announcement, headers);
        ResponseEntity<Announcement[]> announcementList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Announcement[].class);
        Announcement announcement1 = announcementList.getBody()[0];
        assertTrue(announcement1.getAnnouncementName().equals(announcementName));
        assertTrue(announcement1.getMessage().equals(message));
        assertTrue(announcement1.getCreatedDateTime().equals(announcementCreateDateTime));
        assertTrue(announcement1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(4)
    void updateAnnouncementByAnnouncementId() throws Exception {
        String url = hostname + port
                + AnnouncementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&announcementId=" + announcementId;
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Announcement> request = new HttpEntity<>(updatedAnnouncement, headers);
        ResponseEntity<Announcement[]> response= this.restTemplate.exchange(uri, HttpMethod.PUT, request, Announcement[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedAnnouncementByAnnouncementId() throws Exception {
        Announcement[] announcementList = this.restTemplate.getForObject(
                hostname + port
                        + AnnouncementPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&announcementId=" + announcementId, Announcement[].class);
        Announcement announcement1 = announcementList[0];
        assertTrue(announcement1.getAnnouncementName().equals(updatedAnnouncementName));
        assertTrue(announcement1.getMessage().equals(updatedMessage));
        assertTrue(announcement1.getCreatedDateTime().equals(updatedAnnouncementCreateDateTime));
        assertTrue(announcement1.getSession().getId().equals(session.getId()));
        announcementId = announcement1.getId();
    }

    @Test
    @Order(6)
    void deleteAnnouncementByAnnouncementId() throws Exception {

        String url = hostname + port
                + AnnouncementPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&announcementId=" + announcementId;
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
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