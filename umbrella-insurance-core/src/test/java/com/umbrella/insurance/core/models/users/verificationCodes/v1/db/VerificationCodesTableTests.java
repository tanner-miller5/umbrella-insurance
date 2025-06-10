package com.umbrella.insurance.core.models.users.verificationCodes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerificationCodesTableTests {
    private static String verificationCodeText = "3";
    private static Boolean isVerified = true;
    private static Timestamp expirationDateTime = Timestamp.valueOf("1111-11-11 11:11:11");
    private static Timestamp verifiedDateTime = Timestamp.valueOf("2222-11-11 22:22:22");
    private static Long minutesToVerify = 3l;
    private static Long maxAttempts = 4l;
    private static Long currentAttempt = 5l;
    private static String updatedVerificationCodeText = "10";
    private static Boolean updatedIsVerified = false;
    private static Timestamp updatedExpirationDateTime = Timestamp.valueOf("3333-11-11 11:11:11");
    private static Timestamp updatedVerifiedDateTime = Timestamp.valueOf("4444-11-11 11:11:11");
    private static Long updatedMinutesToVerify = 11l;
    private static Long updatedMaxAttempts = 12l;
    private static Long updatedCurrentAttempt = 13l;
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = true;
    private static Boolean isAuthAppVerified = false;
    private static VerificationMethod verificationMethod = new VerificationMethod();
    private static Person person = new Person();
    private static User user = new User();
    private static Device device = new Device();
    private static VerificationCode verificationCode = new VerificationCode();
    private static VerificationCode updatedVerificationCode = new VerificationCode();
    static {

        verificationMethod.setVerificationMethodName("t");

        device.setUserAgent("1");
        device.setCreatedDateTime(Timestamp.valueOf("1111-11-11 11:11:11"));
        device.setIpAddress("234.33.33.33");
        device.setDeviceName("test");

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

        verificationCode.setVerificationCode(verificationCodeText);
        verificationCode.setIsVerified(isVerified);
        verificationCode.setExpirationDateTime(expirationDateTime);
        verificationCode.setVerifiedDateTime(verifiedDateTime);
        verificationCode.setMinutesToVerify(minutesToVerify);
        verificationCode.setMaxAttempts(maxAttempts);
        verificationCode.setCurrentAttempt(currentAttempt);

        updatedVerificationCode.setVerificationCode(updatedVerificationCodeText);
        updatedVerificationCode.setIsVerified(updatedIsVerified);
        updatedVerificationCode.setExpirationDateTime(updatedExpirationDateTime);
        updatedVerificationCode.setVerifiedDateTime(updatedVerifiedDateTime);
        updatedVerificationCode.setMinutesToVerify(updatedMinutesToVerify);
        updatedVerificationCode.setMaxAttempts(updatedMaxAttempts);
        updatedVerificationCode.setCurrentAttempt(updatedCurrentAttempt);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private DeviceService deviceService;

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
    VerificationCodeService verificationCodeService;

    @Autowired
    PersonService personService;

    @Autowired
    UserService userService;

    @Autowired
    VerificationMethodService verificationMethodService;

    @Test
    @Order(2)
    void insertVerificationCodeTest() throws SQLException {
        verificationMethod = verificationMethodService.saveVerificationMethod(verificationMethod);
        verificationCode.setVerificationMethod(verificationMethod);
        updatedVerificationCode.setVerificationMethod(verificationMethod);

        person = personService.savePerson(person);
        user.setPerson(person);

        device = deviceService.saveDevice(device);

        user = userService.saveUser(user);

        verificationCode.setUser(user);
        updatedVerificationCode.setUser(user);

        verificationCode = verificationCodeService.saveVerificationCode(verificationCode);
        updatedVerificationCode.setId(verificationCode.getId());
    }
    @Test
    @Order(3)
    void selectLatestVerificationCodeByUserIdTest() throws SQLException {
        VerificationCode verificationCode1 =
                verificationCodeService.getVerificationCodeByUserId(
                        user.getId()).get();
        assertTrue(verificationCode1.getVerificationMethod().getId().equals(
                verificationMethod.getId()));
        assertTrue(verificationCode1.getVerificationCode().equals(verificationCode.getVerificationCode()));
        assertTrue(verificationCode1.getIsVerified().equals(isVerified));
        assertTrue(verificationCode1.getExpirationDateTime().equals(expirationDateTime));
        assertTrue(verificationCode1.getVerifiedDateTime().equals(verifiedDateTime));
        assertTrue(verificationCode1.getMinutesToVerify().equals(minutesToVerify));
        assertTrue(verificationCode1.getMaxAttempts().equals(maxAttempts));
        assertTrue(verificationCode1.getCurrentAttempt().equals(currentAttempt));
    }
    @Test
    @Order(4)
    void updateLatestVerificationCodeByUserIdTest() throws SQLException {
        updatedVerificationCode = verificationCodeService.updateVerificationCode(
                updatedVerificationCode);
    }
    @Test
    @Order(5)
    void selectLatestUpdatedVerificationCodeByUserIdTest() throws SQLException {
        VerificationCode verificationCode1 =
                verificationCodeService.getVerificationCodeByUserId(
                        user.getId()).get();
        assertTrue(verificationCode1.getUser().getId().equals(user.getId()));
        assertTrue(verificationCode1.getVerificationMethod().getId().equals(
                verificationMethod.getId()));
        assertTrue(verificationCode1.getVerificationCode().equals(updatedVerificationCode.getVerificationCode()));
        assertTrue(verificationCode1.getIsVerified().equals(updatedIsVerified));
        assertTrue(verificationCode1.getExpirationDateTime().equals(updatedExpirationDateTime));
        assertTrue(verificationCode1.getVerifiedDateTime().equals(updatedVerifiedDateTime));
        assertTrue(verificationCode1.getMinutesToVerify().equals(updatedMinutesToVerify));
        assertTrue(verificationCode1.getMaxAttempts().equals(updatedMaxAttempts));
        assertTrue(verificationCode1.getCurrentAttempt().equals(updatedCurrentAttempt));
    }
    @Test
    @Order(6)
    void deleteLatestVerificationCodeByUserId() throws SQLException {
        verificationCodeService.deleteVerificationCode(
                verificationCode.getId());

        verificationMethodService.deleteVerificationMethod(
                verificationMethod.getId());

        deviceService.deleteDevice(
                device.getId());

        userService.deleteUser(
                user.getId());

        personService.deletePerson(
                person.getId());
    }

}
