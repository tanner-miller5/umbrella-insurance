package com.umbrella.insurance.core.models.periods.v1.db;

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
import com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa.PeriodStatusService;
import com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa.PeriodTypeService;
import com.umbrella.insurance.core.models.periods.v1.db.jpa.PeriodService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeriodsTableTests {

    private static Long periodNumber = 11l;
    private static Long updatedPeriodNumber = 22l;

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
    private static PeriodType periodType = new PeriodType();
    private static PeriodStatus periodStatus = new PeriodStatus();
    private static Period period = new Period();
    private static Period updatedPeriod = new Period();
    static {
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

        game.setDateTime(Timestamp.valueOf("1111-11-11 11:11:11"));
        game.setGameName("9");

        periodType.setPeriodTypeName("10");
        periodStatus.setPeriodStatusName("11");

        period.setPeriodNumber(periodNumber);

        updatedPeriod.setPeriodNumber(updatedPeriodNumber);
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
    private PeriodService periodService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private SeasonTypeService seasonTypeService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PeriodTypeService periodTypeService;

    @Autowired
    private PeriodStatusService periodStatusService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Test
    @Order(5)
    void insertPeriodTest() throws SQLException {
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
        game.setLocation(location);

        gameStatus = gameStatusService.saveGameStatus(gameStatus);
        game.setGameStatus(gameStatus);

        gameType = gameTypeService.saveGameType(gameType);
        game.setGameType(gameType);

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        period.setGame(game);
        updatedPeriod.setGame(game);

        periodStatus = periodStatusService.savePeriodStatus(
                periodStatus);
        period.setPeriodStatus(periodStatus);
        updatedPeriod.setPeriodStatus(periodStatus);

        periodType = periodTypeService.savePeriodType(
                periodType);
        period.setPeriodType(periodType);
        updatedPeriod.setPeriodType(periodType);

        period = periodService.savePeriod(period);
        updatedPeriod.setId(period.getId());
    }
    @Test
    @Order(6)
    void selectPeriodByGameIdAndPeriodNumberTest() throws SQLException {
        Period period1 = periodService.getPeriodByGameIdAndPeriodNumber(
                game.getId(),
                periodNumber).get();
        assertTrue(period1.getGame().getId().equals(game.getId()));
        assertTrue(period1.getPeriodNumber().equals(periodNumber));
        assertTrue(period1.getPeriodStatus().getId().equals(periodStatus.getId()));
        assertTrue(period1.getPeriodType().getId().equals(periodType.getId()));
    }
    @Test
    @Order(7)
    void updatePeriodByGameIdAndPeriodNumberTest() throws SQLException {
        updatedPeriod = periodService.updatePeriod(updatedPeriod);
    }
    @Test
    @Order(8)
    void selectUpdatedPeriodByGameIdAndPeriodNumberTest() throws SQLException {
        Period period1 = periodService.getPeriodByGameIdAndPeriodNumber(
                game.getId(),
                updatedPeriodNumber).get();
        assertTrue(period1.getGame().getId().equals(game.getId()));
        assertTrue(period1.getPeriodNumber().equals(updatedPeriodNumber));
        assertTrue(period1.getPeriodStatus().getId().equals(periodStatus.getId()));
        assertTrue(period1.getPeriodType().getId().equals(periodType.getId()));
    }
    @Test
    @Order(9)
    void deletePeriodByGameIdAndPeriodNumberTest() throws SQLException {
        periodService.deletePeriod(
                period.getId());

        periodStatusService.deletePeriodStatus(
                periodStatus.getId());

        periodTypeService.deletePeriodType(
                periodType.getId());

        streetAddressService.deleteStreetAddress(streetAddress.getId());

        cityService.deleteCity(city.getId());

        stateService.deleteState(
                state.getId());

        zipCodeService.deleteZipCode(
                zipCode.getId());

        countryService.deleteCountry(country.getId());

        locationService.deleteLocation(location.getId());

        gameStatusService.deleteGameStatus(
                gameStatus.getId());

        gameTypeService.deleteGameType(gameType.getId());

        seasonTypeService.deleteSeasonType(seasonType.getId());

        gameService.deleteGame(game.getId());
    }

}
