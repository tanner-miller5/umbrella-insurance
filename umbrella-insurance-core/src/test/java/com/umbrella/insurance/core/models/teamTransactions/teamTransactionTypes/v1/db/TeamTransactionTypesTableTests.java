package com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.TeamTransactionType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa.TeamTransactionTypeService;
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
public class TeamTransactionTypesTableTests {
    private static String teamTransactionTypeName = "1234f";
    private static String updatedTeamTransactionTypeName = "12345g";
    private static TeamTransactionType teamTransactionType = new TeamTransactionType();
    private static TeamTransactionType updatedTeamTransactionType = new TeamTransactionType();
    static {
        teamTransactionType.setTeamTransactionTypeName(teamTransactionTypeName);

        updatedTeamTransactionType.setTeamTransactionTypeName(updatedTeamTransactionTypeName);
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
    private TeamTransactionTypeService teamTransactionTypeService;

    @Test
    @Order(2)
    void insertTeamTransactionTypeTest() throws SQLException {
        teamTransactionType = teamTransactionTypeService.saveTeamTransactionType(teamTransactionType);
        updatedTeamTransactionType.setId(teamTransactionType.getId());
    }
    @Test
    @Order(3)
    void selectTeamTransactionTypeByTeamTransactionTypeNameTest() throws SQLException {
        TeamTransactionType teamTransactionType1 = teamTransactionTypeService
                .findTeamTransactionTypeByTeamTransactionTypeName(teamTransactionTypeName).get();
        assertTrue(teamTransactionType1.getTeamTransactionTypeName().equals(teamTransactionTypeName));
    }
    @Test
    @Order(4)
    void updateTeamTransactionTypeByTeamTransactionTypeNameTest() throws SQLException {
        updatedTeamTransactionType = teamTransactionTypeService
                .updateTeamTransactionType(updatedTeamTransactionType);
    }
    @Test
    @Order(5)
    void selectUpdatedTeamTransactionTypeByTeamTransactionTypeNameTest() throws SQLException {
        TeamTransactionType teamTransactionType1 = teamTransactionTypeService
                .findTeamTransactionTypeByTeamTransactionTypeName(updatedTeamTransactionTypeName).get();
        assertTrue(teamTransactionType1.getTeamTransactionTypeName().equals(updatedTeamTransactionTypeName));
    }
    @Test
    @Order(6)
    void deleteTeamTransactionTypeByTeamTransactionTypeNameTest() throws SQLException {
        teamTransactionTypeService
                .deleteTeamTransactionType(updatedTeamTransactionType.getId());
    }

}
