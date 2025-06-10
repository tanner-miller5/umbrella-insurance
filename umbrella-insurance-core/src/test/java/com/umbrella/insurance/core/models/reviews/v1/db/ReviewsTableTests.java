package com.umbrella.insurance.core.models.reviews.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.reviews.v1.db.jpa.ReviewService;
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
public class ReviewsTableTests {
    private static String subject = "1234";
    private static String frontendAppVersion = "1";
    private static String backendAppVersion = "2";
    private static String comment = "123";
    private static Integer rating = 123;
    private static Timestamp reviewCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String updatedSubject = "12345";
    private static String updatedFrontendAppVersion = "3";
    private static String updatedBackendAppVersion = "4";
    private static String updatedComment = "abc";
    private static Integer updatedRating = 123;
    private static Timestamp updatedReviewCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
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
    private static Review review = new Review();
    private static Review updatedReview = new Review();
    static {
        person.setSsn("123456");
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

        review.setSubject(subject);
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedDateTime(reviewCreateDateTime);
        review.setFrontendAppVersion(frontendAppVersion);
        review.setBackendAppVersion(backendAppVersion);
        updatedReview.setSubject(updatedSubject);
        updatedReview.setComment(updatedComment);
        updatedReview.setRating(updatedRating);
        updatedReview.setCreatedDateTime(updatedReviewCreateDateTime);
        updatedReview.setFrontendAppVersion(updatedFrontendAppVersion);
        updatedReview.setBackendAppVersion(updatedBackendAppVersion);
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
    private ReviewService reviewService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;


    @Test
    @Order(3)
    void insertReviewTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);
        review.setUser(user);
        updatedReview.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        review.setSession(session);
        updatedReview.setSession(session);

        review = reviewService.saveReview(review);
        updatedReview.setId(review.getId());
    }
    @Test
    @Order(4)
    void selectReviewByUserIdAndFrontendAppVersionAndBackendAppVersionTest() throws SQLException {
        Review review1 = reviewService.getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
                user.getId(), frontendAppVersion, backendAppVersion).get();
        assertTrue(review1.getSubject().equals(subject));
        assertTrue(review1.getComment().equals(comment));
        assertTrue(review1.getRating().equals(rating));
        assertTrue(review1.getCreatedDateTime().equals(reviewCreateDateTime));
        assertTrue(review1.getSession().getId().equals(session.getId()));
        assertTrue(review1.getBackendAppVersion().equals(backendAppVersion));
        assertTrue(review1.getFrontendAppVersion().equals(frontendAppVersion));
        assertTrue(review1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(5)
    void updateReviewByUsernameAndFrontendAppVersionAndBackendAppVersionTest() throws SQLException {
        updatedReview = reviewService.updateReview(
                updatedReview);
    }
    @Test
    @Order(6)
    void selectUpdatedReviewByUserIdAndFrontendAppVersionAndBackendAppVersionTest() throws SQLException {
        Review review1 = reviewService.getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
                user.getId(), updatedFrontendAppVersion, updatedBackendAppVersion).get();
        assertTrue(review1.getSubject().equals(updatedSubject));
        assertTrue(review1.getComment().equals(updatedComment));
        assertTrue(review1.getRating().equals(updatedRating));
        assertTrue(review1.getCreatedDateTime().equals(updatedReviewCreateDateTime));
        assertTrue(review1.getSession().getId().equals(session.getId()));
        assertTrue(review1.getBackendAppVersion().equals(updatedBackendAppVersion));
        assertTrue(review1.getFrontendAppVersion().equals(updatedFrontendAppVersion));
        assertTrue(review1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(7)
    void deleteReviewByUserIdAndFrontendAppVersionAndBackendAppVersionTest() throws SQLException {
        reviewService.deleteReview(
                updatedReview.getId());

        sessionService.deleteSession(session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
