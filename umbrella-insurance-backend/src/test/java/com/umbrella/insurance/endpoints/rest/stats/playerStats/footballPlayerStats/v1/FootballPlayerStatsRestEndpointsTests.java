package com.umbrella.insurance.endpoints.rest.stats.playerStats.footballPlayerStats.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
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
import com.umbrella.insurance.core.models.schedules.v1.db.jpa.ScheduleService;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.FootballPlayerStatsPrivilege;
import com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.jpa.FootballPlayerStatsService;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa.TeamTypeService;
import com.umbrella.insurance.core.models.teams.v1.db.jpa.TeamService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.endpoints.rest.users.v1.requests.CreateUserRequest;
import com.umbrella.insurance.endpoints.rest.users.v1.responses.CreateUserResponse;
import com.umbrella.insurance.website.WebsiteApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FootballPlayerStatsRestEndpointsTests {
    @Autowired
    private FootballPlayerStatsRestEndpoints footballPlayerStatsRestEndpoints;
    @Autowired
    private SeasonTypeService seasonTypeService;

    @Test
    void contextLoads() throws Exception {
        assertThat(footballPlayerStatsRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress2 = "1555";
    private static String phoneNumber2 = "255";
    private static String username2 = "3555";
    private static String password2 = "5555";
    private static String ssn2 = "12345";
    private static String surname2 = "1225";
    private static String middle2 = "middle5";
    private static String first2 = "first5";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-12");

    private static Long footballPlayerStatsId;
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
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    static {
        createUserRequest.setPassword(password2);
        createUserRequest.setUsername(username2);
        createUserRequest.setSsn(ssn2);
        createUserRequest.setPhoneNumber(phoneNumber2);
        createUserRequest.setSurname(surname2);
        createUserRequest.setMiddleName(middle2);
        createUserRequest.setFirstName(first2);
        createUserRequest.setEmailAddress(emailAddress2);
        createUserRequest.setDateOfBirth(dateOfBirth2);
        createUserRequest.setConsentedToEmails(false);
        createUserRequest.setConsentedToTexts(false);
        createUserRequest.setConsentedToTermsAndConditions(true);

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
        updatedFootballPlayerStats.setLongestPuntYards(4211l);
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
    @BeforeEach
    public void beforeEach() throws SQLException {
    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        Database.closeConnection(connection);
    }

    @Autowired
    private SessionService sessionService;

    @Autowired
    private FootballPlayerStatsService footballPlayerStatsService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private TeamTypeService teamTypeService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Test
    @Order(1)
    void createFootballPlayerStats() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");

        String createUserUrl = hostname + port
                + UserPrivilege.SOA_PATH
                + "?env=" + TestConfig.testEnv.toString();
        HttpEntity<CreateUserRequest> cuRequest = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<CreateUserResponse> createUserResponse = this.restTemplate.exchange(
                createUserUrl, HttpMethod.POST, cuRequest, CreateUserResponse.class);

        session.setSessionCode(createUserResponse.getBody().getSessionCode());

        session = sessionService.getSessionBySessionCode(session.getSessionCode()).get();
        assertTrue(session != null);
        User user = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        personService.savePerson(person);
        player.setPerson(person);

        gameTypeService.saveGameType(gameType);
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

        headers.set("session", session.getSessionCode());
        HttpEntity<FootballPlayerStats> request = new HttpEntity<>(footballPlayerStats, headers);

        String url = hostname + port
                + FootballPlayerStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<FootballPlayerStats> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, FootballPlayerStats.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        FootballPlayerStats footballPlayerStats1 = response.getBody();
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
        footballPlayerStatsId = footballPlayerStats1.getId();
        updatedFootballPlayerStats.setId(footballPlayerStatsId);
    }

    @Test
    @Order(2)
    void readFootballPlayerStatsByFootballPlayerStatsName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + FootballPlayerStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&gameId=" + game.getId()
                + "&playerId=" + player.getId();
        ResponseEntity<FootballPlayerStats[]> footballPlayerStatsList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, FootballPlayerStats[].class);
        assertTrue(footballPlayerStatsList.getStatusCode().is2xxSuccessful());
        FootballPlayerStats footballPlayerStats1 = footballPlayerStatsList.getBody()[0];
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
    @Order(3)
    void readFootballPlayerStatsByFootballPlayerStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<FootballPlayerStats[]> footballPlayerStatsList = this.restTemplate.exchange(
                hostname + port + FootballPlayerStatsPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&footballPlayerStatsId=" + footballPlayerStatsId, HttpMethod.GET, request,
                FootballPlayerStats[].class);
        assertTrue(footballPlayerStatsList.getStatusCode().is2xxSuccessful());
        FootballPlayerStats footballPlayerStats1 = footballPlayerStatsList.getBody()[0];
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
    void updateFootballPlayerStatsByFootballPlayerStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<FootballPlayerStats> request = new HttpEntity<>(updatedFootballPlayerStats, headers);

        String url = hostname + port
                + FootballPlayerStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&footballPlayerStatsId=" + footballPlayerStatsId;
        URI uri = new URI(url);
        ResponseEntity<FootballPlayerStats[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT,
                request, FootballPlayerStats[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedFootballPlayerStatsByFootballPlayerStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<FootballPlayerStats[]> footballPlayerStatsList = this.restTemplate.exchange(
                hostname + port
                        + FootballPlayerStatsPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&footballPlayerStatsId=" + footballPlayerStatsId, HttpMethod.GET,
                request, FootballPlayerStats[].class);
        assertTrue(footballPlayerStatsList.getStatusCode().is2xxSuccessful());
        FootballPlayerStats footballPlayerStats1 = footballPlayerStatsList.getBody()[0];
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
        footballPlayerStatsId = footballPlayerStats1.getId();
    }

    @Test
    @Order(6)
    void deleteFootballPlayerStatsByFootballPlayerStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + FootballPlayerStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&footballPlayerStatsId=" + footballPlayerStatsId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        playerService.deleteByPersonId(
                person.getId());

        personService.deletePersonBySsn(
                person.getSsn());

        streetAddressService.deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                streetAddress.getStreetAddressLine1(),
                streetAddress.getStreetAddressLine2());

        cityService.deleteCityByCityName(
                city.getCityName());

        stateService.deleteStateByStateName(
                state.getStateName());

        zipCodeService.deleteZipCodeByZipCodeValue(
                zipCode.getZipCodeValue());

        countryService.deleteCountryByCountryName(
                country.getCountryName());

        locationService.deleteByLocationName(
                location.getLocationName());

        gameStatusService.deleteByGameStatusName(
                gameStatus.getGameStatusName());

        gameTypeService.deleteByGameTypeName(
                gameType.getGameTypeName());

        seasonTypeService.deleteSeasonTypeBySeasonTypeName(
                seasonType.getSeasonTypeName());

        gameService.deleteGameByGameName(
                game.getGameName());

        sessionService.deleteSession(
                session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn2);
    }
}
