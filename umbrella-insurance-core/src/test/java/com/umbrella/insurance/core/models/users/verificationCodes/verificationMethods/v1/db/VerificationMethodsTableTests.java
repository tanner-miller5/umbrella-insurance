package com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.VerificationMethod;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
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
public class VerificationMethodsTableTests {
    private static String verificationMethodName = "1234";
    private static String updatedVerificationMethodName = "12345";
    private static VerificationMethod verificationMethod = new VerificationMethod();
    private static VerificationMethod updatedVerificationMethod = new VerificationMethod();
    static {
        verificationMethod.setVerificationMethodName(verificationMethodName);

        updatedVerificationMethod.setVerificationMethodName(updatedVerificationMethodName);

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
    private VerificationMethodService verificationMethodService;

    @Test
    @Order(2)
    void insertVerificationMethodTest() throws SQLException {
        verificationMethod = verificationMethodService.saveVerificationMethod(verificationMethod);
        updatedVerificationMethod.setId(verificationMethod.getId());
    }
    @Test
    @Order(3)
    void selectVerificationMethodByVerificationMethodNameTest() throws SQLException {
        VerificationMethod verificationMethod1 = verificationMethodService
                .getVerificationMethodByVerificationMethodName(verificationMethodName).get();
        assertTrue(verificationMethod1.getVerificationMethodName().equals(verificationMethodName));
    }
    @Test
    @Order(4)
    void updateVerificationMethodByVerificationMethodNameTest() throws SQLException {
        updatedVerificationMethod = verificationMethodService
                .updateVerificationMethod(
                        updatedVerificationMethod);
    }
    @Test
    @Order(5)
    void selectUpdatedVerificationMethodByVerificationMethodNameTest() throws SQLException {
        VerificationMethod verificationMethod1 = verificationMethodService
                .getVerificationMethodByVerificationMethodName(updatedVerificationMethodName).get();
        assertTrue(verificationMethod1.getVerificationMethodName().equals(updatedVerificationMethodName));
    }
    @Test
    @Order(6)
    void deleteVerificationMethodByVerificationMethodNameTest() throws SQLException {
        verificationMethodService
                .deleteVerificationMethod(updatedVerificationMethod.getId());
    }

}
