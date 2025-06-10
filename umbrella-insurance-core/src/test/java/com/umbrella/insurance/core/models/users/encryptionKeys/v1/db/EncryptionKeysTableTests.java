package com.umbrella.insurance.core.models.users.encryptionKeys.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.EncryptionKey;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.jpa.EncryptionKeyService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EncryptionKeysTableTests {
    private static String encryptionKeyPrivateKey = "1";
    private static String encryptionKeyPublicKey = "2";
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-10-09 10:10:01");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String updatedEncryptionKeyPrivateKey = "3";
    private static String updatedEncryptionKeyPublicKey = "4";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2022-10-09 10:10:01");
    private static Person person = new Person();
    private static User user = new User();
    private static EncryptionKey encryptionKey = new EncryptionKey();
    private static EncryptionKey updatedEncryptionKey = new EncryptionKey();
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

        encryptionKey.setEncryptionKeyPublicKey(encryptionKeyPublicKey);
        encryptionKey.setEncryptionKeyPrivateKey(encryptionKeyPrivateKey);
        encryptionKey.setCreatedDateTime(createdDateTime);

        updatedEncryptionKey.setEncryptionKeyPublicKey(updatedEncryptionKeyPublicKey);
        updatedEncryptionKey.setEncryptionKeyPrivateKey(updatedEncryptionKeyPrivateKey);
        updatedEncryptionKey.setCreatedDateTime(updatedCreatedDateTime);
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
    private EncryptionKeyService encryptionKeyService;

    @Test
    @Order(2)
    void insertEncryptionKeyTest() throws SQLException {
        personService.savePerson(person);
        user.setPerson(person);

        userService.saveUser(user);
        encryptionKey.setUser(user);
        updatedEncryptionKey.setUser(user);

        encryptionKey = encryptionKeyService.saveEncryptionKey(encryptionKey);
        updatedEncryptionKey.setId(encryptionKey.getId());
    }
    @Test
    @Order(3)
    void selectLatestEncryptionKeyByUserIdTest() throws SQLException {
        EncryptionKey encryptionKey1 =
                encryptionKeyService.getEncryptionKeyByUserId(
                        user.getId()).get();
        assertTrue(encryptionKey1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(encryptionKey1.getUser().getId().equals(user.getId()));
        assertTrue(encryptionKey1.getEncryptionKeyPrivateKey().equals(encryptionKeyPrivateKey));
        assertTrue(encryptionKey1.getEncryptionKeyPublicKey().equals(encryptionKeyPublicKey));
    }
    @Test
    @Order(4)
    void updateLatestEncryptionKeyByUserIdTest() throws SQLException {
        updatedEncryptionKey = encryptionKeyService.updateEncryptionKey(
                        updatedEncryptionKey);
    }
    @Test
    @Order(5)
    void selectUpdatedLatestEncryptionKeyByUserIdTest() throws SQLException {
        EncryptionKey encryptionKey1 =
                encryptionKeyService.getEncryptionKeyByUserId(
                        user.getId()).get();
        assertTrue(encryptionKey1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(encryptionKey1.getUser().getId().equals(user.getId()));
        assertTrue(encryptionKey1.getEncryptionKeyPrivateKey().equals(updatedEncryptionKeyPrivateKey));
        assertTrue(encryptionKey1.getEncryptionKeyPublicKey().equals(updatedEncryptionKeyPublicKey));
    }
    @Test
    @Order(6)
    void deleteLatestEncryptionKeyByUserIdTest() throws SQLException {
        encryptionKeyService.deleteEncryptionKey(
                        encryptionKey.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }

}
