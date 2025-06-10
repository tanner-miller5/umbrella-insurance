package com.umbrella.insurance.core.models.users.sessions.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.Device;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.Session;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
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
public class SessionsTableTests {
    private static String sessionCode = "11";
    private static Timestamp startDateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static Timestamp endDateTime = Timestamp.valueOf("2020-11-11 11:11:11");
    private static Long minutesToExpire = 2l;
    private static String updatedSessionCode = "22";
    private static Timestamp updatedStartDateTime = Timestamp.valueOf("2022-11-11 11:11:55");
    private static Timestamp updatedEndDateTime = Timestamp.valueOf("2022-11-11 20:33:11");
    private static Long updatedMinutesToExpire = 6l;
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String deviceName = "1234";
    private static String ipAddress = "2";
    private static String userAgent = "3";
    private static Person person = new Person();
    private static User user = new User();
    private static Device device = new Device();
    private static Session session = new Session();
    private static Session updatedSession = new Session();
    static {
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
        user.setIsAuthAppVerified(isAuthAppVerified);

        device.setDeviceName(deviceName);
        device.setIpAddress(ipAddress);
        device.setUserAgent(userAgent);
        device.setCreatedDateTime(createdDateTime);

        session.setSessionCode(sessionCode);
        session.setStartDateTime(startDateTime);
        session.setEndDateTime(endDateTime);
        session.setMinutesToExpire(minutesToExpire);

        updatedSession.setSessionCode(updatedSessionCode);
        updatedSession.setStartDateTime(updatedStartDateTime);
        updatedSession.setEndDateTime(updatedEndDateTime);
        updatedSession.setMinutesToExpire(updatedMinutesToExpire);
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
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;

    @Test
    @Order(2)
    void insertSessionTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);
        updatedSession.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);
        updatedSession.setDevice(device);

        session = sessionService.saveSession(session);
        updatedSession.setId(session.getId());
    }
    @Test
    @Order(3)
    void selectSessionByUserIdTest() throws SQLException {
        Session session1 = sessionService.getSessionByUserId(user.getId()).get();
        assertTrue(session1.getDevice().getId().equals(device.getId()));
        assertTrue(session1.getEndDateTime().equals(endDateTime));
        assertTrue(session1.getStartDateTime().equals(startDateTime));
        assertTrue(session1.getSessionCode().equals(sessionCode));
        assertTrue(session1.getUser().getId().equals(user.getId()));
        assertTrue(session1.getMinutesToExpire().equals(minutesToExpire));
    }
    @Test
    @Order(4)
    void selectSessionBySessionCodeTest() throws SQLException {
        Session session1 = sessionService.getSessionBySessionCode(sessionCode).get();
        assertTrue(session1.getDevice().getId().equals(device.getId()));
        assertTrue(session1.getEndDateTime().equals(endDateTime));
        assertTrue(session1.getStartDateTime().equals(startDateTime));
        assertTrue(session1.getSessionCode().equals(sessionCode));
        assertTrue(session1.getUser().getId().equals(user.getId()));
        assertTrue(session1.getMinutesToExpire().equals(minutesToExpire));
    }
    @Test
    @Order(5)
    void updateSessionByUserIdTest() throws SQLException {
        updatedSession = sessionService.updateSession(
                updatedSession);
    }
    @Test
    @Order(6)
    void selectUpdatedSessionByUserIdTest() throws SQLException {
        Session session1 = sessionService.getSessionByUserId(
                user.getId()).get();
        assertTrue(session1.getDevice().getId().equals(device.getId()));
        assertTrue(session1.getEndDateTime().equals(updatedEndDateTime));
        assertTrue(session1.getStartDateTime().equals(updatedStartDateTime));
        assertTrue(session1.getSessionCode().equals(updatedSessionCode));
        assertTrue(session1.getUser().getId().equals(user.getId()));
        assertTrue(session1.getMinutesToExpire().equals(updatedMinutesToExpire));
    }
    @Test
    @Order(7)
    void deleteSessionByUserIdTest() throws SQLException {
        sessionService.deleteSession(
                updatedSession.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
