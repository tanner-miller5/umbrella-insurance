package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.AccountBalanceType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa.AccountBalanceTypeService;
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
public class AccountBalanceTypesTableTests {
    private static String accountBalanceTypeName = "1234";
    private static String updatedAccountBalanceTypeName = "12345";
    private static AccountBalanceType accountBalanceType = new AccountBalanceType();
    private static AccountBalanceType updatedAccountBalanceType = new AccountBalanceType();
    static {
        accountBalanceType.setAccountBalanceTypeName(accountBalanceTypeName);

        updatedAccountBalanceType.setAccountBalanceTypeName(updatedAccountBalanceTypeName);
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
    private AccountBalanceTypeService accountBalanceTypeService;

    @Test
    @Order(2)
    void insertAccountBalanceTypeTest() throws SQLException {
        accountBalanceType = accountBalanceTypeService
                .saveAccountBalanceType(accountBalanceType);
        updatedAccountBalanceType.setId(accountBalanceType.getId());
    }
    @Test
    @Order(3)
    void selectAccountBalanceTypeByAccountBalanceTypeNameTest() throws SQLException {
        AccountBalanceType accountBalanceType1 = accountBalanceTypeService
                .findAccountBalanceTypeByAccountBalanceTypeName(accountBalanceTypeName).get();
        assertTrue(accountBalanceType1.getAccountBalanceTypeName().equals(accountBalanceTypeName));
    }
    @Test
    @Order(4)
    void updateAccountBalanceTypeByAccountBalanceTypeNameTest() throws SQLException {
        updatedAccountBalanceType = accountBalanceTypeService
                .updateAccountBalanceType(
                        updatedAccountBalanceType);
    }
    @Test
    @Order(5)
    void selectUpdatedAccountBalanceTypeByAccountBalanceTypeNameTest() throws SQLException {
        AccountBalanceType accountBalanceType1 = accountBalanceTypeService
                .findAccountBalanceTypeByAccountBalanceTypeName(updatedAccountBalanceType.getAccountBalanceTypeName()).get();
        assertTrue(accountBalanceType1.getAccountBalanceTypeName().equals(updatedAccountBalanceTypeName));
    }
    @Test
    @Order(6)
    void deleteAccountBalanceTypeByAccountBalanceTypeNameTest() throws SQLException {
        accountBalanceTypeService
                .deleteAccountBalanceType(updatedAccountBalanceType.getId());
    }

}
