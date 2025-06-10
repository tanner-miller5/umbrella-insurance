package com.umbrella.insurance.core.models.teamMemberTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.TeamMemberType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.teamMemberTypes.v1.db.jpa.TeamMemberTypeService;
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
public class TeamMemberTypesTableTests {
    private static String teamMemberTypeName = "1234z";
    private static String updatedTeamMemberTypeName = "12345";
    private static TeamMemberType teamMemberType = new TeamMemberType();
    private static TeamMemberType updatedTeamMemberType = new TeamMemberType();
    static {
        teamMemberType.setTeamMemberTypeName(teamMemberTypeName);

        updatedTeamMemberType.setTeamMemberTypeName(updatedTeamMemberTypeName);
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
    private TeamMemberTypeService teamMemberTypeService;

    @Test
    @Order(2)
    void insertTeamMemberTypeTest() throws SQLException {
        teamMemberType = teamMemberTypeService.saveTeamMemberType(teamMemberType);
        updatedTeamMemberType.setId(teamMemberType.getId());
    }
    @Test
    @Order(3)
    void selectTeamMemberTypeByTeamMemberTypeNameTest() throws SQLException {
        TeamMemberType teamMemberType1 = teamMemberTypeService
                .getTeamMemberTypeByTeamMemberTypeName(teamMemberTypeName).get();
        assertTrue(teamMemberType1.getTeamMemberTypeName().equals(teamMemberTypeName));
    }
    @Test
    @Order(4)
    void updateTeamMemberTypeByTeamMemberTypeNameTest() throws SQLException {
        updatedTeamMemberType = teamMemberTypeService.updateTeamMemberType(
                updatedTeamMemberType);
    }
    @Test
    @Order(5)
    void selectUpdatedTeamMemberTypeByTeamMemberTypeNameTest() throws SQLException {
        TeamMemberType teamMemberType1 = teamMemberTypeService
                .getTeamMemberTypeByTeamMemberTypeName(updatedTeamMemberTypeName).get();
        assertTrue(teamMemberType1.getTeamMemberTypeName().equals(updatedTeamMemberTypeName));
    }
    @Test
    @Order(6)
    void deleteTeamMemberTypeByTeamMemberTypeNameTest() throws SQLException {
        teamMemberTypeService
                .deleteTeamMemberType(updatedTeamMemberType.getId());
    }

}
