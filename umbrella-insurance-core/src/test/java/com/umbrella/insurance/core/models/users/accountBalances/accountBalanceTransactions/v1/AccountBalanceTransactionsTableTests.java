package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.AccountBalanceTransaction;
import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionStatus;
import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionType;
import com.umbrella.insurance.core.models.entities.Unit;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.AccountBalanceTransactionStatusEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa.AccountBalanceTransactionStatusService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.AccountBalanceTransactionTypeEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa.AccountBalanceTransactionTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa.AccountBalanceTransactionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountBalanceTransactionsTableTests {
    private static String accountBalanceTransactionName = "1234";
    private static Double amount = 3.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("1111-11-11 11:11:11");
    private static String updatedAccountBalanceTransactionName = "12345";
    private static Double updatedAmount = 5.0;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1113-11-11 11:11:11");
    private static Unit unit = new Unit();
    private static AccountBalanceTransactionType accountBalanceTransactionType = new AccountBalanceTransactionType();
    private static AccountBalanceTransactionStatus accountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    private static AccountBalanceTransaction accountBalanceTransaction = new AccountBalanceTransaction();
    private static AccountBalanceTransaction updatedAccountBalanceTransaction = new AccountBalanceTransaction();
    static {
        unit.setUnitName("dollars");

        accountBalanceTransactionType.setAccountBalanceTransactionTypeName(AccountBalanceTransactionTypeEnum.ESCROW_IN.toString());

        accountBalanceTransactionStatus.setAccountBalanceTransactionStatusName(AccountBalanceTransactionStatusEnum.COMPLETED.toString());

        accountBalanceTransaction.setAccountBalanceTransactionName(accountBalanceTransactionName);
        accountBalanceTransaction.setAmount(amount);
        accountBalanceTransaction.setCreatedDateTime(createdDateTime);

        updatedAccountBalanceTransaction.setAccountBalanceTransactionName(updatedAccountBalanceTransactionName);
        updatedAccountBalanceTransaction.setAmount(updatedAmount);
        updatedAccountBalanceTransaction.setCreatedDateTime(updatedCreatedDateTime);
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
    private AccountBalanceTransactionService accountBalanceTransactionService;

    @Autowired
    private AccountBalanceTransactionTypeService accountBalanceTransactionTypeService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private AccountBalanceTransactionStatusService accountBalanceTransactionStatusService;

    @Test
    @Order(2)
    void insertAccountBalanceTransactionTest() throws SQLException {
        //int count = AccountBalanceTransactionTypesTable.insertAccountBalanceTransactionType(
        //        accountBalanceTransactionType, connection);
        //assertTrue(count == 1);
        accountBalanceTransactionType = accountBalanceTransactionTypeService.getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                accountBalanceTransactionType.getAccountBalanceTransactionTypeName()).get();
        accountBalanceTransaction.setAccountBalanceTransactionType(accountBalanceTransactionType);
        updatedAccountBalanceTransaction.setAccountBalanceTransactionType(accountBalanceTransactionType);


        //count = AccountBalanceTransactionStatusesTable.insertAccountBalanceTransactionStatus(
        //        accountBalanceTransactionStatus, connection);
        //assertTrue(count == 1);
        accountBalanceTransactionStatus = accountBalanceTransactionStatusService
                .getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                accountBalanceTransactionStatus.getAccountBalanceTransactionStatusName()).get();
        accountBalanceTransaction.setAccountBalanceTransactionStatus(accountBalanceTransactionStatus);
        updatedAccountBalanceTransaction.setAccountBalanceTransactionStatus(accountBalanceTransactionStatus);

        unit = unitService.saveUnit(unit);
        accountBalanceTransaction.setUnit(unit);
        updatedAccountBalanceTransaction.setUnit(unit);

        accountBalanceTransactionService.saveAccountBalanceTransaction(accountBalanceTransaction);
        updatedAccountBalanceTransaction.setId(accountBalanceTransaction.getId());
    }
    @Test
    @Order(3)
    void selectAccountBalanceTransactionByAccountBalanceTransactionNameTest() throws SQLException {
        AccountBalanceTransaction accountBalanceTransaction1 = accountBalanceTransactionService
                .getAccountBalanceTransactionByAccountBalanceTransactionName(accountBalanceTransactionName).get();
        assertTrue(accountBalanceTransaction1.getAccountBalanceTransactionName().equals(accountBalanceTransactionName));
    }
    @Test
    @Order(4)
    void updateAccountBalanceTransactionByAccountBalanceTransactionNameTest() throws SQLException {
        updatedAccountBalanceTransaction = accountBalanceTransactionService
                .updateAccountBalanceTransaction(
                        updatedAccountBalanceTransaction);
    }
    @Test
    @Order(5)
    void selectUpdatedAccountBalanceTransactionByAccountBalanceTransactionNameTest() throws SQLException {
        AccountBalanceTransaction accountBalanceTransaction1 = accountBalanceTransactionService
                .getAccountBalanceTransactionByAccountBalanceTransactionName(
                        updatedAccountBalanceTransactionName).get();
        assertTrue(accountBalanceTransaction1.getAccountBalanceTransactionName().equals(updatedAccountBalanceTransactionName));
    }
    @Test
    @Order(6)
    void deleteAccountBalanceTransactionByAccountBalanceTransactionNameTest() throws SQLException {
        accountBalanceTransactionService.deleteAccountBalanceTransaction(
                updatedAccountBalanceTransaction.getId());

        //accountBalanceTransactionTypeService.deleteAccountBalanceTransactionType(
        //        accountBalanceTransactionType.getId());

        //count = AccountBalanceTransactionStatusesTable.deleteAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
        //        accountBalanceTransactionStatus.getAccountBalanceTransactionStatusName(), connection);
        //assertTrue(count == 1);

        unitService.deleteUnit(unit.getId());
    }
}
