package com.umbrella.insurance.core.models.users.accountBalances.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.units.v1.UnitEnum;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa.AccountBalanceTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa.AccountBalanceService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountBalancesTableTests {
    private static Double accountBalanceValue = 1.0;
    private static Timestamp updatedDateTime = Timestamp.valueOf("2024-12-12 12:00:00");
    private static Double updatedAccountBalanceValue = 2.0;
    private static Timestamp updatedUpdatedDateTime = Timestamp.valueOf("2021-12-12 12:00:00");
    private static Timestamp createdDateTime = Timestamp.valueOf("2011-11-11 11:11:11");
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static AccountBalanceType accountBalanceType = new AccountBalanceType();
    private static AccountBalance accountBalance = new AccountBalance();
    private static AccountBalance updatedAccountBalance = new AccountBalance();
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

        accountBalanceType.setAccountBalanceTypeName("3333");

        accountBalance.setAccountBalanceValue(accountBalanceValue);
        accountBalance.setUpdatedDateTime(updatedDateTime);

        updatedAccountBalance.setAccountBalanceValue(updatedAccountBalanceValue);
        updatedAccountBalance.setUpdatedDateTime(updatedUpdatedDateTime);
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
    private AccountBalanceTypeService accountBalanceTypeService;

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Autowired
    private UnitService unitService;

    @Test
    @Order(2)
    void insertAccountBalanceTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        accountBalance.setUser(user);
        updatedAccountBalance.setUser(user);

        accountBalanceType = accountBalanceTypeService.saveAccountBalanceType(accountBalanceType);
        accountBalance.setAccountBalanceType(accountBalanceType);
        updatedAccountBalance.setAccountBalanceType(accountBalanceType);

        Unit unit = unitService.getUnitByUnitName(UnitEnum.butter_bucks.name()).get();
        accountBalance.setUnit(unit);
        updatedAccountBalance.setUnit(unit);

        accountBalance = accountBalanceService.saveAccountBalance(accountBalance);
        updatedAccountBalance.setId(accountBalance.getId());
    }
    @Test
    @Order(3)
    void selectAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitIdTest() throws SQLException {
        AccountBalance accountBalance1 = accountBalanceService
                .getAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                accountBalance.getUser().getId(), accountBalance.getAccountBalanceType().getId(),
                        accountBalance.getUnit().getId()).get();
        assertTrue(accountBalance1.getAccountBalanceValue().equals(accountBalanceValue));
        assertTrue(accountBalance1.getUser().getId().equals(user.getId()));
        assertTrue(accountBalance1.getUpdatedDateTime().equals(updatedDateTime));
        assertTrue(accountBalance1.getAccountBalanceType().getId().equals(accountBalanceType.getId()));
    }
    @Test
    @Order(4)
    void updateAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitIdTest() throws SQLException {
        updatedAccountBalance = accountBalanceService.updateAccountBalance(
                updatedAccountBalance);
    }
    @Test
    @Order(5)
    void selectUpdatedAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitIdTest() throws SQLException {
            AccountBalance accountBalance1 = accountBalanceService
                    .getAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                    updatedAccountBalance.getUser().getId(),
                    updatedAccountBalance.getAccountBalanceType().getId(),
                            accountBalance.getUnit().getId()).get();
            assertTrue(accountBalance1.getAccountBalanceValue().equals(updatedAccountBalanceValue));
            assertTrue(accountBalance1.getUser().getId().equals(user.getId()));
            assertTrue(accountBalance1.getUpdatedDateTime().equals(updatedUpdatedDateTime));
            assertTrue(accountBalance1.getAccountBalanceType().getId().equals(
                    updatedAccountBalance.getAccountBalanceType().getId()));
    }
    @Test
    @Order(6)
    void deleteAccountBalanceByCustomerUserIdAndAccountBalanceTypeIdAndUnitIdTest() throws SQLException {
        accountBalanceService.deleteAccountBalance(
                accountBalance.getId());

        accountBalanceTypeService.deleteAccountBalanceType(
                accountBalanceType.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
