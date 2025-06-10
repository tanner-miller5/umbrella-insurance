package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionStatus;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa.AccountBalanceTransactionStatusService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountBalanceTransactionStatusesTableTests {
    private static String accountBalanceTransactionStatusName = "1234";
    private static String updatedAccountBalanceTransactionStatusName = "12345";
    private static AccountBalanceTransactionStatus accountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    private static AccountBalanceTransactionStatus updatedAccountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    static {
        accountBalanceTransactionStatus.setAccountBalanceTransactionStatusName(accountBalanceTransactionStatusName);

        updatedAccountBalanceTransactionStatus.setAccountBalanceTransactionStatusName(updatedAccountBalanceTransactionStatusName);
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
    private AccountBalanceTransactionStatusService accountBalanceTransactionStatusService;

    @Test
    @Order(2)
    void insertAccountBalanceTransactionStatusTest() throws SQLException {
        accountBalanceTransactionStatus = accountBalanceTransactionStatusService.saveAccountBalanceTransactionStatus(
                    accountBalanceTransactionStatus);
        updatedAccountBalanceTransactionStatus.setId(accountBalanceTransactionStatus.getId());
    }
    @Test
    @Order(3)
    void selectAccountBalanceTransactionStatusByAccountBalanceTransactionStatusNameTest() throws SQLException {
        AccountBalanceTransactionStatus accountBalanceTransactionStatus1 =
                accountBalanceTransactionStatusService
                        .getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                        accountBalanceTransactionStatusName).get();
        assertTrue(accountBalanceTransactionStatus1.getAccountBalanceTransactionStatusName().equals(accountBalanceTransactionStatusName));
    }
    @Test
    @Order(4)
    void updateAccountBalanceTransactionStatusByAccountBalanceTransactionStatusNameTest() throws SQLException {
        updatedAccountBalanceTransactionStatus = accountBalanceTransactionStatusService
                .updateAccountBalanceTransactionStatus(
                updatedAccountBalanceTransactionStatus);
    }
    @Test
    @Order(5)
    void selectUpdatedAccountBalanceTransactionStatusByAccountBalanceTransactionStatusNameTest() throws SQLException {
        AccountBalanceTransactionStatus accountBalanceTransactionStatus1 =
                accountBalanceTransactionStatusService
                        .getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                updatedAccountBalanceTransactionStatusName).get();
        assertTrue(accountBalanceTransactionStatus1.getAccountBalanceTransactionStatusName().equals(updatedAccountBalanceTransactionStatusName));
    }
    @Test
    @Order(6)
    void deleteAccountBalanceTransactionStatusByAccountBalanceTransactionStatusNameTest() throws SQLException {
        accountBalanceTransactionStatusService
                .deleteAccountBalanceTransactionStatus(
                updatedAccountBalanceTransactionStatus.getId());
    }
}
