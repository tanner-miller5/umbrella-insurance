package com.umbrella.insurance.core.models.communications.sentTexts.v1.db;

import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa.SentTextService;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.SentText;
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
public class SentTextsTableTests {
    private static String recipientPhoneNumber = "1";
    private static String senderPhoneNumber = "1";
    private static String textMessage = "1";
    private static Timestamp sentDateTime = Timestamp.valueOf("2023-12-12 12:00:00");

    private static String updatedRecipientPhoneNumber = "2";
    private static String updatedSenderPhoneNumber = "2";
    private static String updatedTextMessage = "2";
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
    private static SentText sentText = new SentText();
    private static SentText updatedSentText = new SentText();
    static {
        person.setSsn("123a");
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

        sentText.setRecipientPhoneNumber(recipientPhoneNumber);
        sentText.setSenderPhoneNumber(senderPhoneNumber);
        sentText.setTextMessage(textMessage);
        sentText.setSentDateTime(sentDateTime);

        //
        updatedSentText.setRecipientPhoneNumber(updatedRecipientPhoneNumber);
        updatedSentText.setSenderPhoneNumber(updatedSenderPhoneNumber);
        updatedSentText.setTextMessage(updatedTextMessage);
        updatedSentText.setSentDateTime(updatedSentDateTime);

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
    PersonService personService;

    @Autowired
    UserService userService;

    @Autowired
    SentTextService sentTextService;

    @Test
    @Order(3)
    void insertSentTextTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        sentText.setUser(user);
        updatedSentText.setUser(user);

        sentText = sentTextService.saveSentText(sentText);
        updatedSentText.setId(sentText.getId());

    }
    @Test
    @Order(4)
    void selectSentTextByTextMessageTest() throws SQLException {
        SentText sentText1 = sentTextService.getSentTextById(sentText.getId()).get();
        assertTrue(sentText1.getRecipientPhoneNumber().equals(recipientPhoneNumber));
        assertTrue(sentText1.getSenderPhoneNumber().equals(senderPhoneNumber));
        assertTrue(sentText1.getSentDateTime().equals(sentDateTime));
        assertTrue(sentText1.getTextMessage().equals(textMessage));
        assertTrue(sentText1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(5)
    void updateSentTextByTextMessageTest() throws SQLException {
        updatedSentText = sentTextService.updateSentText(updatedSentText);
    }
    @Test
    @Order(6)
    void selectUpdatedSentTextByTextMessageTest() throws SQLException {
        SentText sentText1 = sentTextService.getSentTextById(updatedSentText.getId()).get();
        assertTrue(sentText1.getRecipientPhoneNumber().equals(updatedRecipientPhoneNumber));
        assertTrue(sentText1.getSenderPhoneNumber().equals(updatedSenderPhoneNumber));
        assertTrue(sentText1.getSentDateTime().equals(updatedSentDateTime));
        assertTrue(sentText1.getTextMessage().equals(updatedTextMessage));
        assertTrue(sentText1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(7)
    void deleteSentTextBySentTextNameTest() throws SQLException {
        sentTextService.deleteSentText(updatedSentText.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
