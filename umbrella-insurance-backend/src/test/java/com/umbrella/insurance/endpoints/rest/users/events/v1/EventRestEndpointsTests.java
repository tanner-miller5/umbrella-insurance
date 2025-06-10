package com.umbrella.insurance.endpoints.rest.users.events.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa.EventTypeService;
import com.umbrella.insurance.core.models.users.events.v1.db.EventPrivilege;
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
public class EventRestEndpointsTests {
    @Autowired
    private EventRestEndpoints eventRestEndpoints;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventTypeService eventTypeService;

    @Test
    void contextLoads() throws Exception {
        assertThat(eventRestEndpoints).isNotNull();
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

    private static Long eventId;
    private static Timestamp createdDateTime = Timestamp.valueOf("2100-08-05 05:04:34");
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2100-08-05 05:04:34");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static EventType eventType = new EventType();
    private static Person person = new Person();
    private static User user = new User();
    private static Event event = new Event();
    private static Event updatedEvent = new Event();
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

        eventType.setEventTypeName("event type name test");

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

        event.setCreatedDateTime(createdDateTime);

        updatedEvent.setCreatedDateTime(updatedCreatedDateTime);
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
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService2;

    @Autowired
    private PasswordService passwordService;



    @Test
    @Order(1)
    void createEvent() throws Exception {
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

        eventType = eventTypeService.saveEventType(eventType);
        event.setEventType(eventType);
        updatedEvent.setEventType(eventType);

        personService.savePerson(person);
        person = personService.getPersonBySsn(person.getSsn()).get();
        user.setPerson(person);

        user = userService.saveUser(user);
        user = userService.getUserByPersonId(person.getId()).get();

        event.setSession(session);
        updatedEvent.setSession(session);

        headers.set("session", session.getSessionCode());
        HttpEntity<Event> request = new HttpEntity<>(event, headers);

        String url = hostname + port
                + EventPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Event> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Event.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Event event1 = response.getBody();
        assertTrue(event1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(event1.getEventType().getId().equals(eventType.getId()));
        assertTrue(event1.getSession().getId().equals(session.getId()));
        eventId = event1.getId();
        updatedEvent.setId(eventId);
    }

    @Test
    @Order(2)
    void readEventByEventName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + EventPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&sessionId=" + session.getId();
        ResponseEntity<Event[]> eventList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Event[].class);
        assertTrue(eventList.getStatusCode().is2xxSuccessful());
        Event event1 = eventList.getBody()[0];
        assertTrue(event1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(event1.getEventType().getId().equals(eventType.getId()));
        assertTrue(event1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(3)
    void readEventByEventId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Event[]> eventList = this.restTemplate.exchange(
                hostname + port + EventPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&eventId=" + eventId, HttpMethod.GET, request, Event[].class);
        assertTrue(eventList.getStatusCode().is2xxSuccessful());
        Event event1 = eventList.getBody()[0];
        assertTrue(event1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(event1.getEventType().getId().equals(eventType.getId()));
        assertTrue(event1.getSession().getId().equals(session.getId()));
    }

    @Test
    @Order(4)
    void updateEventByEventId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Event> request = new HttpEntity<>(updatedEvent, headers);

        String url = hostname + port
                + EventPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&eventId=" + eventId;
        URI uri = new URI(url);
        ResponseEntity<Event[]> eventList = this.restTemplate.exchange(
                uri, HttpMethod.PUT, request, Event[].class);
        assertTrue(eventList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedEventByEventId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Event[]> eventList = this.restTemplate.exchange(
                hostname + port
                        + EventPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&eventId=" + eventId, HttpMethod.GET, request, Event[].class);
        assertTrue(eventList.getStatusCode().is2xxSuccessful());
        Event event1 = eventList.getBody()[0];
        assertTrue(event1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(event1.getEventType().getId().equals(eventType.getId()));
        assertTrue(event1.getSession().getId().equals(session.getId()));
        eventId = event1.getId();
    }

    @Test
    @Order(6)
    void deleteEventByEventId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + EventPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&eventId=" + eventId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(
                uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        userService.deleteUserByPersonId(person.getId());

        personService.deletePersonBySsn(person.getSsn());

        eventTypeService.deleteEventTypeByEventTypeName(eventType.getEventTypeName());

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
