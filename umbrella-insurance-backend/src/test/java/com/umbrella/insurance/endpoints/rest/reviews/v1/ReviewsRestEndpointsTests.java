package com.umbrella.insurance.endpoints.rest.reviews.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Review;
import com.umbrella.insurance.core.models.entities.Session;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.reviews.v1.db.ReviewPrivilege;
import com.umbrella.insurance.core.models.reviews.v1.db.jpa.ReviewService;
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

import com.umbrella.insurance.core.models.entities.*;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewsRestEndpointsTests {

    @Autowired
    private ReviewsRestEndpoints reviewsRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(reviewsRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static Long reviewId;
    private static String subject = "1234";
    private static String comment = "123";
    private static Integer rating = 123;
    private static Timestamp reviewCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String frontendAppVersion = "1";
    private static String backendAppVersion = "2";
    private static String updatedSubject = "12345";
    private static String updatedComment = "abc";
    private static Integer updatedRating = 1234;
    private static Timestamp updatedReviewCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
    private static String updatedFrontendAppVersion = "3";
    private static String updatedBackendAppVersion = "4";

    private static Review review = new Review();
    private static Review updatedReview = new Review();

    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    private static User user;

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

        review.setComment(comment);
        review.setRating(rating);
        review.setSubject(subject);
        review.setCreatedDateTime(reviewCreateDateTime);
        review.setBackendAppVersion(backendAppVersion);
        review.setFrontendAppVersion(frontendAppVersion);
        updatedReview.setComment(updatedComment);
        updatedReview.setRating(updatedRating);
        updatedReview.setSubject(updatedSubject);
        updatedReview.setCreatedDateTime(updatedReviewCreateDateTime);
        updatedReview.setBackendAppVersion(updatedBackendAppVersion);
        updatedReview.setFrontendAppVersion(updatedFrontendAppVersion);
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
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PersonService personService;

    @Test
    @Order(1)
    void createReview() throws Exception {
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
        review.setSession(session);
        updatedReview.setSession(session);
        user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(userApplicationRoleRelationship);
        review.setUser(user);
        updatedReview.setUser(user);

        headers.set("session", session.getSessionCode());
        HttpEntity<Review> request = new HttpEntity<>(review, headers);

        String url = hostname + port
                + ReviewPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Review> review1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Review.class);

        assertTrue(review1.getStatusCode().is2xxSuccessful());
        assertTrue(review1.getBody().getSubject().equals(subject));
        assertTrue(review1.getBody().getComment().equals(comment));
        assertTrue(review1.getBody().getRating().equals(rating));
        assertTrue(review1.getBody().getCreatedDateTime().equals(reviewCreateDateTime));
        assertTrue(review1.getBody().getSession().getId().equals(session.getId()));
        assertTrue(review1.getBody().getFrontendAppVersion().equals(frontendAppVersion));
        assertTrue(review1.getBody().getBackendAppVersion().equals(backendAppVersion));
        assertTrue(review1.getBody().getUser().getId().equals(user.getId()));
        reviewId = review1.getBody().getId();
        updatedReview.setId(reviewId);
    }

    @Test
    @Order(2)
    void readReviewByUserIdAndFrontendAppVersionAndBackendAppVersion() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + ReviewPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&userId=" + user.getId()
                + "&frontendAppVersion=" + frontendAppVersion
                + "&backendAppVersion=" + backendAppVersion;
        ResponseEntity<Review[]> reviewList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Review[].class);
        assertTrue(reviewList.getStatusCode().is2xxSuccessful());
        Review review1 = reviewList.getBody()[0];
        assertTrue(review1.getSubject().equals(subject));
        assertTrue(review1.getComment().equals(comment));
        assertTrue(review1.getRating().equals(rating));
        assertTrue(review1.getCreatedDateTime().equals(reviewCreateDateTime));
        assertTrue(review1.getSession().getId().equals(session.getId()));
        assertTrue(review1.getFrontendAppVersion().equals(frontendAppVersion));
        assertTrue(review1.getBackendAppVersion().equals(backendAppVersion));
        assertTrue(review1.getUser().getId().equals(user.getId()));
    }

    @Test
    @Order(3)
    void readReviewByReviewId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port + ReviewPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&reviewId=" + reviewId;

        ResponseEntity<Review[]> reviewList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Review[].class);
        assertTrue(reviewList.getStatusCode().is2xxSuccessful());
        Review review1 = reviewList.getBody()[0];
        assertTrue(review1.getSubject().equals(subject));
        assertTrue(review1.getComment().equals(comment));
        assertTrue(review1.getRating().equals(rating));
        assertTrue(review1.getCreatedDateTime().equals(reviewCreateDateTime));
        assertTrue(review1.getSession().getId().equals(session.getId()));
        assertTrue(review1.getFrontendAppVersion().equals(frontendAppVersion));
        assertTrue(review1.getBackendAppVersion().equals(backendAppVersion));
        assertTrue(review1.getUser().getId().equals(user.getId()));
    }

    @Test
    @Order(4)
    void updateReviewByReviewId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Review> request = new HttpEntity<>(updatedReview, headers);
        String url = hostname + port
                + ReviewPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&reviewId=" + reviewId;
        URI uri = new URI(url);
        ResponseEntity<Review[]> reviewList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Review[].class);
        assertTrue(reviewList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedReviewByReviewId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Review[]> reviewList = this.restTemplate.exchange(
                hostname + port
                        + ReviewPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&reviewId=" + reviewId, HttpMethod.GET, request, Review[].class);
        assertTrue(reviewList.getStatusCode().is2xxSuccessful());
        Review review1 = reviewList.getBody()[0];
        assertTrue(review1.getSubject().equals(updatedSubject));
        assertTrue(review1.getComment().equals(updatedComment));
        assertTrue(review1.getRating().equals(updatedRating));
        assertTrue(review1.getCreatedDateTime().equals(updatedReviewCreateDateTime));
        assertTrue(review1.getSession().getId().equals(session.getId()));
        assertTrue(review1.getFrontendAppVersion().equals(updatedFrontendAppVersion));
        assertTrue(review1.getBackendAppVersion().equals(updatedBackendAppVersion));
        assertTrue(review1.getUser().getId().equals(user.getId()));
        reviewId = review1.getId();
    }

    @Test
    @Order(6)
    void deleteReviewByReviewId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + ReviewPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&reviewId=" + reviewId;
        URI uri = new URI(url);
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
