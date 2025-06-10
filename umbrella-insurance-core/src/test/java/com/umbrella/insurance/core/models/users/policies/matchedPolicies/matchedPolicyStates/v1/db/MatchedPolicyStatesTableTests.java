package com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.MatchedPolicyState;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa.MatchedPolicyStateService;
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
public class MatchedPolicyStatesTableTests {
    private static String matchedPolicyStateName = "1234";
    private static String updatedMatchedPolicyStateName = "12345";
    private static MatchedPolicyState matchedPolicyState = new MatchedPolicyState();
    private static MatchedPolicyState updatedMatchedPolicyState = new MatchedPolicyState();
    static {
        matchedPolicyState.setMatchedPolicyStateName(matchedPolicyStateName);

        updatedMatchedPolicyState.setMatchedPolicyStateName(updatedMatchedPolicyStateName);
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
    private MatchedPolicyStateService matchedPolicyStateService;

    @Test
    @Order(2)
    void insertMatchedPolicyStateTest() throws SQLException {
        matchedPolicyStateService.saveMatchedPolicyState(matchedPolicyState);
        updatedMatchedPolicyState.setId(matchedPolicyState.getId());
    }
    @Test
    @Order(3)
    void selectMatchedPolicyStateByMatchedPolicyStateNameTest() throws SQLException {
        MatchedPolicyState matchedPolicyState1 = matchedPolicyStateService
                .getMatchedPolicyStateByMatchedPolicyStateName(matchedPolicyStateName).get();
        assertTrue(matchedPolicyState1.getMatchedPolicyStateName().equals(matchedPolicyStateName));
    }
    @Test
    @Order(4)
    void updateMatchedPolicyStateByMatchedPolicyStateNameTest() throws SQLException {
        updatedMatchedPolicyState = matchedPolicyStateService
                .updateMatchedPolicyState(
                        updatedMatchedPolicyState);
    }
    @Test
    @Order(5)
    void selectUpdatedMatchedPolicyStateByMatchedPolicyStateNameTest() throws SQLException {
        MatchedPolicyState matchedPolicyState1 = matchedPolicyStateService
                .getMatchedPolicyStateByMatchedPolicyStateName(updatedMatchedPolicyStateName).get();
        assertTrue(matchedPolicyState1.getMatchedPolicyStateName().equals(updatedMatchedPolicyStateName));
    }
    @Test
    @Order(6)
    void deleteMatchedPolicyStateByMatchedPolicyStateNameTest() throws SQLException {
        matchedPolicyStateService.deleteMatchedPolicyState(updatedMatchedPolicyState.getId());
    }

}
