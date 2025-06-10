package com.umbrella.insurance.core.models.leagues.conferences.v1.db;

import com.umbrella.insurance.core.models.entities.Conference;
import com.umbrella.insurance.core.models.entities.GameType;
import com.umbrella.insurance.core.models.entities.League;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.leagues.conferences.v1.db.jpa.ConferenceService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.leagues.v1.db.jpa.LeagueService;
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
public class ConferencesTableTests {
    private static String conferenceName = "1234";
    private static String updatedConferenceName = "12345";
    private static GameType gameType = new GameType();
    private static League league = new League();
    private static Conference conference = new Conference();
    private static Conference updatedConference = new Conference();
    static {
        gameType.setGameTypeName("test");

        league.setLeagueName("test");

        conference.setConferenceName(conferenceName);

        updatedConference.setConferenceName(updatedConferenceName);
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
    private GameTypeService gameTypeService;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private ConferenceService conferenceService;

    @Test
    @Order(3)
    void insertConferenceTest() throws SQLException {
        gameType = gameTypeService.saveGameType(gameType);
        league.setGameType(gameType);

        leagueService.saveLeague(league);
        conference.setLeague(league);
        updatedConference.setLeague(league);

        conference = conferenceService.saveConference(conference);
        updatedConference.setId(conference.getId());
    }
    @Test
    @Order(4)
    void selectConferenceByConferenceNameAndLeagueIdTest() throws SQLException {
        Conference conference1 = conferenceService.getConferenceByNameAndLeagueId(
                conferenceName, league.getId()).get();
        assertTrue(conference1.getConferenceName().equals(conferenceName));
        assertTrue(conference1.getLeague().getId().equals(league.getId()));
    }
    @Test
    @Order(5)
    void updateConferenceByConferenceNameAndLeagueIdTest() throws SQLException {
        updatedConference = conferenceService
                .updateConference(
                updatedConference);
    }
    @Test
    @Order(6)
    void selectUpdatedConferenceByConferenceNameAndLeagueIdTest() throws SQLException {
        Conference conference1 = conferenceService
                .getConferenceByNameAndLeagueId(
                updatedConferenceName, league.getId()).get();
        assertTrue(conference1.getConferenceName().equals(updatedConferenceName));
        assertTrue(conference1.getLeague().getId().equals(league.getId()));
    }
    @Test
    @Order(7)
    void deleteConferenceByConferenceNameAndLeagueIdTest() throws SQLException {
        conferenceService
                .deleteConference(
                updatedConference.getId());

        leagueService.deleteLeague(league.getId());

        gameTypeService.deleteGameType(
                gameType.getId());
    }
}
