package com.umbrella.insurance.core.models.announcements.v1.db;

import com.umbrella.insurance.core.models.announcements.v1.db.jpa.AnnouncementService;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnouncementsTableTests {
    private static String announcementName = "1234";
    private static String message = "123";
    private static Timestamp announcementCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String updatedAnnouncementName = "12345";
    private static String updatedMessage = "abc";
    private static Timestamp updatedAnnouncementCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
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
    private static Long minutesToExpire = 2L;
    private static Person person = new Person();
    private static User user = new User();
    private static Device device = new Device();
    private static Session session = new Session();
    private static Announcement announcement = new Announcement();
    private static Announcement updatedAnnouncement = new Announcement();
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

        announcement.setMessage(message);
        announcement.setAnnouncementName(announcementName);
        announcement.setCreatedDateTime(announcementCreateDateTime);
        updatedAnnouncement.setMessage(updatedMessage);
        updatedAnnouncement.setAnnouncementName(updatedAnnouncementName);
        updatedAnnouncement.setCreatedDateTime(updatedAnnouncementCreateDateTime);
    }

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    PersonService personService;

    @Autowired
    UserService userService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    SessionService sessionService;

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
    void insertAnnouncementTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        announcement.setSession(session);
        updatedAnnouncement.setSession(session);

        announcement = announcementService.saveAnnouncement(announcement);
        updatedAnnouncement.setId(announcement.getId());
    }
    @Test
    @Order(4)
    void selectAnnouncementByAnnouncementNameTest() throws SQLException {
        Announcement announcement1 = announcementService.getAnnouncementByName(announcementName).get();
        assertTrue(announcement1.getAnnouncementName().equals(announcementName));
        assertTrue(announcement1.getMessage().equals(message));
        assertTrue(announcement1.getCreatedDateTime().equals(announcementCreateDateTime));
        assertTrue(announcement1.getSession().getId().equals(session.getId()));
    }
    @Test
    @Order(5)
    void updateAnnouncementByAnnouncementNameTest() throws SQLException {
        updatedAnnouncement = announcementService.updateAnnouncement(updatedAnnouncement);
    }
    @Test
    @Order(6)
    void selectUpdatedAnnouncementByAnnouncementNameTest() throws SQLException {
        Announcement announcement1 = announcementService.getAnnouncementByName(updatedAnnouncementName).get();
        assertTrue(announcement1.getAnnouncementName().equals(updatedAnnouncementName));
        assertTrue(announcement1.getMessage().equals(updatedMessage));
        assertTrue(announcement1.getCreatedDateTime().equals(updatedAnnouncementCreateDateTime));
        assertTrue(announcement1.getSession().getId().equals(session.getId()));
    }
    @Test
    @Order(7)
    void deleteAnnouncementByAnnouncementNameTest() throws SQLException {
        announcementService.deleteAnnouncement(updatedAnnouncement.getId());

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
            List<Long> announcementIds = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Announcement announcement = new Announcement();
                announcement.setAnnouncementName("Announcement " + i); // Setting unique names for each announcement
                announcement.setMessage("" + 50000 + (i * 1000)); //  announcement_data for each announcement
                announcement.setCreatedDateTime(announcementCreateDateTime);
                announcement = announcementService.saveAnnouncement(announcement);
                announcementIds.add(announcement.getId());
                System.out.println(announcement.getAnnouncementName() + ":" + announcement.getId());
            }

            // Read Operation: Print all announcements
            List<Announcement> allAnnouncements = announcementService.getAnnouncements();
            System.out.println("All Announcements:");
            for (Announcement announcement : allAnnouncements) {
                System.out.println(announcement.getAnnouncementName() + " - " + announcement.getMessage());
            }

            Optional<Announcement> announcementByName = announcementService
                    .getAnnouncementByName("Announcement 1");
            System.out.println("announcementByName:" + announcementByName.get().getAnnouncementName());

            // Update Operation: Update two announcements
            for(Long id : announcementIds) {
                Optional<Announcement> announcement = announcementService.getAnnouncementById(id);
                announcement.get().setMessage(announcement.get().getMessage() + 5000);
                announcementService.updateAnnouncement(announcement.get());
            }



            // Read Operation: Print updated announcements
            List<Announcement> updatedAnnouncements = announcementService.getAnnouncementByIds(announcementIds);
            System.out.println("\nUpdated Announcements:");
            for (Announcement announcement : updatedAnnouncements) {
                System.out.println(announcement.getAnnouncementName() + " - " + announcement.getMessage());
            }

            // Delete Operation
            for (Announcement announcement : updatedAnnouncements) {
                announcementService.deleteAnnouncement(announcement.getId());
            }
        } finally {
        }
    }

}
