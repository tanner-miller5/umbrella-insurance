package com.umbrella.insurance.core.models.records.playerRecords.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.entities.Record;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.people.players.v1.db.jpa.PlayerService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.records.playerRecords.v1.db.jpa.PlayerRecordService;
import com.umbrella.insurance.core.models.records.v1.db.jpa.RecordService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
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
public class PlayerRecordsTableTests {
    private static Record record = new Record();
    private static Player player = new Player();
    private static Season season = new Season();
    private static Person person = new Person();
    private static GameType gameType = new GameType();
    private static PlayerRecord playerRecord = new PlayerRecord();
    private static PlayerRecord updatedPlayerRecord = new PlayerRecord();
    static {
        record.setLosses(1l);
        record.setTies(2l);
        record.setWins(3l);
        record.setRecordName("1");

        gameType.setGameTypeName("2");

        season.setEndDate(Date.valueOf("1116-11-11"));
        season.setStartDate(Date.valueOf("2002-10-10"));
        season.setSeasonName("6");

        player.setPlayerPosition("11");
        player.setBirthplace("12");
        player.setExperience("10");
        player.setJerseyNumber("00");
        player.setPlayerStatus("12");
        player.setDraftInfo("13");
        player.setHeight("14");
        player.setCollege("15");
        player.setWeight("16");

        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSsn("1");
        person.setSurname("2");
        person.setMiddleName("3");
        person.setFirstName("4");
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
    private SeasonService seasonService;

    @Autowired
    private PersonService personService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private PlayerRecordService playerRecordService;

    @Test
    @Order(2)
    void insertPlayerRecordTest() throws SQLException {
        season = seasonService.saveSeason(season);
        playerRecord.setSeason(season);
        updatedPlayerRecord.setSeason(season);

        person = personService.savePerson(person);
        player.setPerson(person);

        gameType = gameTypeService.saveGameType(gameType);
        player.setGameType(gameType);

        player = playerService.savePlayer(player);
        playerRecord.setPlayer(player);
        updatedPlayerRecord.setPlayer(player);

        record = recordService.saveRecord(record);
        playerRecord.setRecord(record);
        updatedPlayerRecord.setRecord(record);

        playerRecord = playerRecordService.savePlayerRecord(playerRecord);
        updatedPlayerRecord.setId(playerRecord.getId());
    }
    @Test
    @Order(3)
    void selectPlayerRecordByPlayerRecordNameTest() throws SQLException {
        PlayerRecord playerRecord1 = playerRecordService.getPlayerRecordBySeasonIdAndPlayerId(
                updatedPlayerRecord.getSeason().getId(), updatedPlayerRecord.getPlayer().getId()).get();
        assertTrue(playerRecord1.getRecord().getId().equals(record.getId()));
        assertTrue(playerRecord1.getPlayer().getId().equals(playerRecord.getPlayer().getId()));
        assertTrue(playerRecord1.getSeason().getId().equals(season.getId()));
    }
    @Test
    @Order(4)
    void updatePlayerRecordByPlayerRecordNameTest() throws SQLException {
        updatedPlayerRecord = playerRecordService.updatePlayerRecord(
                updatedPlayerRecord);
    }
    @Test
    @Order(5)
    void selectUpdatedPlayerRecordByPlayerRecordNameTest() throws SQLException {
        PlayerRecord playerRecord1 = playerRecordService.getPlayerRecordBySeasonIdAndPlayerId(
                season.getId(), player.getId()).get();
        assertTrue(playerRecord1.getRecord().getId().equals(record.getId()));
        assertTrue(playerRecord1.getPlayer().getId().equals(player.getId()));
        assertTrue(playerRecord1.getSeason().getId().equals(season.getId()));
    }
    @Test
    @Order(6)
    void deletePlayerRecordByPlayerRecordNameTest() throws SQLException {
        playerRecordService.deletePlayerRecord(playerRecord.getId());

        playerService.deletePlayer(player.getId());

        personService.deletePerson(person.getId());

        recordService.deleteRecord(record.getId());

        gameTypeService.deleteGameType(gameType.getId());

        seasonService.deleteSeason(season.getId());
    }

}
