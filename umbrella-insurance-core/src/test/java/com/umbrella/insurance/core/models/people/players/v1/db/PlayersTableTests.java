package com.umbrella.insurance.core.models.people.players.v1.db;

import com.umbrella.insurance.core.models.entities.GameType;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.Player;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.people.players.v1.db.jpa.PlayerService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayersTableTests {
    private static String height = "1";
    private static String weight = "2";
    private static String college = "3";
    private static String draftInfo = "4";
    private static String playerStatus="5";
    private static String jerseyNumber="6";
    private static String position="7";
    private static String experience = "8";
    private static String birthPlace = "9";

    private static String updatedHeight = "1";
    private static String updatedWeight = "2";
    private static String updatedCollege = "3";
    private static String updatedDraftInfo = "4";
    private static String updatedPlayerStatus="5";
    private static String updatedJerseyNumber="6";
    private static String updatedPosition="7";
    private static String updatedExperience = "8";
    private static String updatedBirthPlace = "9";

    private static Person person = new Person();
    private static GameType gameType = new GameType();
    private static Player player = new Player();
    private static Player updatedPlayer = new Player();
    static {
        person.setSsn("12345678");
        person.setDateOfBirth(Date.valueOf("1950-11-13"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        gameType.setGameTypeName("soccer");

        player.setHeight(height);
        player.setWeight(weight);
        player.setCollege(college);
        player.setDraftInfo(draftInfo);
        player.setPlayerStatus(playerStatus);
        player.setJerseyNumber(jerseyNumber);
        player.setPlayerPosition(position);
        player.setExperience(experience);
        player.setBirthplace(birthPlace);


        updatedPlayer.setHeight(updatedHeight);
        updatedPlayer.setWeight(updatedWeight);
        updatedPlayer.setCollege(updatedCollege);
        updatedPlayer.setDraftInfo(updatedDraftInfo);
        updatedPlayer.setPlayerStatus(updatedPlayerStatus);
        updatedPlayer.setJerseyNumber(updatedJerseyNumber);
        updatedPlayer.setPlayerPosition(updatedPosition);
        updatedPlayer.setExperience(updatedExperience);
        updatedPlayer.setBirthplace(updatedBirthPlace);
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
    private PersonService personService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameTypeService gameTypeService;

    @Test
    @Order(4)
    void insertPlayerTest() throws SQLException {
        person = personService.savePerson(person);
        player.setPerson(person);
        updatedPlayer.setPerson(person);

        gameType = gameTypeService.saveGameType(gameType);
        player.setGameType(gameType);
        updatedPlayer.setGameType(gameType);

        player = playerService.savePlayer(player);
        updatedPlayer.setId(player.getId());
    }
    @Test
    @Order(5)
    void selectPlayerByPlayerIdTest() throws SQLException {
        Player player1 = playerService.getPlayerById(
                player.getId()).get();
        //person_id, game_type_id,height, weight, college,
        //draft_info, player_status,jersey_number, "position",experience, birthplace
        assertTrue(player1.getPerson().getId().equals(person.getId()));
        assertTrue(player1.getGameType().getId().equals(gameType.getId()));
        assertTrue(player1.getHeight().equals(height));
        assertTrue(player1.getWeight().equals(weight));
        assertTrue(player1.getCollege().equals(college));
        assertTrue(player1.getDraftInfo().equals(draftInfo));
        assertTrue(player1.getPlayerStatus().equals(playerStatus));
        assertTrue(player1.getJerseyNumber().equals(jerseyNumber));
        assertTrue(player1.getPlayerPosition().equals(position));
        assertTrue(player1.getExperience().equals(experience));
        assertTrue(player1.getBirthplace().equals(birthPlace));
    }
    @Test
    @Order(6)
    void updatePlayerByPersonIdTest() throws SQLException {
        updatedPlayer = playerService.updatePlayer(
                updatedPlayer);
    }
    @Test
    @Order(7)
    void selectUpdatedPlayerByPlayerIdTest() throws SQLException {
        Player player1 = playerService.getPlayerById(
                player.getId()).get();
        assertTrue(player1.getPerson().getId().equals(person.getId()));
        assertTrue(player1.getGameType().getId().equals(gameType.getId()));
        assertTrue(player1.getHeight().equals(updatedHeight));
        assertTrue(player1.getWeight().equals(updatedWeight));
        assertTrue(player1.getCollege().equals(updatedCollege));
        assertTrue(player1.getDraftInfo().equals(updatedDraftInfo));
        assertTrue(player1.getPlayerStatus().equals(updatedPlayerStatus));
        assertTrue(player1.getJerseyNumber().equals(updatedJerseyNumber));
        assertTrue(player1.getPlayerPosition().equals(updatedPosition));
        assertTrue(player1.getExperience().equals(updatedExperience));
        assertTrue(player1.getBirthplace().equals(updatedBirthPlace));
    }
    @Test
    @Order(8)
    void deletePlayerByPersonIdTest() throws SQLException {
        playerService.deletePlayer(
                updatedPlayer.getId());

        personService.deletePerson(
                person.getId());

        gameTypeService.deleteGameType(
                gameType.getId());

    }
}
