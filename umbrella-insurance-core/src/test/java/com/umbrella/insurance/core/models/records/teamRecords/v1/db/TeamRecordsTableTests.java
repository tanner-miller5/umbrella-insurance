package com.umbrella.insurance.core.models.records.teamRecords.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.entities.Record;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa.LevelOfCompetitionService;
import com.umbrella.insurance.core.models.records.teamRecords.v1.db.jpa.TeamRecordService;
import com.umbrella.insurance.core.models.records.v1.db.jpa.RecordService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa.TeamTypeService;
import com.umbrella.insurance.core.models.teams.v1.db.jpa.TeamService;
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
public class TeamRecordsTableTests {
    private static Record record = new Record();
    private static Team team = new Team();
    private static TeamType teamType = new TeamType();
    private static Season season = new Season();
    private static Season firstSeason = new Season();
    private static Season lastSeason = new Season();
    private static LevelOfCompetition levelOfCompetition = new LevelOfCompetition();
    private static GameType gameType = new GameType();
    private static StreetAddress streetAddress = new StreetAddress();
    private static City city = new City();
    private static State state = new State();
    private static ZipCode zipCode = new ZipCode();
    private static Country country = new Country();
    private static Location location = new Location();
    private static TeamRecord teamRecord = new TeamRecord();
    private static TeamRecord updatedTeamRecord = new TeamRecord();
    static {
        record.setLosses(1l);
        record.setTies(2l);
        record.setWins(3l);
        record.setRecordName("1");

        gameType.setGameTypeName("2");

        levelOfCompetition.setLevelOfCompetitionName("pro");

        firstSeason.setEndDate(Date.valueOf("1111-11-11"));
        firstSeason.setStartDate(Date.valueOf("2000-10-10"));
        firstSeason.setSeasonName("3");

        lastSeason.setEndDate(Date.valueOf("1115-11-11"));
        lastSeason.setStartDate(Date.valueOf("2001-10-10"));
        lastSeason.setSeasonName("4");

        season.setEndDate(Date.valueOf("1116-11-11"));
        season.setStartDate(Date.valueOf("2002-10-10"));
        season.setSeasonName("6");

        team.setLogoName("logo");
        team.setTeamName("team");
        team.setLogoImage(new byte[1]);

        streetAddress.setStreetAddressLine1("1");
        streetAddress.setStreetAddressLine2("2");
        city.setCityName("3");
        state.setStateName("4");
        zipCode.setZipCodeValue("5");
        country.setCountryName("6");
        location.setLocationName("name");

        teamType.setTeamTypeName("5");

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

    @Autowired
    private RecordService recordService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StateService stateService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TeamRecordService teamRecordService;

    @Test
    @Order(2)
    void insertTeamRecordTest() throws SQLException {
        record = recordService.saveRecord(record);
        teamRecord.setRecord(record);
        updatedTeamRecord.setRecord(record);

        gameType = gameTypeService.saveGameType(gameType);
        team.setGameType(gameType);

        levelOfCompetition = levelOfCompetitionService.saveLevelOfCompetition(
                levelOfCompetition);
        team.setLevelOfCompetition(levelOfCompetition);

        season = seasonService.saveSeason(season);
        teamRecord.setSeason(season);
        updatedTeamRecord.setSeason(season);

        firstSeason = seasonService.saveSeason(firstSeason);
        team.setFirstSeason(firstSeason);

        lastSeason = seasonService.saveSeason(lastSeason);
        team.setLastSeason(lastSeason);

        streetAddress = streetAddressService.saveStreetAddress(streetAddress);
        location.setStreetAddress(streetAddress);

        city = cityService.saveCity(city);
        location.setCity(city);

        state = stateService.saveState(state);
        location.setState(state);

        zipCode = zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);

        country = countryService.saveCountry(country);
        location.setCountry(country);

        location = locationService.saveLocation(location);
        team.setLocation(location);

        teamType = teamTypeService.saveTeamType(teamType);
        team.setTeamType(teamType);

        team = teamService.saveTeam(team);
        teamRecord.setTeam(team);
        updatedTeamRecord.setTeam(team);

        teamRecord = teamRecordService.saveTeamRecord(teamRecord);
        updatedTeamRecord.setId(teamRecord.getId());
    }
    @Test
    @Order(3)
    void selectTeamRecordRecordBySeasonIdAndTeamIdTest() throws SQLException {
        TeamRecord teamRecord1 = teamRecordService.getTeamRecordBySeasonIdAndTeamId(
                season.getId(), team.getId()).get();
        assertTrue(teamRecord1.getRecord().getId().equals(record.getId()));
        assertTrue(teamRecord1.getTeam().getId().equals(teamRecord.getTeam().getId()));
        assertTrue(teamRecord1.getSeason().getId().equals(season.getId()));
    }
    @Test
    @Order(4)
    void updateTeamRecordRecordBySeasonIdAndTeamIdTest() throws SQLException {
        updatedTeamRecord = teamRecordService.updateTeamRecord(
                updatedTeamRecord);
    }
    @Test
    @Order(5)
    void selectUpdatedTeamRecordRecordBySeasonIdAndTeamId() throws SQLException {
        TeamRecord teamRecordRecord1 = teamRecordService.getTeamRecordBySeasonIdAndTeamId(
                season.getId(), team.getId()).get();
        assertTrue(teamRecordRecord1.getRecord().getId().equals(record.getId()));
        assertTrue(teamRecordRecord1.getTeam().getId().equals(team.getId()));
        assertTrue(teamRecordRecord1.getSeason().getId().equals(season.getId()));
    }
    @Test
    @Order(6)
    void deleteTeamRecordRecordBySeasonIdAndTeamIdTest() throws SQLException {
        teamRecordService.deleteTeamRecord(
                teamRecord.getId());

        recordService.deleteRecord(record.getId());

        teamService.deleteTeam(
                team.getId());

        gameTypeService.deleteGameType(
                gameType.getId());

        levelOfCompetitionService.deleteLevelOfCompetition(
                levelOfCompetition.getId());

        streetAddressService.deleteStreetAddress(
                streetAddress.getId());

        cityService.deleteCity(
                city.getId());

        stateService.deleteState(
                state.getId());

        zipCodeService.deleteZipCode(
                zipCode.getId());

        countryService.deleteCountry(
                country.getId());

        locationService.deleteLocation(
                location.getId());

        seasonService.deleteSeason(
                season.getId());

        seasonService.deleteSeason(
                firstSeason.getId());

        seasonService.deleteSeason(
                lastSeason.getId());

        teamTypeService.deleteTeamType(
                teamType.getId());
    }

}
