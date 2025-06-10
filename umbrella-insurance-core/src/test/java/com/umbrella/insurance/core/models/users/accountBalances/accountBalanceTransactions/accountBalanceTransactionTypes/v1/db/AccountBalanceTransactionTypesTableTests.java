package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa.AccountBalanceTransactionTypeService;
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
public class AccountBalanceTransactionTypesTableTests {
    private static String accountBalanceTransactionTypeName = "1234";
    private static String updatedAccountBalanceTransactionTypeName = "12345";
    private static AccountBalanceTransactionType accountBalanceTransactionType = new AccountBalanceTransactionType();
    private static AccountBalanceTransactionType updatedAccountBalanceTransactionType = new AccountBalanceTransactionType();
    static {
        accountBalanceTransactionType.setAccountBalanceTransactionTypeName(accountBalanceTransactionTypeName);

        updatedAccountBalanceTransactionType.setAccountBalanceTransactionTypeName(updatedAccountBalanceTransactionTypeName);
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
    private AccountBalanceTransactionTypeService accountBalanceTransactionTypeService;

    @Test
    @Order(2)
    void insertAccountBalanceTransactionTypeTest() throws SQLException {
        accountBalanceTransactionType = accountBalanceTransactionTypeService.saveAccountBalanceTransactionType(
                accountBalanceTransactionType);
        updatedAccountBalanceTransactionType.setId(accountBalanceTransactionType.getId());
    }
    @Test
    @Order(3)
    void selectAccountBalanceTransactionTypeByAccountBalanceTransactionTypeNameTest() throws SQLException {
        AccountBalanceTransactionType accountBalanceTransactionType1 =
                accountBalanceTransactionTypeService.getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                        accountBalanceTransactionTypeName).get();
        assertTrue(accountBalanceTransactionType1.getAccountBalanceTransactionTypeName().equals(
                accountBalanceTransactionTypeName));
    }
    @Test
    @Order(4)
    void updateAccountBalanceTransactionTypeByAccountBalanceTransactionTypeNameTest() throws SQLException {
        accountBalanceTransactionTypeService
                .updateAccountBalanceTransactionType(
                        updatedAccountBalanceTransactionType);
    }
    @Test
    @Order(5)
    void selectUpdatedAccountBalanceTransactionTypeByAccountBalanceTransactionTypeNameTest() throws SQLException {
        AccountBalanceTransactionType accountBalanceTransactionType1 =
                accountBalanceTransactionTypeService
                        .getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                        updatedAccountBalanceTransactionTypeName).get();
        assertTrue(accountBalanceTransactionType1
                .getAccountBalanceTransactionTypeName().equals(updatedAccountBalanceTransactionTypeName));
    }
    @Test
    @Order(6)
    void deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeNameTest() throws SQLException {
        accountBalanceTransactionTypeService.deleteAccountBalanceTransactionType(
                        updatedAccountBalanceTransactionType.getId());
    }
}
