package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.PendingPolicyState;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa.PendingPolicyStateService;
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
public class PendingPolicyStatesTableTests {
    private static String pendingPolicyStateName = "1";
    private static String updatedPendingPolicyStateName = "3";
    private static PendingPolicyState pendingPolicyState = new PendingPolicyState();
    private static PendingPolicyState updatedPendingPolicyState = new PendingPolicyState();
    static {
        pendingPolicyState.setPendingPolicyStateName(pendingPolicyStateName);

        updatedPendingPolicyState.setPendingPolicyStateName(updatedPendingPolicyStateName);
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
    private PendingPolicyStateService pendingPolicyStateService;

    @Test
    @Order(2)
    void insertPendingPolicyStateTest() throws SQLException {
        pendingPolicyState = pendingPolicyStateService.savePendingPolicyState(pendingPolicyState);
        updatedPendingPolicyState.setId(pendingPolicyState.getId());
    }
    @Test
    @Order(3)
    void selectPendingPolicyStateByPendingPolicyStateNameTest() throws SQLException {
        PendingPolicyState pendingPolicyState1 = pendingPolicyStateService
                .getPendingPolicyStateName(pendingPolicyStateName).get();
        assertTrue(pendingPolicyState1.getPendingPolicyStateName().equals(pendingPolicyStateName));
    }
    @Test
    @Order(4)
    void updatePendingPolicyStateByPendingPolicyStateNameTest() throws SQLException {
        updatedPendingPolicyState = pendingPolicyStateService
                .updatePendingPolicyState(
                        updatedPendingPolicyState);
    }
    @Test
    @Order(5)
    void selectUpdatedPendingPolicyStateByPendingPolicyStateNameTest() throws SQLException {
        PendingPolicyState pendingPolicyState1 = pendingPolicyStateService
                .getPendingPolicyStateName(updatedPendingPolicyStateName).get();
        assertTrue(pendingPolicyState1.getPendingPolicyStateName().equals(updatedPendingPolicyStateName));
    }
    @Test
    @Order(6)
    void deletePendingPolicyStateByPendingPolicyStateNameTest() throws SQLException {
        pendingPolicyStateService
                .deletePendingPolicyState(updatedPendingPolicyState.getId());
    }

}
