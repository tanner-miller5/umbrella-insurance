package com.umbrella.insurance.core.models.geographies.states.v1.db;

import com.umbrella.insurance.core.models.entities.State;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
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
public class StatesTableTests {
    private static String stateName = "1234";
    private static String updatedStateName = "12345";
    private static State state = new State();
    private static State updatedState = new State();
    static {
        state.setStateName(stateName);

        updatedState.setStateName(updatedStateName);
    }

    @Autowired
    private StateService stateService;

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

    @Test
    @Order(2)
    void insertStateTest() throws SQLException {
        state = stateService.saveState(state);
        updatedState.setId(state.getId());
    }
    @Test
    @Order(3)
    void selectStateByStateNameTest() throws SQLException {
        State state1 = stateService.getStateByStateName(stateName).get();
        assertTrue(state1.getStateName().equals(stateName));
    }
    @Test
    @Order(4)
    void updateStateByStateNameTest() throws SQLException {
        updatedState = stateService.updateState(
                updatedState);
    }
    @Test
    @Order(5)
    void selectUpdatedStateByStateNameTest() throws SQLException {
        State state1 = stateService.getStateByStateName(updatedStateName).get();
        assertTrue(state1.getStateName().equals(updatedStateName));
    }
    @Test
    @Order(6)
    void deleteStateByStateNameTest() throws SQLException {
        stateService.deleteState(updatedState.getId());
    }
}
