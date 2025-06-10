package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.PendingPolicyType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa.PendingPolicyTypeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PendingPolicyTypesTableTests {
    private static String pendingPolicyTypeName = "policyName";
    private static String updatedPendingPolicyTypeName = "policyName1";

    private static PendingPolicyType pendingPolicyType = new PendingPolicyType();
    private static PendingPolicyType updatedPendingPolicyType = new PendingPolicyType();
    static {
        pendingPolicyType.setPendingPolicyTypeName(pendingPolicyTypeName);

        updatedPendingPolicyType.setPendingPolicyTypeName(updatedPendingPolicyTypeName);
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
    private PendingPolicyTypeService pendingPolicyTypeService;

    @Test
    @Order(2)
    void insertPendingPolicyTypeTest() throws SQLException {
        pendingPolicyType = pendingPolicyTypeService.savePendingPolicyType(pendingPolicyType);
        updatedPendingPolicyType.setId(pendingPolicyType.getId());
    }
    @Test
    @Order(3)
    void selectPendingPolicyTypeByPendingPolicyTypeNameTest() throws SQLException {
        PendingPolicyType pendingPolicyType1 = pendingPolicyTypeService
                .getPendingPolicyTypeByPendingPolicyTypeName(
                        pendingPolicyTypeName).get();
        assertTrue(pendingPolicyType1.getPendingPolicyTypeName().equals(pendingPolicyTypeName));
    }
    @Test
    @Order(4)
    void updatePendingPolicyTypeByPendingPolicyTypeNameTest() throws SQLException {
        updatedPendingPolicyType = pendingPolicyTypeService.updatePendingPolicyType(
                updatedPendingPolicyType);
    }
    @Test
    @Order(5)
    void selectUpdatedPendingPolicyTypeByPendingPolicyTypeNameTest() throws SQLException {
        PendingPolicyType pendingPolicyType1 = pendingPolicyTypeService
                .getPendingPolicyTypeByPendingPolicyTypeName(
                updatedPendingPolicyTypeName).get();
        assertTrue(pendingPolicyType1.getPendingPolicyTypeName().equals(updatedPendingPolicyTypeName));
    }
    @Test
    @Order(6)
    void deletePendingPolicyTypeByPendingPolicyTypeNameTest() throws SQLException {
        pendingPolicyTypeService.deletePendingPolicyType(
                updatedPendingPolicyType.getId());
    }
}
