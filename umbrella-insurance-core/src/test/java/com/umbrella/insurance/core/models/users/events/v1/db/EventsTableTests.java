package com.umbrella.insurance.core.models.users.events.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa.EventTypeService;
import com.umbrella.insurance.core.models.users.events.v1.db.jpa.EventService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventsTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2100-08-05 05:04:34");
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2100-08-05 05:04:34");
    private static String sessionCode = "11";
    private static Timestamp startDateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static Timestamp endDateTime = Timestamp.valueOf("2020-11-11 11:11:11");
    private static Long minutesToExpire = 2l;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String deviceName = "1234";
    private static String ipAddress = "2";
    private static String userAgent = "3";
    private static EventType eventType = new EventType();
    private static Person person = new Person();
    private static User user = new User();
    private static Device device = new Device();
    private static Session session = new Session();
    private static Event event = new Event();
    private static Event updatedEvent = new Event();
    static {
        eventType.setEventTypeName("event type name test");

        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        device.setDeviceName(deviceName);
        device.setIpAddress(ipAddress);
        device.setUserAgent(userAgent);
        device.setCreatedDateTime(createdDateTime);

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(isAuthAppVerified);

        session.setSessionCode(sessionCode);
        session.setStartDateTime(startDateTime);
        session.setEndDateTime(endDateTime);
        session.setMinutesToExpire(minutesToExpire);

        event.setCreatedDateTime(createdDateTime);

        updatedEvent.setCreatedDateTime(updatedCreatedDateTime);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();

    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;

    @Test
    @Order(2)
    void insertEventTest() throws SQLException {
        eventType = eventTypeService.saveEventType(eventType);
        event.setEventType(eventType);
        updatedEvent.setEventType(eventType);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        event.setSession(session);
        updatedEvent.setSession(session);

        event = eventService.saveEvent(event);
        updatedEvent.setId(event.getId());
    }
    @Test
    @Order(3)
    void selectLatestEventBySessionIdTest() throws SQLException {
        Event event1 = eventService.getEventBySessionId(
                session.getId()).get();
        assertTrue(event1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(event1.getEventType().getId().equals(eventType.getId()));
        assertTrue(event1.getSession().getId().equals(session.getId()));
    }
    @Test
    @Order(4)
    void updateLatestEventBySessionIdTest() throws SQLException {
        updatedEvent = eventService.updateEvent(
                updatedEvent);
    }
    @Test
    @Order(5)
    void selectUpdatedLatestEventBySessionIdTest() throws SQLException {
        Event event1 = eventService.getEventBySessionId(
                session.getId()).get();
        assertTrue(event1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(event1.getEventType().getId().equals(eventType.getId()));
        assertTrue(event1.getSession().getId().equals(session.getId()));
    }
    @Test
    @Order(6)
    void deleteLatestEventBySessionIdTest() throws SQLException {
        eventService.deleteEvent(
                event.getId());

        sessionService.deleteSession(session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());

        eventTypeService.deleteEventType(eventType.getId());
    }

}
