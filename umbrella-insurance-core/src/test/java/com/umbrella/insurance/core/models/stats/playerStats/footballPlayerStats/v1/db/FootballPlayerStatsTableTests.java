package com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db;

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
import com.umbrella.insurance.core.models.people.players.v1.db.jpa.PlayerService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.jpa.FootballPlayerStatsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FootballPlayerStatsTableTests {
    private static String height = "1";
    private static String weight = "2";
    private static String college = "3";
    private static String draftInfo = "4";
    private static String playerStatus="5";
    private static String jerseyNumber="6";
    private static String position="7";
    private static String experience = "8";
    private static String birthPlace = "9";
    private static Timestamp dateTime = Timestamp.valueOf("2023-12-12 12:12:10");
    private static String gameName = "1";

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
    private static Person person = new Person();
    private static Player player = new Player();
    private static FootballPlayerStats footballPlayerStats = new FootballPlayerStats();
    private static FootballPlayerStats updatedFootballPlayerStats = new FootballPlayerStats();

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

        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1950-11-13"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        player.setHeight(height);
        player.setWeight(weight);
        player.setCollege(college);
        player.setDraftInfo(draftInfo);
        player.setPlayerStatus(playerStatus);
        player.setJerseyNumber(jerseyNumber);
        player.setPlayerPosition(position);
        player.setExperience(experience);
        player.setBirthplace(birthPlace);

        footballPlayerStats.setTotalTackles(3l);
        footballPlayerStats.setSoloTackles(4l);
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        footballPlayerStats.setTotalSacks(5l);
        footballPlayerStats.setTacklesForLoss(6l);
        footballPlayerStats.setPassesDefended(7l);
        footballPlayerStats.setQuarterbackHits(8l);
        footballPlayerStats.setDefensiveTouchdowns(9l);
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        footballPlayerStats.setTotalFumbles(10l);
        footballPlayerStats.setFumblesLost(11l);
        footballPlayerStats.setFumblesRecovered(12l);
        footballPlayerStats.setInterceptions(13l);
        footballPlayerStats.setInterceptionYards(14l);
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        footballPlayerStats.setInterceptionTouchdowns(15l);
        footballPlayerStats.setNumberOfFieldGoals(16l);
        footballPlayerStats.setFieldGoalPercentage(17l);
        footballPlayerStats.setLongestFieldGoal(18l);
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        footballPlayerStats.setExtraPoints(19l);
        footballPlayerStats.setExtraPointAttempts(20l);
        footballPlayerStats.setPoints(21l);
        footballPlayerStats.setNumberOfKickReturns(22l);
        footballPlayerStats.setTotalKickReturnYards(23l);
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        footballPlayerStats.setNumberOfYardsPerKickReturn(Double.valueOf(24));
        footballPlayerStats.setLongestKickReturnYards(25l);
        footballPlayerStats.setKickReturnTouchdowns(26l);
        footballPlayerStats.setCompletions(27l);
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        footballPlayerStats.setPassingAttempts(28l);
        footballPlayerStats.setPassingYards(29l);
        footballPlayerStats.setYardsPerPassAttempt(Double.valueOf(30));
        footballPlayerStats.setPassingTouchdowns(31l);
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        footballPlayerStats.setInterceptionsThrown(32l);
        footballPlayerStats.setNumberOfTimesSacked(33l);
        footballPlayerStats.setSackedYardsLost(34l);
        footballPlayerStats.setPasserRating(Double.valueOf(35));
        footballPlayerStats.setAdjustedQbr(Double.valueOf(36));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        footballPlayerStats.setNumberOfPunts(37l);
        footballPlayerStats.setTotalPuntYards(38l);
        footballPlayerStats.setPuntYardsPerPunt(Double.valueOf(39));
        footballPlayerStats.setTouchbacks(40l);
        footballPlayerStats.setIn20(41l);
        footballPlayerStats.setLongestPuntYards(42l);
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        footballPlayerStats.setNumberOfPuntReturns(43l);
        footballPlayerStats.setTotalPuntReturnYards(44l);
        footballPlayerStats.setPuntReturnYardsPerPuntReturn(Double.valueOf(45));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        footballPlayerStats.setLongestPuntReturnYards(46l);
        footballPlayerStats.setPuntReturnTouchdowns(47l);
        footballPlayerStats.setTotalReceptions(48l);
        footballPlayerStats.setTotalReceivingYards(49l);
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        footballPlayerStats.setAverageYardsPerReception(Double.valueOf(50));
        footballPlayerStats.setReceivingTouchdowns(51l);
        footballPlayerStats.setLongestReception(52l);
        footballPlayerStats.setReceivingTargets(53l);
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        footballPlayerStats.setRushingAttempts(54l);
        footballPlayerStats.setAverages(Double.valueOf(55));
        footballPlayerStats.setRushingTouchdowns(56l);
        footballPlayerStats.setLongestRun(57l);


        updatedFootballPlayerStats.setTotalTackles(311l);
        updatedFootballPlayerStats.setSoloTackles(411l);
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        updatedFootballPlayerStats.setTotalSacks(511l);
        updatedFootballPlayerStats.setTacklesForLoss(611l);
        updatedFootballPlayerStats.setPassesDefended(711l);
        updatedFootballPlayerStats.setQuarterbackHits(811l);
        updatedFootballPlayerStats.setDefensiveTouchdowns(911l);
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        updatedFootballPlayerStats.setTotalFumbles(1011l);
        updatedFootballPlayerStats.setFumblesLost(1111l);
        updatedFootballPlayerStats.setFumblesRecovered(1211l);
        updatedFootballPlayerStats.setInterceptions(1311l);
        updatedFootballPlayerStats.setInterceptionYards(1411l);
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        updatedFootballPlayerStats.setInterceptionTouchdowns(1511l);
        updatedFootballPlayerStats.setNumberOfFieldGoals(1611l);
        updatedFootballPlayerStats.setFieldGoalPercentage(1711l);
        updatedFootballPlayerStats.setLongestFieldGoal(1811l);
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        updatedFootballPlayerStats.setExtraPoints(1911l);
        updatedFootballPlayerStats.setExtraPointAttempts(2011l);
        updatedFootballPlayerStats.setPoints(2111l);
        updatedFootballPlayerStats.setNumberOfKickReturns(2211l);
        updatedFootballPlayerStats.setTotalKickReturnYards(2311l);
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        updatedFootballPlayerStats.setNumberOfYardsPerKickReturn(Double.valueOf(2411));
        updatedFootballPlayerStats.setLongestKickReturnYards(2511l);
        updatedFootballPlayerStats.setKickReturnTouchdowns(2611l);
        updatedFootballPlayerStats.setCompletions(2711l);
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        updatedFootballPlayerStats.setPassingAttempts(2811l);
        updatedFootballPlayerStats.setPassingYards(2911l);
        updatedFootballPlayerStats.setYardsPerPassAttempt(Double.valueOf(3011));
        updatedFootballPlayerStats.setPassingTouchdowns(3111l);
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        updatedFootballPlayerStats.setInterceptionsThrown(3211l);
        updatedFootballPlayerStats.setNumberOfTimesSacked(3311l);
        updatedFootballPlayerStats.setSackedYardsLost(3411l);
        updatedFootballPlayerStats.setPasserRating(Double.valueOf(3511));
        updatedFootballPlayerStats.setAdjustedQbr(Double.valueOf(3611));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        updatedFootballPlayerStats.setNumberOfPunts(3711l);
        updatedFootballPlayerStats.setTotalPuntYards(3811l);
        updatedFootballPlayerStats.setPuntYardsPerPunt(Double.valueOf(3911));
        updatedFootballPlayerStats.setTouchbacks(4011l);
        updatedFootballPlayerStats.setIn20(4111l);
        updatedFootballPlayerStats.setLongestPuntYards(421l);
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        updatedFootballPlayerStats.setNumberOfPuntReturns(4311l);
        updatedFootballPlayerStats.setTotalPuntReturnYards(4411l);
        updatedFootballPlayerStats.setPuntReturnYardsPerPuntReturn(Double.valueOf(4511));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        updatedFootballPlayerStats.setLongestPuntReturnYards(4611l);
        updatedFootballPlayerStats.setPuntReturnTouchdowns(4711l);
        updatedFootballPlayerStats.setTotalReceptions(4811l);
        updatedFootballPlayerStats.setTotalReceivingYards(4911l);
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        updatedFootballPlayerStats.setAverageYardsPerReception(Double.valueOf(5011));
        updatedFootballPlayerStats.setReceivingTouchdowns(5111l);
        updatedFootballPlayerStats.setLongestReception(5211l);
        updatedFootballPlayerStats.setReceivingTargets(5311l);
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        updatedFootballPlayerStats.setRushingAttempts(5411l);
        updatedFootballPlayerStats.setAverages(Double.valueOf(5511));
        updatedFootballPlayerStats.setRushingTouchdowns(5611l);
        updatedFootballPlayerStats.setLongestRun(5711l);
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
    private FootballPlayerStatsService footballPlayerStatsService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private SeasonTypeService seasonTypeService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Test
    @Order(2)
    void insertFootballPlayerStatsTest() throws SQLException {
        person = personService.savePerson(person);
        player.setPerson(person);

        gameType = gameTypeService.saveGameType(gameType);
        player.setGameType(gameType);
        game.setGameType(gameType);

        player = playerService.savePlayer(player);
        footballPlayerStats.setPlayer(player);
        updatedFootballPlayerStats.setPlayer(player);

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

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        footballPlayerStats.setGame(game);
        updatedFootballPlayerStats.setGame(game);

        footballPlayerStatsService.saveFootballPlayerStats(footballPlayerStats);
        updatedFootballPlayerStats.setId(footballPlayerStats.getId());
    }
    @Test
    @Order(3)
    void selectFootballPlayerStatsByGameIdAndPlayerIdTest() throws SQLException {
        FootballPlayerStats footballPlayerStats1 =
                footballPlayerStatsService.getFootballPlayerStatsByGameIdAndPlayerId(
                game.getId(), player.getId()).get();
        assertTrue(footballPlayerStats1.getGame().getId().equals(footballPlayerStats.getGame().getId()));
        assertTrue(footballPlayerStats1.getPlayer().getId().equals(footballPlayerStats.getPlayer().getId()));
        assertTrue(footballPlayerStats1.getTotalTackles().equals(footballPlayerStats.getTotalTackles()));
        assertTrue(footballPlayerStats1.getSoloTackles().equals(footballPlayerStats.getSoloTackles()));
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        assertTrue(footballPlayerStats1.getTotalSacks().equals(footballPlayerStats.getTotalSacks()));
        assertTrue(footballPlayerStats1.getTacklesForLoss().equals(footballPlayerStats.getTacklesForLoss()));
        assertTrue(footballPlayerStats1.getPassesDefended().equals(footballPlayerStats.getPassesDefended()));
        assertTrue(footballPlayerStats1.getQuarterbackHits().equals(footballPlayerStats.getQuarterbackHits()));
        assertTrue(footballPlayerStats1.getDefensiveTouchdowns().equals(footballPlayerStats.getDefensiveTouchdowns()));
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        assertTrue(footballPlayerStats1.getTotalFumbles().equals(footballPlayerStats.getTotalFumbles()));
        assertTrue(footballPlayerStats1.getFumblesLost().equals(footballPlayerStats.getFumblesLost()));
        assertTrue(footballPlayerStats1.getFumblesRecovered().equals(footballPlayerStats.getFumblesRecovered()));
        assertTrue(footballPlayerStats1.getInterceptions().equals(footballPlayerStats.getInterceptions()));
        assertTrue(footballPlayerStats1.getInterceptionYards().equals(footballPlayerStats.getInterceptionYards()));
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        assertTrue(footballPlayerStats1.getInterceptionTouchdowns().equals(footballPlayerStats.getInterceptionTouchdowns()));
        assertTrue(footballPlayerStats1.getNumberOfFieldGoals().equals(footballPlayerStats.getNumberOfFieldGoals()));
        assertTrue(footballPlayerStats1.getFieldGoalPercentage().equals(footballPlayerStats.getFieldGoalPercentage()));
        assertTrue(footballPlayerStats1.getLongestFieldGoal().equals(footballPlayerStats.getLongestFieldGoal()));
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        assertTrue(footballPlayerStats1.getExtraPoints().equals(footballPlayerStats.getExtraPoints()));
        assertTrue(footballPlayerStats1.getExtraPointAttempts().equals(footballPlayerStats.getExtraPointAttempts()));
        assertTrue(footballPlayerStats1.getPoints().equals(footballPlayerStats.getPoints()));
        assertTrue(footballPlayerStats1.getNumberOfKickReturns().equals(footballPlayerStats.getNumberOfKickReturns()));
        assertTrue(footballPlayerStats1.getTotalKickReturnYards().equals(footballPlayerStats.getTotalKickReturnYards()));
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        assertTrue(footballPlayerStats1.getNumberOfYardsPerKickReturn().equals(footballPlayerStats.getNumberOfYardsPerKickReturn()));
        assertTrue(footballPlayerStats1.getLongestKickReturnYards().equals(footballPlayerStats.getLongestKickReturnYards()));
        assertTrue(footballPlayerStats1.getKickReturnTouchdowns().equals(footballPlayerStats.getKickReturnTouchdowns()));
        assertTrue(footballPlayerStats1.getCompletions().equals(footballPlayerStats.getCompletions()));
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        assertTrue(footballPlayerStats1.getPassingAttempts().equals(footballPlayerStats.getPassingAttempts()));
        assertTrue(footballPlayerStats1.getPassingYards().equals(footballPlayerStats.getPassingYards()));
        assertTrue(footballPlayerStats1.getYardsPerPassAttempt().equals(footballPlayerStats.getYardsPerPassAttempt()));
        assertTrue(footballPlayerStats1.getPassingTouchdowns().equals(footballPlayerStats.getPassingTouchdowns()));
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        assertTrue(footballPlayerStats1.getInterceptionsThrown().equals(footballPlayerStats.getInterceptionsThrown()));
        assertTrue(footballPlayerStats1.getNumberOfTimesSacked().equals(footballPlayerStats.getNumberOfTimesSacked()));
        assertTrue(footballPlayerStats1.getSackedYardsLost().equals(footballPlayerStats.getSackedYardsLost()));
        assertTrue(footballPlayerStats1.getPasserRating().equals(footballPlayerStats.getPasserRating()));
        assertTrue(footballPlayerStats1.getAdjustedQbr().equals(footballPlayerStats.getAdjustedQbr()));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        assertTrue(footballPlayerStats1.getNumberOfPunts().equals(footballPlayerStats.getNumberOfPunts()));
        assertTrue(footballPlayerStats1.getTotalPuntYards().equals(footballPlayerStats.getTotalPuntYards()));
        assertTrue(footballPlayerStats1.getPuntYardsPerPunt().equals(footballPlayerStats.getPuntYardsPerPunt()));
        assertTrue(footballPlayerStats1.getTouchbacks().equals(footballPlayerStats.getTouchbacks()));
        assertTrue(footballPlayerStats1.getIn20().equals(footballPlayerStats.getIn20()));
        assertTrue(footballPlayerStats1.getLongestPuntYards().equals(footballPlayerStats.getLongestPuntYards()));
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        assertTrue(footballPlayerStats1.getNumberOfPuntReturns().equals(footballPlayerStats.getNumberOfPuntReturns()));
        assertTrue(footballPlayerStats1.getTotalPuntReturnYards().equals(footballPlayerStats.getTotalPuntReturnYards()));
        assertTrue(footballPlayerStats1.getPuntReturnYardsPerPuntReturn().equals(footballPlayerStats.getPuntReturnYardsPerPuntReturn()));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        assertTrue(footballPlayerStats1.getLongestPuntReturnYards().equals(footballPlayerStats.getLongestPuntReturnYards()));
        assertTrue(footballPlayerStats1.getPuntReturnTouchdowns().equals(footballPlayerStats.getPuntReturnTouchdowns()));
        assertTrue(footballPlayerStats1.getTotalReceptions().equals(footballPlayerStats.getTotalReceptions()));
        assertTrue(footballPlayerStats1.getTotalReceivingYards().equals(footballPlayerStats.getTotalReceivingYards()));
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        assertTrue(footballPlayerStats1.getAverageYardsPerReception().equals(footballPlayerStats.getAverageYardsPerReception()));
        assertTrue(footballPlayerStats1.getReceivingTouchdowns().equals(footballPlayerStats.getReceivingTouchdowns()));
        assertTrue(footballPlayerStats1.getLongestReception().equals(footballPlayerStats.getLongestReception()));
        assertTrue(footballPlayerStats1.getReceivingTargets().equals(footballPlayerStats.getReceivingTargets()));
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        assertTrue(footballPlayerStats1.getRushingAttempts().equals(footballPlayerStats.getRushingAttempts()));
        assertTrue(footballPlayerStats1.getAverages().equals(footballPlayerStats.getAverages()));
        assertTrue(footballPlayerStats1.getRushingTouchdowns().equals(footballPlayerStats.getRushingTouchdowns()));
        assertTrue(footballPlayerStats1.getLongestRun().equals(footballPlayerStats.getLongestRun()));
    }
    @Test
    @Order(4)
    void updateFootballPlayerStatsByGameIdAndPlayerIdTest() throws SQLException {
        updatedFootballPlayerStats = footballPlayerStatsService.updateFootballPlayerStats(
                updatedFootballPlayerStats);
    }
    @Test
    @Order(5)
    void selectUpdatedFootballPlayerStatsByGameIdAndPlayerIdTest() throws SQLException {
        FootballPlayerStats footballPlayerStats1 =
                footballPlayerStatsService.getFootballPlayerStatsByGameIdAndPlayerId(
                game.getId(), player.getId()).get();
        assertTrue(footballPlayerStats1.getGame().getId().equals(updatedFootballPlayerStats.getGame().getId()));
        assertTrue(footballPlayerStats1.getPlayer().getId().equals(updatedFootballPlayerStats.getPlayer().getId()));
        assertTrue(footballPlayerStats1.getTotalTackles().equals(updatedFootballPlayerStats.getTotalTackles()));
        assertTrue(footballPlayerStats1.getSoloTackles().equals(updatedFootballPlayerStats.getSoloTackles()));
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        assertTrue(footballPlayerStats1.getTotalSacks().equals(updatedFootballPlayerStats.getTotalSacks()));
        assertTrue(footballPlayerStats1.getTacklesForLoss().equals(updatedFootballPlayerStats.getTacklesForLoss()));
        assertTrue(footballPlayerStats1.getPassesDefended().equals(updatedFootballPlayerStats.getPassesDefended()));
        assertTrue(footballPlayerStats1.getQuarterbackHits().equals(updatedFootballPlayerStats.getQuarterbackHits()));
        assertTrue(footballPlayerStats1.getDefensiveTouchdowns().equals(updatedFootballPlayerStats.getDefensiveTouchdowns()));
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        assertTrue(footballPlayerStats1.getTotalFumbles().equals(updatedFootballPlayerStats.getTotalFumbles()));
        assertTrue(footballPlayerStats1.getFumblesLost().equals(updatedFootballPlayerStats.getFumblesLost()));
        assertTrue(footballPlayerStats1.getFumblesRecovered().equals(updatedFootballPlayerStats.getFumblesRecovered()));
        assertTrue(footballPlayerStats1.getInterceptions().equals(updatedFootballPlayerStats.getInterceptions()));
        assertTrue(footballPlayerStats1.getInterceptionYards().equals(updatedFootballPlayerStats.getInterceptionYards()));
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        assertTrue(footballPlayerStats1.getInterceptionTouchdowns().equals(updatedFootballPlayerStats.getInterceptionTouchdowns()));
        assertTrue(footballPlayerStats1.getNumberOfFieldGoals().equals(updatedFootballPlayerStats.getNumberOfFieldGoals()));
        assertTrue(footballPlayerStats1.getFieldGoalPercentage().equals(updatedFootballPlayerStats.getFieldGoalPercentage()));
        assertTrue(footballPlayerStats1.getLongestFieldGoal().equals(updatedFootballPlayerStats.getLongestFieldGoal()));
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        assertTrue(footballPlayerStats1.getExtraPoints().equals(updatedFootballPlayerStats.getExtraPoints()));
        assertTrue(footballPlayerStats1.getExtraPointAttempts().equals(updatedFootballPlayerStats.getExtraPointAttempts()));
        assertTrue(footballPlayerStats1.getPoints().equals(updatedFootballPlayerStats.getPoints()));
        assertTrue(footballPlayerStats1.getNumberOfKickReturns().equals(updatedFootballPlayerStats.getNumberOfKickReturns()));
        assertTrue(footballPlayerStats1.getTotalKickReturnYards().equals(updatedFootballPlayerStats.getTotalKickReturnYards()));
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        assertTrue(footballPlayerStats1.getNumberOfYardsPerKickReturn().equals(updatedFootballPlayerStats.getNumberOfYardsPerKickReturn()));
        assertTrue(footballPlayerStats1.getLongestKickReturnYards().equals(updatedFootballPlayerStats.getLongestKickReturnYards()));
        assertTrue(footballPlayerStats1.getKickReturnTouchdowns().equals(updatedFootballPlayerStats.getKickReturnTouchdowns()));
        assertTrue(footballPlayerStats1.getCompletions().equals(updatedFootballPlayerStats.getCompletions()));
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        assertTrue(footballPlayerStats1.getPassingAttempts().equals(updatedFootballPlayerStats.getPassingAttempts()));
        assertTrue(footballPlayerStats1.getPassingYards().equals(updatedFootballPlayerStats.getPassingYards()));
        assertTrue(footballPlayerStats1.getYardsPerPassAttempt().equals(updatedFootballPlayerStats.getYardsPerPassAttempt()));
        assertTrue(footballPlayerStats1.getPassingTouchdowns().equals(updatedFootballPlayerStats.getPassingTouchdowns()));
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        assertTrue(footballPlayerStats1.getInterceptionsThrown().equals(updatedFootballPlayerStats.getInterceptionsThrown()));
        assertTrue(footballPlayerStats1.getNumberOfTimesSacked().equals(updatedFootballPlayerStats.getNumberOfTimesSacked()));
        assertTrue(footballPlayerStats1.getSackedYardsLost().equals(updatedFootballPlayerStats.getSackedYardsLost()));
        assertTrue(footballPlayerStats1.getPasserRating().equals(updatedFootballPlayerStats.getPasserRating()));
        assertTrue(footballPlayerStats1.getAdjustedQbr().equals(updatedFootballPlayerStats.getAdjustedQbr()));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        assertTrue(footballPlayerStats1.getNumberOfPunts().equals(updatedFootballPlayerStats.getNumberOfPunts()));
        assertTrue(footballPlayerStats1.getTotalPuntYards().equals(updatedFootballPlayerStats.getTotalPuntYards()));
        assertTrue(footballPlayerStats1.getPuntYardsPerPunt().equals(updatedFootballPlayerStats.getPuntYardsPerPunt()));
        assertTrue(footballPlayerStats1.getTouchbacks().equals(updatedFootballPlayerStats.getTouchbacks()));
        assertTrue(footballPlayerStats1.getIn20().equals(updatedFootballPlayerStats.getIn20()));
        assertTrue(footballPlayerStats1.getLongestPuntYards().equals(updatedFootballPlayerStats.getLongestPuntYards()));
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        assertTrue(footballPlayerStats1.getNumberOfPuntReturns().equals(updatedFootballPlayerStats.getNumberOfPuntReturns()));
        assertTrue(footballPlayerStats1.getTotalPuntReturnYards().equals(updatedFootballPlayerStats.getTotalPuntReturnYards()));
        assertTrue(footballPlayerStats1.getPuntReturnYardsPerPuntReturn().equals(updatedFootballPlayerStats.getPuntReturnYardsPerPuntReturn()));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        assertTrue(footballPlayerStats1.getLongestPuntReturnYards().equals(updatedFootballPlayerStats.getLongestPuntReturnYards()));
        assertTrue(footballPlayerStats1.getPuntReturnTouchdowns().equals(updatedFootballPlayerStats.getPuntReturnTouchdowns()));
        assertTrue(footballPlayerStats1.getTotalReceptions().equals(updatedFootballPlayerStats.getTotalReceptions()));
        assertTrue(footballPlayerStats1.getTotalReceivingYards().equals(updatedFootballPlayerStats.getTotalReceivingYards()));
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        assertTrue(footballPlayerStats1.getAverageYardsPerReception().equals(updatedFootballPlayerStats.getAverageYardsPerReception()));
        assertTrue(footballPlayerStats1.getReceivingTouchdowns().equals(updatedFootballPlayerStats.getReceivingTouchdowns()));
        assertTrue(footballPlayerStats1.getLongestReception().equals(updatedFootballPlayerStats.getLongestReception()));
        assertTrue(footballPlayerStats1.getReceivingTargets().equals(updatedFootballPlayerStats.getReceivingTargets()));
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        assertTrue(footballPlayerStats1.getRushingAttempts().equals(updatedFootballPlayerStats.getRushingAttempts()));
        assertTrue(footballPlayerStats1.getAverages().equals(updatedFootballPlayerStats.getAverages()));
        assertTrue(footballPlayerStats1.getRushingTouchdowns().equals(updatedFootballPlayerStats.getRushingTouchdowns()));
        assertTrue(footballPlayerStats1.getLongestRun().equals(updatedFootballPlayerStats.getLongestRun()));
    }
    @Test
    @Order(6)
    void deleteFootballPlayerStatsByGameIdAndPlayerIdTest() throws SQLException {
        footballPlayerStatsService.deleteFootballPlayerStats(updatedFootballPlayerStats.getId());

        playerService.deletePlayer(
                player.getId());

        personService.deletePerson(
                person.getId());

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

        gameService.deleteGame(
                game.getId());
    }

}
