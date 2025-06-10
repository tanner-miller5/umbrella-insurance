package com.umbrella.insurance.core.models.games.v1.db;

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
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GamesTableTests {
    private static Timestamp dateTime = Timestamp.valueOf("2023-12-12 12:12:10");
    private static String gameName = "1";
    private static Timestamp updatedDateTime = Timestamp.valueOf("2024-11-11 11:11:00");
    private static String updatedGameName = "11";

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
    private static Game updatedGame = new Game();
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

        game.setDateTime(dateTime);
        game.setGameName(gameName);
        updatedGame.setDateTime(updatedDateTime);
        updatedGame.setGameName(updatedGameName);

    }

    @Autowired
    StreetAddressService streetAddressService;

    @Autowired
    CityService cityService;

    @Autowired
    StateService stateService;

    @Autowired
    ZipCodeService zipCodeService;

    @Autowired
    CountryService countryService;

    @Autowired
    LocationService locationService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private SeasonTypeService seasonTypeService;

    @Autowired
    private GameService gameService;

    @Test
    @Order(6)
    void insertGameTest() {
        Connection connection = null;
        try {
            connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);

            streetAddress = streetAddressService.saveStreetAddress(streetAddress);
            location.setStreetAddress(streetAddress);

            city = cityService.saveCity(city);
            location.setCity(city);

            state = stateService.saveState(state);
            location.setState(state);

            zipCode = zipCodeService.saveZipCode(zipCode);
            location.setZipCode(zipCode);

            countryService.saveCountry(country);
            location.setCountry(country);

            location = locationService.saveLocation(location);
            game.setLocation(location);
            updatedGame.setLocation(location);

            gameStatus = gameStatusService.saveGameStatus(gameStatus);
            game.setGameStatus(gameStatus);
            updatedGame.setGameStatus(gameStatus);

            gameType = gameTypeService.saveGameType(gameType);
            game.setGameType(gameType);
            updatedGame.setGameType(gameType);

            seasonType = seasonTypeService.saveSeasonType(seasonType);
            game.setSeasonType(seasonType);
            updatedGame.setSeasonType(seasonType);

            gameStatus = gameStatusService.saveGameStatus(gameStatus);
            game.setGameStatus(gameStatus);
            updatedGame.setGameStatus(gameStatus);

            game = gameService.saveGame(game);
            updatedGame.setId(game.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
    @Test
    @Order(7)
    void selectGameByGameNameTest() {
        Connection connection = null;
        try {
            connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
            Game game1 = gameService.getGame(
                    gameName).get();
            assertTrue(game1.getGameName().equals(gameName));
            assertTrue(game1.getDateTime().equals(dateTime));
            assertTrue(game1.getGameStatus().getId().equals(gameStatus.getId()));
            assertTrue(game1.getGameType().getId().equals(gameType.getId()));
            assertTrue(game1.getLocation().getId().equals(location.getId()));
            assertTrue(game1.getSeasonType().getId().equals(seasonType.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
    @Test
    @Order(8)
    void updateGameByGameNameTest() {
        Connection connection = null;
        try {
            connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
            updatedGame = gameService.updateGame(updatedGame);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
    @Test
    @Order(9)
    void selectUpdatedGameByGameNameTest() {
        Connection connection = null;
        try {
            connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
            Game game1 = gameService.getGame(
                    updatedGameName).get();
            assertTrue(game1.getGameName().equals(updatedGameName));
            assertTrue(game1.getDateTime().equals(updatedDateTime));
            assertTrue(game1.getGameStatus().getId().equals(gameStatus.getId()));
            assertTrue(game1.getGameType().getId().equals(gameType.getId()));
            assertTrue(game1.getLocation().getId().equals(location.getId()));
            assertTrue(game1.getSeasonType().getId().equals(seasonType.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }
    @Test
    @Order(10)
    void deleteGameByGameNameTest() {
        Connection connection = null;
        try {
            connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
            streetAddressService.deleteStreetAddress(
                    streetAddress.getId());

            cityService.deleteCity(
                    city.getId());

            stateService.deleteState(
                    state.getId());

            zipCodeService.deleteZipCode(zipCode.getId());

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

            gameService.deleteGame(
                    updatedGame.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } finally {
            Database.closeConnection(connection);
        }
    }

}
