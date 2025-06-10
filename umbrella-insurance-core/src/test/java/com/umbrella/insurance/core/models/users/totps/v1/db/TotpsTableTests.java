package com.umbrella.insurance.core.models.users.totps.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.Totp;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.totps.v1.db.jpa.TotpService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TotpsTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static Totp totp = new Totp();
    private static Totp updatedTotp = new Totp();
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

        totp.setTotpCode("1");
        totp.setCreatedDateTime(createdDateTime);

        updatedTotp.setTotpCode("2");
        updatedTotp.setCreatedDateTime(createdDateTime);
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
    private TotpService totpService;

    @Test
    @Order(2)
    void insertTOTPTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        totp.setUser(user);
        updatedTotp.setUser(user);

        totp = totpService.saveTotp(totp);
        updatedTotp.setId(totp.getId());
    }
    @Test
    @Order(3)
    void selectTOTPByUserIdTest() throws SQLException {
        Totp totp1 = totpService.getTotpByUserId(user.getId()).get();
        assertTrue(totp1.getUser().getId().equals(totp.getUser().getId()));
        assertTrue(totp1.getTotpCode().equals(totp.getTotpCode()));
    }
    @Test
    @Order(5)
    void updateTOTPByUserIdTest() throws SQLException {
        updatedTotp = totpService.updateTotp(
                updatedTotp);
    }
    @Test
    @Order(6)
    void selectUpdatedTOTPByUserIdTest() throws SQLException {
        Totp totp1 = totpService.getTotpByUserId(user.getId()).get();
        assertTrue(totp1.getUser().getId().equals(updatedTotp.getUser().getId()));
        assertTrue(totp1.getTotpCode().equals(updatedTotp.getTotpCode()));
    }
    @Test
    @Order(7)
    void deleteTOTPByUserIdTest() throws SQLException {
        totpService.deleteTotp(
                totp.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
