package com.umbrella.insurance.core.models.communications.sentEmails.v1.db;

import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa.SentEmailService;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.SentEmail;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SentEmailsTableTests {
    private static String recipientEmailAddress = "1";
    private static String senderEmailAddress = "2";
    private static String contentType = "3";
    private static String emailSubject = "4";
    private static String emailBody = "5";
    private static Timestamp sentDateTime = Timestamp.valueOf("2023-12-12 12:00:00");

    private static String updatedRecipientEmailAddress = "22";
    private static String updatedSenderEmailAddress = "33";
    private static String updatedContentType = "44";
    private static String updatedEmailSubject = "55";
    private static String updatedEmailBody = "66";
    private static Timestamp updatedSentDateTime = Timestamp.valueOf("2024-11-11 11:00:00");
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static SentEmail sentEmail = new SentEmail();
    private static SentEmail updatedSentEmail = new SentEmail();
    static {
        person.setSsn("123b");
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

        sentEmail.setRecipientEmailAddress(recipientEmailAddress);
        sentEmail.setSenderEmailAddress(senderEmailAddress);
        sentEmail.setContentType(contentType);
        sentEmail.setEmailSubject(emailSubject);
        sentEmail.setEmailBody(emailBody);
        sentEmail.setSentDateTime(sentDateTime);

        //
        updatedSentEmail.setRecipientEmailAddress(updatedRecipientEmailAddress);
        updatedSentEmail.setSenderEmailAddress(updatedSenderEmailAddress);
        updatedSentEmail.setContentType(updatedContentType);
        updatedSentEmail.setEmailSubject(updatedEmailSubject);
        updatedSentEmail.setEmailBody(updatedEmailBody);
        updatedSentEmail.setSentDateTime(updatedSentDateTime);

    }

    @Autowired
    SentEmailService sentEmailService;

    @Autowired
    PersonService personService;
    @Autowired
    UserService userService;

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
    void insertSentEmailTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        sentEmail.setUser(user);
        updatedSentEmail.setUser(user);

        sentEmail = sentEmailService.saveSentEmail(sentEmail);
        updatedSentEmail.setId(sentEmail.getId());
    }
    @Test
    @Order(4)
    void selectSentEmailByEmailSubjectTest() throws SQLException {
        SentEmail sentEmail1 = sentEmailService.getSentEmailById(sentEmail.getId()).get();
        assertTrue(sentEmail1.getEmailSubject().equals(emailSubject));
        assertTrue(sentEmail1.getContentType().equals(contentType));
        assertTrue(sentEmail1.getEmailBody().equals(emailBody));
        assertTrue(sentEmail1.getRecipientEmailAddress().equals(recipientEmailAddress));
        assertTrue(sentEmail1.getSenderEmailAddress().equals(senderEmailAddress));
        assertTrue(sentEmail1.getSentDateTime().equals(sentDateTime));
        assertTrue(sentEmail1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(5)
    void updateSentEmailByEmailSubjectTest() throws SQLException {
        updatedSentEmail = sentEmailService.updateSentEmail(updatedSentEmail);
    }
    @Test
    @Order(6)
    void selectUpdatedSentEmailByEmailSubject() throws SQLException {
        SentEmail sentEmail1 = sentEmailService.getSentEmailById(updatedSentEmail.getId()).get();
        assertTrue(sentEmail1.getEmailSubject().equals(updatedEmailSubject));
        assertTrue(sentEmail1.getContentType().equals(updatedContentType));
        assertTrue(sentEmail1.getEmailBody().equals(updatedEmailBody));
        assertTrue(sentEmail1.getRecipientEmailAddress().equals(updatedRecipientEmailAddress));
        assertTrue(sentEmail1.getSenderEmailAddress().equals(updatedSenderEmailAddress));
        assertTrue(sentEmail1.getSentDateTime().equals(updatedSentDateTime));
        //assertTrue(sentEmail1.getUserId().equals(user.getVerificationMethodId()));
    }
    @Test
    @Order(7)
    void deleteSentEmailByEmailSubjectTest() throws SQLException {
        sentEmailService.deleteSentEmail(updatedSentEmail.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
