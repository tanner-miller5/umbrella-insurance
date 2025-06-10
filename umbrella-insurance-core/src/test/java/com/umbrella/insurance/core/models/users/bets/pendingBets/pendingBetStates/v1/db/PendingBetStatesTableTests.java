package com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.PendingBetState;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa.PendingBetStateService;
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
public class PendingBetStatesTableTests {
    private static String pendingBetStateName = "1234";
    private static String updatedPendingBetStateName = "12345";
    private static PendingBetState pendingBetState = new PendingBetState();
    private static PendingBetState updatedPendingBetState = new PendingBetState();
    static {
        pendingBetState.setPendingBetStateName(pendingBetStateName);

        updatedPendingBetState.setPendingBetStateName(updatedPendingBetStateName);

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
    private PendingBetStateService pendingBetStateService;

    @Test
    @Order(2)
    void insertPendingBetStateTest() throws SQLException {
        pendingBetState = pendingBetStateService.savePendingBetState(pendingBetState);
        updatedPendingBetState.setId(pendingBetState.getId());
    }
    @Test
    @Order(3)
    void selectPendingBetStateByPendingBetStateNameTest() throws SQLException {
        PendingBetState pendingBetState1 =
                pendingBetStateService.getPendingBetStateByPendingBetStateName(
                        pendingBetStateName).get();
        assertTrue(pendingBetState1.getPendingBetStateName().equals(pendingBetStateName));
    }
    @Test
    @Order(4)
    void updatePendingBetStateByPendingBetStateNameTest() throws SQLException {
        updatedPendingBetState = pendingBetStateService
                .updatePendingBetState(updatedPendingBetState);
    }
    @Test
    @Order(5)
    void selectUpdatedPendingBetStateByPendingBetStateNameTest() throws SQLException {
        PendingBetState pendingBetState1 =
                pendingBetStateService.getPendingBetStateByPendingBetStateName(
                        updatedPendingBetStateName).get();
        assertTrue(pendingBetState1.getPendingBetStateName().equals(updatedPendingBetStateName));
    }
    @Test
    @Order(6)
    void deletePendingBetStateByPendingBetStateNameTest() throws SQLException {
        pendingBetStateService.deletePendingBetState(
                updatedPendingBetState.getId());
    }
}
