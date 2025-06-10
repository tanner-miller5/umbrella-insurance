package com.umbrella.insurance.core.models.leagues.v1.db;

import com.umbrella.insurance.core.models.entities.GameType;
import com.umbrella.insurance.core.models.entities.League;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
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
public class LeaguesTableTests {
    private static String leagueName = "1234";
    private static String updatedLeagueName = "12345";

    private static GameType gameType = new GameType();
    private static League league = new League();
    private static League updatedLeague = new League();
    static {
        gameType.setGameTypeName("test");

        league.setLeagueName(leagueName);

        updatedLeague.setLeagueName(updatedLeagueName);
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
    GameTypeService gameTypeService;

    @Autowired
    LeagueService leagueService;

    @Test
    @Order(3)
    void insertLeagueTest() throws SQLException {
        gameType = gameTypeService.saveGameType(gameType);
        league.setGameType(gameType);
        updatedLeague.setGameType(gameType);

        league = leagueService.saveLeague(league);
        updatedLeague.setId(league.getId());
    }
    @Test
    @Order(4)
    void selectLeagueByLeagueNameAndGameTypeIdTest() throws SQLException {
        League league1 = leagueService.getLeagueByLeagueNameAndGameTypeId(
                leagueName, gameType.getId()).get();
        assertTrue(league1.getLeagueName().equals(leagueName));
        assertTrue(league1.getGameType().getId().equals(gameType.getId()));
    }
    @Test
    @Order(5)
    void updateLeagueByLeagueNameAndGameTypeIdTest() throws SQLException {
        updatedLeague = leagueService.updateLeague(
                updatedLeague);
    }
    @Test
    @Order(6)
    void selectUpdatedLeagueByLeagueNameAndGameTypeIdTest() throws SQLException {
        League league1 = leagueService
                .getLeagueByLeagueNameAndGameTypeId(
                updatedLeagueName,
                gameType.getId()).get();
        assertTrue(league1.getLeagueName().equals(updatedLeagueName));
        assertTrue(league1.getGameType().getId().equals(gameType.getId()));
    }
    @Test
    @Order(7)
    void deleteLeagueByLeagueNameAndGameTypeIdTest() throws SQLException {
        leagueService.deleteLeague(
                updatedLeague.getId());
        gameTypeService.deleteGameType(
                gameType.getId());
    }


}
