package com.umbrella.insurance.core.models.schedules.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.games.gameStatuses.v1.db.jpa.GameStatusService;
import com.umbrella.insurance.core.models.games.v1.db.jpa.GameService;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
import com.umbrella.insurance.core.models.schedules.v1.db.jpa.ScheduleService;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SchedulesTableTests {
    private static Timestamp dateTime = Timestamp.valueOf("2023-12-12 12:12:10");
    private static String gameName = "1";
    private static Season season = new Season();
    private static StreetAddress streetAddress = new StreetAddress();
    private static City city = new City();
    private static State state = new State();
    private static ZipCode zipCode = new ZipCode();
    private static Country country = new Country();
    private static Location location = new Location();
    private static GameStatus gameStatus = new GameStatus();
    private static GameType gameType = new GameType();
    private static SeasonType seasonType = new SeasonType();
    private static Game game = new Game();
    private static Schedule schedule = new Schedule();
    private static Schedule updatedSchedule = new Schedule();
    static {
        season.setEndDate(Date.valueOf("1116-11-11"));
        season.setStartDate(Date.valueOf("2002-10-10"));
        season.setSeasonName("6");

        location.setLocationName("1");
        streetAddress.setStreetAddressLine1("2");
        streetAddress.setStreetAddressLine2("3");
        city.setCityName("4");
        state.setStateName("5");
        zipCode.setZipCodeValue("6");
        country.setCountryName("7");

        gameStatus.setGameStatusName("6");
        gameType.setGameTypeName("7");
        seasonType.setSeasonTypeName("8");

        game.setDateTime(dateTime);
        game.setGameName(gameName);
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
    StreetAddressService streetAddressService;

    @Autowired
    CityService cityService;

    @Autowired
    StateService stateService;

    @Autowired
    CountryService countryService;

    @Autowired
    GameTypeService gameTypeService;

    @Autowired
    SeasonTypeService seasonTypeService;

    @Autowired
    GameStatusService gameStatusService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    LocationService locationService;

    @Autowired
    ZipCodeService zipCodeService;

    @Autowired
    GameService gameService;


    @Test
    @Order(2)
    void insertScheduleTest() throws SQLException {
        streetAddress = streetAddressService.saveStreetAddress(streetAddress);
        location.setStreetAddress(streetAddress);

        season = seasonService.saveSeason(season);
        schedule.setSeason(season);
        updatedSchedule.setSeason(season);

        city = cityService.saveCity(city);
        location.setCity(city);

        state = stateService.saveState(state);
        location.setState(state);

        zipCode = zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);

        country = countryService.saveCountry(country);
        location.setCountry(country);

        location = locationService.saveLocation(location);
        game.setLocation(location);

        gameStatus = gameStatusService.saveGameStatus(gameStatus);
        game.setGameStatus(gameStatus);

        gameType = gameTypeService.saveGameType(gameType);
        game.setGameType(gameType);

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        schedule.setGame(game);
        updatedSchedule.setGame(game);

        schedule = scheduleService.saveSchedule(schedule);
        updatedSchedule.setId(schedule.getId());
    }
    @Test
    @Order(3)
    void selectScheduleByGameIdTest() throws SQLException {
        Schedule schedule1 = scheduleService
                .getScheduleByGameId(game.getId()).get();
        assertTrue(schedule1.getGame().getId().equals(game.getId()));
        assertTrue(schedule1.getSeason().getId().equals(season.getId()));
    }
    @Test
    @Order(4)
    void updateScheduleByGameIdTest() throws SQLException {
        updatedSchedule = scheduleService.updateSchedule(
                updatedSchedule);
    }
    @Test
    @Order(5)
    void selectUpdatedScheduleByGameIdTest() throws SQLException {
        Schedule schedule1 = scheduleService.getScheduleByGameId(
                game.getId()).get();
        assertTrue(schedule1.getGame().getId().equals(game.getId()));
        assertTrue(schedule1.getSeason().getId().equals(season.getId()));
    }
    @Test
    @Order(6)
    void deleteScheduleByGameIdTest() throws SQLException {
        scheduleService.deleteSchedule(updatedSchedule.getId());

        seasonService.deleteSeason(
                season.getId());

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

        gameStatusService.deleteGameStatus(
                gameStatus.getId());

        gameTypeService.deleteGameType(
                gameType.getId());

        seasonTypeService.deleteSeasonType(
                seasonType.getId());

        gameService.deleteGame(game.getId());
    }
}
