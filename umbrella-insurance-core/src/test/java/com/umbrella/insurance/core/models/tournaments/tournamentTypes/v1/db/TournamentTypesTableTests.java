package com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.TournamentType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa.TournamentTypeService;
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
public class TournamentTypesTableTests {
    private static String tournamentTypeName = "1234";
    private static String updatedTournamentTypeName = "12345";
    private static TournamentType tournamentType = new TournamentType();
    private static TournamentType updatedTournamentType = new TournamentType();
    static {
        tournamentType.setTournamentTypeName(tournamentTypeName);

        updatedTournamentType.setTournamentTypeName(updatedTournamentTypeName);

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
    TournamentTypeService tournamentTypeService;

    @Test
    @Order(2)
    void insertTournamentTypeTest() throws SQLException {
        tournamentType = tournamentTypeService.saveTournamentType(tournamentType);
        updatedTournamentType.setId(tournamentType.getId());
    }
    @Test
    @Order(3)
    void selectTournamentTypeByTournamentTypeNameTest() throws SQLException {
        TournamentType tournamentType1 = tournamentTypeService
                .findTournamentTypeByTournamentTypeName(tournamentTypeName).get();
        assertTrue(tournamentType1.getTournamentTypeName().equals(tournamentTypeName));
    }
    @Test
    @Order(4)
    void updateTournamentTypeByTournamentTypeNameTest() throws SQLException {
        updatedTournamentType = tournamentTypeService
                .updateTournamentType(
                        updatedTournamentType);
    }
    @Test
    @Order(5)
    void selectUpdatedTournamentTypeByTournamentTypeNameTest() throws SQLException {
        TournamentType tournamentType1 = tournamentTypeService
                .findTournamentTypeByTournamentTypeName(updatedTournamentTypeName).get();
        assertTrue(tournamentType1.getTournamentTypeName().equals(updatedTournamentTypeName));
    }
    @Test
    @Order(6)
    void deleteTournamentTypeByTournamentTypeNameTest() throws SQLException {
        tournamentTypeService
                .deleteTournamentType(updatedTournamentType.getId());
    }
}
