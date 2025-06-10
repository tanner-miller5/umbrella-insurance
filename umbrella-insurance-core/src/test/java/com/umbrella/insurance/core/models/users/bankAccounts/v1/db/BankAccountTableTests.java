package com.umbrella.insurance.core.models.users.bankAccounts.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.BankAccount;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.users.bankAccounts.v1.db.jpa.BankAccountService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-12-12 12:00:00");
    private static String accountNumber = "1";
    private static String routingNumber = "2";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2020-12-12 11:00:00");
    private static String updatedAccountNumber = "3";
    private static String updatedRoutingNumber = "4";
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static Person person = new Person();
    private static User user = new User();
    private static BankAccount bankAccount = new BankAccount();
    private static BankAccount updatedBankAccount = new BankAccount();
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

        bankAccount.setCreatedDateTime(createdDateTime);
        bankAccount.setRoutingNumber(routingNumber);
        bankAccount.setAccountNumber(accountNumber);

        updatedBankAccount.setCreatedDateTime(updatedCreatedDateTime);
        updatedBankAccount.setRoutingNumber(updatedRoutingNumber);
        updatedBankAccount.setAccountNumber(updatedAccountNumber);

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
    private BankAccountService bankAccountService;

    @Test
    @Order(2)
    void insertBankAccountTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        bankAccount.setUser(user);
        updatedBankAccount.setUser(user);

        bankAccount = bankAccountService.saveBankAccount(bankAccount);
        updatedBankAccount.setId(bankAccount.getId());
    }
    @Test
    @Order(3)
    void selectBankAccountByCustomerUserIdTest() throws SQLException {
        BankAccount bankAccount1 = bankAccountService.getBankAccountByUserId(
                bankAccount.getUser().getId()).get();
        assertTrue(bankAccount1.getAccountNumber().equals(accountNumber));
        assertTrue(bankAccount1.getRoutingNumber().equals(routingNumber));
        assertTrue(bankAccount1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(bankAccount1.getUser().getId().equals(user.getId()));
    }
    @Test
    @Order(4)
    void updateBankAccountByCustomerUserIdTest() throws SQLException {
        updatedBankAccount = bankAccountService.updateBankAccount(
                updatedBankAccount);
    }
    @Test
    @Order(5)
    void selectUpdatedBankAccountByUserId() throws SQLException {
        BankAccount bankAccount1 = bankAccountService.getBankAccountByUserId(
                updatedBankAccount.getUser().getId()).get();
        assertTrue(bankAccount1.getAccountNumber().equals(updatedAccountNumber));
        assertTrue(bankAccount1.getRoutingNumber().equals(updatedRoutingNumber));
        assertTrue(bankAccount1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(bankAccount1.getUser().getId().equals(updatedBankAccount.getUser().getId()));
    }
    @Test
    @Order(6)
    void deleteBankAccountByUserIdTest() throws SQLException {
        bankAccountService.deleteBankAccount(
                updatedBankAccount.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());
    }
}
