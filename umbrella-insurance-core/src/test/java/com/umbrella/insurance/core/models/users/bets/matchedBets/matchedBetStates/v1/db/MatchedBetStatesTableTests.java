package com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.MatchedBetState;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa.MatchedBetStateService;
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
public class MatchedBetStatesTableTests {
    private static String matchedBetStateName = "1234";
    private static String updatedMatchedBetStateName = "12345";
    private static MatchedBetState matchedBetState = new MatchedBetState();
    private static MatchedBetState updatedMatchedBetState = new MatchedBetState();
    static {
        matchedBetState.setMatchedBetStateName(matchedBetStateName);

        updatedMatchedBetState.setMatchedBetStateName(updatedMatchedBetStateName);
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
    private MatchedBetStateService matchedBetStateService;

    @Test
    @Order(2)
    void insertMatchedBetStateTest() throws SQLException {
        matchedBetState = matchedBetStateService.saveMatchedBetState(matchedBetState);
        updatedMatchedBetState.setId(matchedBetState.getId());
    }
    @Test
    @Order(3)
    void selectMatchedBetStateByMatchedBetStateNameTest() throws SQLException {
        MatchedBetState matchedBetState1 = matchedBetStateService
                .getMatchedBetStateByMatchedBetStateName(matchedBetStateName).get();
        assertTrue(matchedBetState1.getMatchedBetStateName().equals(matchedBetStateName));
    }
    @Test
    @Order(4)
    void updateMatchedBetStateByMatchedBetStateNameTest() throws SQLException {
        updatedMatchedBetState = matchedBetStateService
                .updateMatchedBetState(
                        updatedMatchedBetState);
    }
    @Test
    @Order(5)
    void selectUpdatedMatchedBetStateByMatchedBetStateNameTest() throws SQLException {
        MatchedBetState matchedBetState1 = matchedBetStateService
                .getMatchedBetStateByMatchedBetStateName(updatedMatchedBetStateName).get();
        assertTrue(matchedBetState1.getMatchedBetStateName().equals(updatedMatchedBetStateName));
    }
    @Test
    @Order(6)
    void deleteMatchedBetStateByMatchedBetStateNameTest() throws SQLException {
        matchedBetStateService
                .deleteMatchedBetState(updatedMatchedBetState.getId());
    }

}
