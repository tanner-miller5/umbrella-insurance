package com.umbrella.insurance.core.models.tournaments.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Tournament;
import com.umbrella.insurance.core.models.entities.TournamentType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa.TournamentTypeService;
import com.umbrella.insurance.core.models.tournaments.v1.db.jpa.TournamentService;
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
public class TournamentsTableTests {
    private static String tournamentName = "1234";
    private static String updatedTournamentName = "12345";
    private static String tournamentTypeName = "1234";
    private static TournamentType tournamentType = new TournamentType();
    private static Tournament tournament = new Tournament();
    private static Tournament updatedTournament = new Tournament();
    static {
        tournamentType.setTournamentTypeName(tournamentTypeName);
        tournament.setTournamentType(tournamentType);
        updatedTournament.setTournamentType(tournamentType);

        tournament.setTournamentName(tournamentName);

        updatedTournament.setTournamentName(updatedTournamentName);
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
    private TournamentService tournamentService;

    @Autowired
    private TournamentTypeService tournamentTypeService;

    @Test
    @Order(2)
    void insertTournamentTest() throws SQLException {
        tournamentType = tournamentTypeService.saveTournamentType(tournamentType);
        tournament.setTournamentType(tournamentType);
        updatedTournament.setTournamentType(tournamentType);

        tournament = tournamentService.saveTournament(tournament);
        updatedTournament.setId(tournament.getId());
    }
    @Test
    @Order(3)
    void selectTournamentByTournamentNameTest() throws SQLException {
        Tournament tournament1 = tournamentService
                .getTournamentByTournamentName(tournamentName).get();
        assertTrue(tournament1.getTournamentName().equals(tournamentName));
        assertTrue(tournament1.getTournamentType().getId().equals(tournamentType.getId()));
    }
    @Test
    @Order(4)
    void updateTournamentByTournamentNameTest() throws SQLException {
        updatedTournament = tournamentService.updateTournament(
                updatedTournament);
    }
    @Test
    @Order(5)
    void selectUpdatedTournamentByTournamentNameTest() throws SQLException {
        Tournament tournament1 = tournamentService
                .getTournamentByTournamentName(updatedTournamentName).get();
        assertTrue(tournament1.getTournamentName().equals(updatedTournamentName));
        assertTrue(tournament1.getTournamentType().getId().equals(tournamentType.getId()));
    }
    @Test
    @Order(6)
    void deleteTournamentByTournamentNameTest() throws SQLException {
        tournamentService.deleteTournament(updatedTournament.getId());
        tournamentTypeService.deleteTournamentType(tournamentType.getId());
    }

}
