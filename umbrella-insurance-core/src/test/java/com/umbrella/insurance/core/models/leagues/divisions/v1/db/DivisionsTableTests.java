package com.umbrella.insurance.core.models.leagues.divisions.v1.db;

import com.umbrella.insurance.core.models.entities.Conference;
import com.umbrella.insurance.core.models.entities.Division;
import com.umbrella.insurance.core.models.entities.GameType;
import com.umbrella.insurance.core.models.entities.League;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.jpa.DivisionService;
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
public class DivisionsTableTests {
    private static String divisionName = "1234";
    private static String updatedDivisionName = "12345";
    private static GameType gameType = new GameType();
    private static League league = new League();
    private static Conference conference = new Conference();
    private static Division division = new Division();
    private static Division updatedDivision = new Division();
    static {
        gameType.setGameTypeName("test");
        league.setLeagueName("test");
        conference.setConferenceName("test");
        division.setDivisionName(divisionName);

        updatedDivision.setDivisionName(updatedDivisionName);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private DivisionService divisionService;

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
    GameTypeService gameTypeService;

    @Autowired
    ConferenceService conferenceService;

    @Test
    @Order(3)
    void insertDivisionTest() throws SQLException {
        gameType = gameTypeService.saveGameType(gameType);
        league.setGameType(gameType);

        league = leagueService.saveLeague(league);
        conference.setLeague(league);

        conference = conferenceService.saveConference(conference);
        division.setConference(conference);
        updatedDivision.setConference(conference);

        division = divisionService.saveDivision(division);
        updatedDivision.setId(division.getId());

    }
    @Test
    @Order(4)
    void selectDivisionByConferenceIdAndDivisionNameTest() throws SQLException {
        Division division1 = divisionService
                .getDivisionByConferenceIdAndDivisionName(
                conference.getId(), divisionName).get();
        assertTrue(division1.getConference().getId().equals(conference.getId()));
        assertTrue(division1.getDivisionName().equals(divisionName));
    }
    @Test
    @Order(5)
    void updateDivisionByConferenceIdAndDivisionNameTest() throws SQLException {
        updatedDivision = divisionService
                .updateDivision(
                updatedDivision);
    }
    @Test
    @Order(6)
    void selectUpdatedDivisionByConferenceIdAndDivisionNameTest() throws SQLException {
        Division division1 = divisionService
                .getDivisionByConferenceIdAndDivisionName(
                conference.getId(), updatedDivisionName).get();
        assertTrue(division1.getConference().getId().equals(conference.getId()));
        assertTrue(division1.getDivisionName().equals(updatedDivisionName));
    }
    @Test
    @Order(7)
    void deleteDivisionByConferenceIdAndDivisionNameTest() throws SQLException {
        divisionService.deleteDivision(
                updatedDivision.getId());

        conferenceService.deleteConference(
                conference.getId());

        leagueService.deleteLeague(
                league.getId());

        gameTypeService.deleteGameType(gameType.getId());
    }

}
