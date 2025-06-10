package com.umbrella.insurance.core.models.ads.v1.db;

import com.umbrella.insurance.core.models.ads.v1.db.jpa.AdService;
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
public class AdsTableTests {
    private static String adName = "1234";
    private static String adData = "123";
    private static Timestamp adCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String updatedAdName = "12345";
    private static String updatedData = "abc";
    private static Timestamp updatedAdCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
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
    private static Ad ad = new Ad();
    private static Ad updatedAd = new Ad();

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

        ad.setAdData(adData);
        ad.setAdName(adName);
        ad.setCreatedDateTime(adCreateDateTime);


        updatedAd.setAdData(updatedData);
        updatedAd.setAdName(updatedAdName);
        updatedAd.setCreatedDateTime(updatedAdCreateDateTime);
    }

    @Autowired
    AdService adService;

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
    void insertAdTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        ad.setUser(user);
        updatedAd.setUser(user);
        ad = adService.saveAd(ad);
        updatedAd.setId(ad.getId());
    }
    @Test
    @Order(4)
    void selectAdByAdNameTest() throws SQLException {
        Ad ad1 = adService.findByAdName(adName).get();
        assertTrue(ad1.getAdName().equals(adName));
        assertTrue(ad1.getAdData().equals(adData));
        assertTrue(ad1.getCreatedDateTime().equals(adCreateDateTime));
        assertTrue(ad1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(5)
    void updateAdByAdNameTest() throws SQLException {
        updatedAd = adService.updateAd(updatedAd);
    }
    @Test
    @Order(6)
    void selectUpdatedAdByAdNameTest() throws SQLException {
        Ad ad1 = adService.findByAdName(updatedAdName).get();
        assertTrue(ad1.getAdName().equals(updatedAdName));
        assertTrue(ad1.getAdData().equals(updatedData));
        assertTrue(ad1.getCreatedDateTime().equals(updatedAdCreateDateTime));
        assertTrue(ad1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(7)
    void deleteAdByAdNameTest() throws SQLException {
        adService.deleteByAdName(updatedAd.getAdName());

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
                Ad ad = new Ad();
                ad.setAdName("Ad " + i); // Setting unique names for each ad
                ad.setAdData("" + 50000 + (i * 1000)); //  ad_data for each ad
                ad.setCreatedDateTime(adCreateDateTime);
                adService.saveAd(ad);
            }

            // Read Operation: Print all ads
            List<Ad> allAds = adService.getAds();
            System.out.println("All Ads:");
            for (Ad ad : allAds) {
                System.out.println(ad.getAdName() + " - " + ad.getAdData());
            }

            // Update Operation: Update two ads

            allAds.get(0).setAdData(allAds.get(0).getAdData() + 5000);
            adService.updateAd(allAds.get(0));
            allAds.get(1).setAdData(allAds.get(1).getAdData() + 5000);
            adService.updateAd(allAds.get(1));


            // Read Operation: Print updated ads
            List<Ad> updatedAds = adService.getAds();
            System.out.println("\nUpdated Ads:");
            for (Ad ad : updatedAds) {
                System.out.println(ad.getAdName() + " - " + ad.getAdData());
            }

            // Delete Operation
            for (Ad ad : updatedAds) {
                adService.deleteByAdId(ad.getId());
            }
        } finally {
        }
    }
}
