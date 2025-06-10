package com.umbrella.insurance.core.models.appVersions.v1.db;

import com.umbrella.insurance.core.models.appVersions.v1.db.jpa.AppVersionService;
import com.umbrella.insurance.core.models.databases.v1.Database;

import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;

import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;

import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;

import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AppVersionsTableTests {
    private static String appName = "1234";
    private static String appVers = "123";
    private static String updatedAppName = "12345";
    private static String updatedAppVers = "abc";
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
    private static String sessionCode = "11";
    private static Timestamp startDateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static Timestamp endDateTime = Timestamp.valueOf("2020-11-11 11:11:11");
    private static Long minutesToExpire = 2l;
    private static Person person = new Person();
    private static User user = new User();
    private static Device device = new Device();
    private static Session session = new Session();
    private static AppVersion appVersion = new AppVersion();
    private static AppVersion updatedAppVersion = new AppVersion();

    static {
        person.setSsn("123c");
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

        appVersion.setAppName(appName);
        appVersion.setAppVersion(appVers);


        updatedAppVersion.setAppName(updatedAppName);
        updatedAppVersion.setAppVersion(updatedAppVers);
    }

    @Autowired
    PersonService personService;

    @Autowired
    UserService userService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    SessionService sessionService;

    @Autowired
    AppVersionService appVersionService;

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

    @Test
    @Order(3)
    void insertAppVersionTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        appVersion.setSession(session);
        updatedAppVersion.setSession(session);

        appVersion = appVersionService.saveAppVersion(appVersion);
        updatedAppVersion.setId(appVersion.getId());
    }
    @Test
    @Order(4)
    void selectAppVersionByAppNameTest() throws SQLException {
        AppVersion appVersion1 = appVersionService.getAppVersionByAppName(appName).get();
        assertTrue(appVersion1.getAppName().equals(appName));
        assertTrue(appVersion1.getAppVersion().equals(appVers));
    }
    @Test
    @Order(5)
    void updateAppVersionByAppNameTest() throws SQLException {
        updatedAppVersion = appVersionService.updateAppVersion(updatedAppVersion);
    }
    @Test
    @Order(6)
    void selectUpdatedAppVersionByAppNameTest() throws SQLException {
        AppVersion appVersion1 = appVersionService.getAppVersionByAppName(updatedAppName).get();
        assertTrue(appVersion1.getAppName().equals(updatedAppName));
        assertTrue(appVersion1.getAppVersion().equals(updatedAppVers));
    }
    @Test
    @Order(7)
    void deleteAppVersionByAppNameTest() throws SQLException {
        appVersionService.deleteAppVersion(updatedAppVersion.getId());

        sessionService.deleteSession(
                session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

    @Test
    @Order(8)
    void jpaTest() {

        try {
            for (int i = 1; i <= 5; i++) {
                AppVersion appVersion = new AppVersion();
                appVersion.setAppName("AppVersion " + i); // Setting unique names for each app_version
                appVersion.setAppVersion("" + 50000 + (i * 1000)); //  app_version for each app_version
                appVersionService.saveAppVersion(appVersion);
            }

            // Read Operation: Print all app_versions
            List<AppVersion> allAppVersions = appVersionService.getAppVersions();
            System.out.println("All App versions:");
            for (AppVersion appVersion : allAppVersions) {
                System.out.println(appVersion.getAppName() + " - " + appVersion.getAppVersion());
            }

            // Update Operation: Update two appVersions

            allAppVersions.get(0).setAppName(allAppVersions.get(0).getAppName() + 5000);
            appVersionService.updateAppVersion(allAppVersions.get(0));
            allAppVersions.get(1).setAppName(allAppVersions.get(1).getAppVersion() + 5000);
            appVersionService.updateAppVersion(allAppVersions.get(1));


            // Read Operation: Print updated app versions
            List<AppVersion> updatedAppVersions = appVersionService.getAppVersions();
            System.out.println("\nUpdated AppVersions:");
            for (AppVersion appVersion : updatedAppVersions) {
                System.out.println(appVersion.getAppName() + " - " + appVersion.getAppVersion());
            }

            // Delete Operation
            for (AppVersion appVersion : updatedAppVersions) {
                appVersionService.deleteAppVersion(appVersion.getId());
            }
        } finally {
        }
    }
}
