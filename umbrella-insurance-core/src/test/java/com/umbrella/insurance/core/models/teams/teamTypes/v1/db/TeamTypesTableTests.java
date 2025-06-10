package com.umbrella.insurance.core.models.teams.teamTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.TeamType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa.TeamTypeService;
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
public class TeamTypesTableTests {
    private static String teamTypeName = "1234y";
    private static String updatedTeamTypeName = "12345";
    private static TeamType teamType = new TeamType();
    private static TeamType updatedTeamType = new TeamType();
    static {
        teamType.setTeamTypeName(teamTypeName);

        updatedTeamType.setTeamTypeName(updatedTeamTypeName);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private TeamTypeService teamTypeService;

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
    void insertTeamTypeTest() throws SQLException {
        teamType = teamTypeService.saveTeamType(teamType);
        updatedTeamType.setId(teamType.getId());
    }
    @Test
    @Order(3)
    void selectTeamTypeByTeamTypeNameTest() throws SQLException {
        TeamType teamType1 = teamTypeService.getTeamTypeByTeamTypeName(teamTypeName).get();
        assertTrue(teamType1.getTeamTypeName().equals(teamTypeName));
    }
    @Test
    @Order(4)
    void updateTeamTypeByTeamTypeNameTest() throws SQLException {
        updatedTeamType = teamTypeService.updateTeamType(updatedTeamType);
    }
    @Test
    @Order(5)
    void selectUpdatedTeamTypeByTeamTypeNameTest() throws SQLException {
        TeamType teamType1 = teamTypeService.getTeamTypeByTeamTypeName(updatedTeamTypeName).get();
        assertTrue(teamType1.getTeamTypeName().equals(updatedTeamTypeName));
    }
    @Test
    @Order(6)
    void deleteTeamTypeByTeamTypeNameTest() throws SQLException {
        teamTypeService.deleteTeamType(updatedTeamType.getId());
    }

}
