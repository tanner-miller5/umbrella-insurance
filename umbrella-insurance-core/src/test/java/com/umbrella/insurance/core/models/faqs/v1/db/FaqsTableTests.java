package com.umbrella.insurance.core.models.faqs.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;

import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.Device;
import com.umbrella.insurance.core.models.entities.Faq;
import com.umbrella.insurance.core.models.entities.Session;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;

import com.umbrella.insurance.core.models.faqs.v1.db.jpa.FaqService;
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
public class FaqsTableTests {
    private static String faqName = "1234";
    private static String question = "123";
    private static String answer = "123";
    private static Timestamp faqCreateDateTime = Timestamp.valueOf("2023-05-04 12:00:00");
    private static String updatedFaqName = "12345";
    private static String updatedQuestion = "abc";
    private static String updatedAnswer = "abc";
    private static Timestamp updatedFaqCreateDateTime = Timestamp.valueOf("2023-05-04 13:00:00");
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
    private static Faq faq = new Faq();
    private static Faq updatedFaq = new Faq();
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

        faq.setQuestion(question);
        faq.setAnswer(answer);
        faq.setFaqName(faqName);
        faq.setCreatedDateTime(faqCreateDateTime);
        updatedFaq.setQuestion(updatedQuestion);
        updatedFaq.setAnswer(updatedAnswer);
        updatedFaq.setFaqName(updatedFaqName);
        updatedFaq.setCreatedDateTime(updatedFaqCreateDateTime);
    }

    @Autowired
    PersonService personService;

    @Autowired
    UserService userService;

    @Autowired
    DeviceService deviceService;

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private FaqService faqService;

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
    void insertFaqTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);

        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        faq.setSession(session);
        updatedFaq.setSession(session);

        faq = faqService.saveFaq(faq);
        updatedFaq.setId(faq.getId());
    }
    @Test
    @Order(4)
    void selectFaqByFaqNameTest() throws SQLException {
        Faq faq1 = faqService.getFaqByName(faqName).get();
        assertTrue(faq1.getFaqName().equals(faqName));
        assertTrue(faq1.getQuestion().equals(question));
        assertTrue(faq1.getAnswer().equals(answer));
        assertTrue(faq1.getCreatedDateTime().equals(faqCreateDateTime));
        assertTrue(faq1.getSession().getId().equals(session.getId()));
    }
    @Test
    @Order(5)
    void updateFaqByFaqNameTest() throws SQLException {
        updatedFaq = faqService.updateFaq(updatedFaq);
    }
    @Test
    @Order(6)
    void selectUpdatedFaqByFaqNameTest() throws SQLException {
        Faq faq1 = faqService.getFaqByName(updatedFaqName).get();
        assertTrue(faq1.getFaqName().equals(updatedFaqName));
        assertTrue(faq1.getQuestion().equals(updatedQuestion));
        assertTrue(faq1.getAnswer().equals(updatedAnswer));
        assertTrue(faq1.getCreatedDateTime().equals(updatedFaqCreateDateTime));
        assertTrue(faq1.getSession().getId().equals(session.getId()));
    }
    @Test
    @Order(7)
    void deleteFaqByFaqNameTest() throws SQLException {
        faqService.deleteFaq(updatedFaq.getId());

        sessionService.deleteSession(
                session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());

    }

}
