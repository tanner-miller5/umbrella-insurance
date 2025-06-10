package com.umbrella.insurance.endpoints.rest.stats.teamStats.footballTeamStats.v1;

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
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa.LevelOfCompetitionService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.FootballTeamStatsPrivilege;
import com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.jpa.FootballTeamStatsService;
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
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FootballTeamStatsRestEndpointsTests {
    @Autowired
    private FootballTeamStatsRestEndpoints footballTeamStatsRestEndpoints;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private StreetAddressService streetAddressService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ZipCodeService zipCodeService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() throws Exception {
        assertThat(footballTeamStatsRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress2 = "14";
    private static String phoneNumber2 = "24";
    private static String username2 = "34";
    private static String password2 = "54";
    private static String ssn2 = "1234";
    private static String surname2 = "1224";
    private static String middle2 = "middle4";
    private static String first2 = "first4";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-13");

    private static Long footballTeamStatsId;
    private static String teamName = "1234";
    private static String logoName = "1";
    private static byte[] blackBoxJpgImageByteArray = HexFormat.of()
            .parseHex("ffd8ffe000104a46494600010201004800480000ffe100ca4578696600004d4d002a000000080006011200030000000100010000011a00050000000100000056011b0005000000010000005e01280003000000010002000002130003000000010001000087690004000000010000006600000000000000480000000100000048000000010007900000070000000430323231910100070000000401020300a00000070000000430313030a00100030000000100010000a00200040000000100000094a00300040000000100000094a40600030000000100000000000000000000ffe207d84943435f50524f46494c45000101000007c86170706c022000006d6e74725247422058595a2007d900020019000b001a000b616373704150504c000000006170706c000000000000000000000000000000000000f6d6000100000000d32d6170706c00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000b64657363000001080000006f6473636d000001780000058a637072740000070400000038777470740000073c000000147258595a00000750000000146758595a00000764000000146258595a0000077800000014725452430000078c0000000e636861640000079c0000002c625452430000078c0000000e675452430000078c0000000e64657363000000000000001447656e65726963205247422050726f66696c6500000000000000000000001447656e65726963205247422050726f66696c6500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006d6c7563000000000000001f0000000c736b534b00000028000001846461444b00000024000001ac6361455300000024000001d07669564e00000024000001f4707442520000002600000218756b55410000002a0000023e6672465500000028000002686875485500000028000002907a68545700000012000002b86b6f4b5200000016000002ca6e624e4f00000026000002e06373435a00000022000003066865494c0000001e00000328726f524f0000002400000346646544450000002c0000036a6974495400000028000003967376534500000026000002e07a68434e00000012000003be6a614a500000001a000003d0656c475200000022000003ea7074504f000000260000040c6e6c4e4c000000280000043265734553000000260000040c74685448000000240000045a74725452000000220000047e6669464900000028000004a06872485200000028000004c8706c504c0000002c000004f072755255000000220000051c656e5553000000260000053e617245470000002600000564005601610065006f006200650063006e00fd0020005200470042002000700072006f00660069006c00470065006e006500720065006c0020005200470042002d00700072006f00660069006c00500065007200660069006c0020005200470042002000670065006e00e800720069006300431ea500750020006800ec006e006800200052004700420020004300680075006e006700500065007200660069006c0020005200470042002000470065006e00e9007200690063006f0417043004330430043b044c043d043804390020043f0440043e044404300439043b002000520047004200500072006f00660069006c0020006700e9006e00e900720069007100750065002000520056004200c1006c00740061006c00e1006e006f00730020005200470042002000700072006f00660069006c901a752800520047004282725f6963cf8ff0c77cbc1800200052004700420020d504b85cd30cc77c00470065006e0065007200690073006b0020005200470042002d00700072006f00660069006c004f006200650063006e00fd0020005200470042002000700072006f00660069006c05e405e805d505e405d905dc0020005200470042002005db05dc05dc05d900500072006f00660069006c0020005200470042002000670065006e00650072006900630041006c006c00670065006d00650069006e006500730020005200470042002d00500072006f00660069006c00500072006f00660069006c006f0020005200470042002000670065006e0065007200690063006f666e901a00520047004263cf8ff065874ef64e00822c0020005200470042002030d730ed30d530a130a430eb039303b503bd03b903ba03cc002003c003c103bf03c603af03bb002000520047004200500065007200660069006c0020005200470042002000670065006e00e9007200690063006f0041006c00670065006d00650065006e0020005200470042002d00700072006f006600690065006c0e420e1b0e230e440e1f0e250e4c002000520047004200200e170e310e480e270e440e1b00470065006e0065006c0020005200470042002000500072006f00660069006c00690059006c00650069006e0065006e0020005200470042002d00700072006f006600690069006c006900470065006e006500720069010d006b00690020005200470042002000700072006f00660069006c0055006e006900770065007200730061006c006e0079002000700072006f00660069006c0020005200470042041e04310449043804390020043f0440043e04440438043b044c002000520047004200470065006e00650072006900630020005200470042002000500072006f00660069006c00650645064406410020062a06390631064a0641002000520047004200200627064406390627064500007465787400000000436f707972696768742032303037204170706c6520496e632e2c20616c6c207269676874732072657365727665642e0058595a20000000000000f35200010000000116cf58595a20000000000000744d00003dee000003d058595a200000000000005a750000ac730000173458595a20000000000000281a0000159f0000b83663757276000000000000000101cd0000736633320000000000010c42000005defffff326000007920000fd91fffffba2fffffda3000003dc0000c06cffc00011080094009403012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffdb0043000101010101010201010203020202030403030303040604040404040607060606060606070707070707070708080808080809090909090b0b0b0b0b0b0b0b0b0bffdb004301020202030303050303050b0806080b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0bffdd0004000affda000c03010002110311003f00ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd0ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd1ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd2ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd3ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd4ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd5ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd6ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd7ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd0ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd9");
    private static Timestamp dateTime = Timestamp.valueOf("2023-12-12 12:12:10");
    private static String gameName = "1";
    private static TeamType teamType = new TeamType();
    private static Season firstSeason = new Season();
    private static Season lastSeason = new Season();
    private static LevelOfCompetition levelOfCompetition = new LevelOfCompetition();
    private static Team team = new Team();
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
    private static FootballTeamStats footballTeamStats = new FootballTeamStats();
    private static FootballTeamStats updatedFootballTeamStats = new FootballTeamStats();
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

        levelOfCompetition.setLevelOfCompetitionName("pro");

        firstSeason.setEndDate(Date.valueOf("1111-11-11"));
        firstSeason.setStartDate(Date.valueOf("2000-10-10"));
        firstSeason.setSeasonName("3");

        lastSeason.setEndDate(Date.valueOf("1115-11-11"));
        lastSeason.setStartDate(Date.valueOf("2001-10-10"));
        lastSeason.setSeasonName("4");

        teamType.setTeamTypeName("5");
        team.setTeamName(teamName);
        team.setLogoName(logoName);
        team.setLogoImage(blackBoxJpgImageByteArray);

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
        footballTeamStats.setTotalTackles(3l);
        footballTeamStats.setSoloTackles(4l);
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        footballTeamStats.setTotalSacks(5l);
        footballTeamStats.setTacklesForLoss(6l);
        footballTeamStats.setPassesDefended(7l);
        footballTeamStats.setQuarterbackHits(8l);
        footballTeamStats.setDefensiveTouchdowns(9l);
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        footballTeamStats.setTotalFumbles(10l);
        footballTeamStats.setFumblesLost(11l);
        footballTeamStats.setFumblesRecovered(12l);
        footballTeamStats.setInterceptions(13l);
        footballTeamStats.setInterceptionYards(14l);
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        footballTeamStats.setInterceptionTouchdowns(15l);
        footballTeamStats.setNumberOfFieldGoals(16l);
        footballTeamStats.setFieldGoalPercentage(17l);
        footballTeamStats.setLongestFieldGoal(18l);
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        footballTeamStats.setExtraPoints(19l);
        footballTeamStats.setExtraPointAttempts(20l);
        footballTeamStats.setPoints(21l);
        footballTeamStats.setNumberOfKickReturns(22l);
        footballTeamStats.setTotalKickReturnYards(23l);
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        footballTeamStats.setNumberOfYardsPerKickReturn(Double.valueOf(24));
        footballTeamStats.setLongestKickReturnYards(25l);
        footballTeamStats.setKickReturnTouchdowns(26l);
        footballTeamStats.setCompletions(27l);
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        footballTeamStats.setPassingAttempts(28l);
        footballTeamStats.setPassingYards(29l);
        footballTeamStats.setYardsPerPassAttempt(Double.valueOf(30));
        footballTeamStats.setPassingTouchdowns(31l);
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        footballTeamStats.setInterceptionsThrown(32l);
        footballTeamStats.setNumberOfTimesSacked(33l);
        footballTeamStats.setSackedYardsLost(34l);
        footballTeamStats.setPasserRating(Double.valueOf(35));
        footballTeamStats.setAdjustedQbr(Double.valueOf(36));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        footballTeamStats.setNumberOfPunts(37l);
        footballTeamStats.setTotalPuntYards(38l);
        footballTeamStats.setPuntYardsPerPunt(Double.valueOf(39));
        footballTeamStats.setTouchbacks(40l);
        footballTeamStats.setIn20(41l);
        footballTeamStats.setLongestPuntYards(42l);
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        footballTeamStats.setNumberOfPuntReturns(43l);
        footballTeamStats.setTotalPuntReturnYards(44l);
        footballTeamStats.setPuntReturnYardsPerPuntReturn(Double.valueOf(45));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        footballTeamStats.setLongestPuntReturnYards(46l);
        footballTeamStats.setPuntReturnTouchdowns(47l);
        footballTeamStats.setTotalReceptions(48l);
        footballTeamStats.setTotalReceivingYards(49l);
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        footballTeamStats.setAverageYardsPerReception(Double.valueOf(50));
        footballTeamStats.setReceivingTouchdowns(51l);
        footballTeamStats.setLongestReception(52l);
        footballTeamStats.setReceivingTargets(53l);
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        footballTeamStats.setRushingAttempts(54l);
        footballTeamStats.setAverages(Double.valueOf(55));
        footballTeamStats.setRushingTouchdowns(56l);
        footballTeamStats.setLongestRun(57l);

        updatedFootballTeamStats.setTotalTackles(311l);
        updatedFootballTeamStats.setSoloTackles(411l);
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        updatedFootballTeamStats.setTotalSacks(511l);
        updatedFootballTeamStats.setTacklesForLoss(611l);
        updatedFootballTeamStats.setPassesDefended(711l);
        updatedFootballTeamStats.setQuarterbackHits(811l);
        updatedFootballTeamStats.setDefensiveTouchdowns(911l);
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        updatedFootballTeamStats.setTotalFumbles(1011l);
        updatedFootballTeamStats.setFumblesLost(1111l);
        updatedFootballTeamStats.setFumblesRecovered(1211l);
        updatedFootballTeamStats.setInterceptions(1311l);
        updatedFootballTeamStats.setInterceptionYards(1411l);
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        updatedFootballTeamStats.setInterceptionTouchdowns(1511l);
        updatedFootballTeamStats.setNumberOfFieldGoals(1611l);
        updatedFootballTeamStats.setFieldGoalPercentage(1711l);
        updatedFootballTeamStats.setLongestFieldGoal(1811l);
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        updatedFootballTeamStats.setExtraPoints(1911l);
        updatedFootballTeamStats.setExtraPointAttempts(2011l);
        updatedFootballTeamStats.setPoints(2111l);
        updatedFootballTeamStats.setNumberOfKickReturns(2211l);
        updatedFootballTeamStats.setTotalKickReturnYards(2311l);
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        updatedFootballTeamStats.setNumberOfYardsPerKickReturn(Double.valueOf(2411));
        updatedFootballTeamStats.setLongestKickReturnYards(2511l);
        updatedFootballTeamStats.setKickReturnTouchdowns(2611l);
        updatedFootballTeamStats.setCompletions(2711l);
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        updatedFootballTeamStats.setPassingAttempts(2811l);
        updatedFootballTeamStats.setPassingYards(2911l);
        updatedFootballTeamStats.setYardsPerPassAttempt(Double.valueOf(3011));
        updatedFootballTeamStats.setPassingTouchdowns(3111l);
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        updatedFootballTeamStats.setInterceptionsThrown(3211l);
        updatedFootballTeamStats.setNumberOfTimesSacked(3311l);
        updatedFootballTeamStats.setSackedYardsLost(3411l);
        updatedFootballTeamStats.setPasserRating(Double.valueOf(3511));
        updatedFootballTeamStats.setAdjustedQbr(Double.valueOf(3611));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        updatedFootballTeamStats.setNumberOfPunts(3711l);
        updatedFootballTeamStats.setTotalPuntYards(3811l);
        updatedFootballTeamStats.setPuntYardsPerPunt(Double.valueOf(3911));
        updatedFootballTeamStats.setTouchbacks(4011l);
        updatedFootballTeamStats.setIn20(4111l);
        updatedFootballTeamStats.setLongestPuntYards(4211l);
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        updatedFootballTeamStats.setNumberOfPuntReturns(4311l);
        updatedFootballTeamStats.setTotalPuntReturnYards(4411l);
        updatedFootballTeamStats.setPuntReturnYardsPerPuntReturn(Double.valueOf(4511));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        updatedFootballTeamStats.setLongestPuntReturnYards(4611l);
        updatedFootballTeamStats.setPuntReturnTouchdowns(4711l);
        updatedFootballTeamStats.setTotalReceptions(4811l);
        updatedFootballTeamStats.setTotalReceivingYards(4911l);
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        updatedFootballTeamStats.setAverageYardsPerReception(Double.valueOf(5011));
        updatedFootballTeamStats.setReceivingTouchdowns(5111l);
        updatedFootballTeamStats.setLongestReception(5211l);
        updatedFootballTeamStats.setReceivingTargets(5311l);
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        updatedFootballTeamStats.setRushingAttempts(5411l);
        updatedFootballTeamStats.setAverages(Double.valueOf(5511));
        updatedFootballTeamStats.setRushingTouchdowns(5611l);
        updatedFootballTeamStats.setLongestRun(5711l);
    }

    private static Connection connection;
    @BeforeEach
    public void beforeEach() throws SQLException {
    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private FootballTeamStatsService footballTeamStatsService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private TeamTypeService teamTypeService;

    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private StateService stateService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private SeasonTypeService seasonTypeService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PasswordService passwordService;

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        //Database.copyOutManagerTest(connection, FootballTeamStatsTable.FOOTBALL_TEAM_STATS_TABLE_NAME);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        //footballTeamStatsService.d(connection);
        //Database.copyInManagerTest(connection, FootballTeamStatsTable.FOOTBALL_TEAM_STATS_TABLE_NAME);
        Database.closeConnection(connection);
    }

    @Test
    @Order(1)
    void createFootballTeamStats() throws Exception {
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

        teamType = teamTypeService.saveTeamType(teamType);
        team.setTeamType(teamType);

        levelOfCompetitionService.saveLevelOfCompetition(levelOfCompetition);
        team.setLevelOfCompetition(levelOfCompetition);

        gameType = gameTypeService.saveGameType(gameType);
        game.setGameType(gameType);
        team.setGameType(gameType);

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
        game.setLocation(location);
        team.setLocation(location);

        team = teamService.saveTeam(team);
        footballTeamStats.setTeam(team);
        updatedFootballTeamStats.setTeam(team);

        gameStatus = gameStatusService.saveGameStatus(gameStatus);
        game.setGameStatus(gameStatus);

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        footballTeamStats.setGame(game);
        updatedFootballTeamStats.setGame(game);

        headers.set("session", session.getSessionCode());
        HttpEntity<FootballTeamStats> request = new HttpEntity<>(footballTeamStats, headers);

        String url = hostname + port
                + FootballTeamStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<FootballTeamStats> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, FootballTeamStats.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        FootballTeamStats footballTeamStats1 = response.getBody();
        assertTrue(footballTeamStats1.getGame().getId().equals(footballTeamStats.getGame().getId()));
        assertTrue(footballTeamStats1.getTeam().getId().equals(footballTeamStats.getTeam().getId()));
        assertTrue(footballTeamStats1.getTotalTackles().equals(footballTeamStats.getTotalTackles()));
        assertTrue(footballTeamStats1.getSoloTackles().equals(footballTeamStats.getSoloTackles()));
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        assertTrue(footballTeamStats1.getTotalSacks().equals(footballTeamStats.getTotalSacks()));
        assertTrue(footballTeamStats1.getTacklesForLoss().equals(footballTeamStats.getTacklesForLoss()));
        assertTrue(footballTeamStats1.getPassesDefended().equals(footballTeamStats.getPassesDefended()));
        assertTrue(footballTeamStats1.getQuarterbackHits().equals(footballTeamStats.getQuarterbackHits()));
        assertTrue(footballTeamStats1.getDefensiveTouchdowns().equals(footballTeamStats.getDefensiveTouchdowns()));
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        assertTrue(footballTeamStats1.getTotalFumbles().equals(footballTeamStats.getTotalFumbles()));
        assertTrue(footballTeamStats1.getFumblesLost().equals(footballTeamStats.getFumblesLost()));
        assertTrue(footballTeamStats1.getFumblesRecovered().equals(footballTeamStats.getFumblesRecovered()));
        assertTrue(footballTeamStats1.getInterceptions().equals(footballTeamStats.getInterceptions()));
        assertTrue(footballTeamStats1.getInterceptionYards().equals(footballTeamStats.getInterceptionYards()));
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        assertTrue(footballTeamStats1.getInterceptionTouchdowns().equals(footballTeamStats.getInterceptionTouchdowns()));
        assertTrue(footballTeamStats1.getNumberOfFieldGoals().equals(footballTeamStats.getNumberOfFieldGoals()));
        assertTrue(footballTeamStats1.getFieldGoalPercentage().equals(footballTeamStats.getFieldGoalPercentage()));
        assertTrue(footballTeamStats1.getLongestFieldGoal().equals(footballTeamStats.getLongestFieldGoal()));
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        assertTrue(footballTeamStats1.getExtraPoints().equals(footballTeamStats.getExtraPoints()));
        assertTrue(footballTeamStats1.getExtraPointAttempts().equals(footballTeamStats.getExtraPointAttempts()));
        assertTrue(footballTeamStats1.getPoints().equals(footballTeamStats.getPoints()));
        assertTrue(footballTeamStats1.getNumberOfKickReturns().equals(footballTeamStats.getNumberOfKickReturns()));
        assertTrue(footballTeamStats1.getTotalKickReturnYards().equals(footballTeamStats.getTotalKickReturnYards()));
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        assertTrue(footballTeamStats1.getNumberOfYardsPerKickReturn().equals(footballTeamStats.getNumberOfYardsPerKickReturn()));
        assertTrue(footballTeamStats1.getLongestKickReturnYards().equals(footballTeamStats.getLongestKickReturnYards()));
        assertTrue(footballTeamStats1.getKickReturnTouchdowns().equals(footballTeamStats.getKickReturnTouchdowns()));
        assertTrue(footballTeamStats1.getCompletions().equals(footballTeamStats.getCompletions()));
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        assertTrue(footballTeamStats1.getPassingAttempts().equals(footballTeamStats.getPassingAttempts()));
        assertTrue(footballTeamStats1.getPassingYards().equals(footballTeamStats.getPassingYards()));
        assertTrue(footballTeamStats1.getYardsPerPassAttempt().equals(footballTeamStats.getYardsPerPassAttempt()));
        assertTrue(footballTeamStats1.getPassingTouchdowns().equals(footballTeamStats.getPassingTouchdowns()));
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        assertTrue(footballTeamStats1.getInterceptionsThrown().equals(footballTeamStats.getInterceptionsThrown()));
        assertTrue(footballTeamStats1.getNumberOfTimesSacked().equals(footballTeamStats.getNumberOfTimesSacked()));
        assertTrue(footballTeamStats1.getSackedYardsLost().equals(footballTeamStats.getSackedYardsLost()));
        assertTrue(footballTeamStats1.getPasserRating().equals(footballTeamStats.getPasserRating()));
        assertTrue(footballTeamStats1.getAdjustedQbr().equals(footballTeamStats.getAdjustedQbr()));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        assertTrue(footballTeamStats1.getNumberOfPunts().equals(footballTeamStats.getNumberOfPunts()));
        assertTrue(footballTeamStats1.getTotalPuntYards().equals(footballTeamStats.getTotalPuntYards()));
        assertTrue(footballTeamStats1.getPuntYardsPerPunt().equals(footballTeamStats.getPuntYardsPerPunt()));
        assertTrue(footballTeamStats1.getTouchbacks().equals(footballTeamStats.getTouchbacks()));
        assertTrue(footballTeamStats1.getIn20().equals(footballTeamStats.getIn20()));
        assertTrue(footballTeamStats1.getLongestPuntYards().equals(footballTeamStats.getLongestPuntYards()));
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        assertTrue(footballTeamStats1.getNumberOfPuntReturns().equals(footballTeamStats.getNumberOfPuntReturns()));
        assertTrue(footballTeamStats1.getTotalPuntReturnYards().equals(footballTeamStats.getTotalPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnYardsPerPuntReturn().equals(footballTeamStats.getPuntReturnYardsPerPuntReturn()));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        assertTrue(footballTeamStats1.getLongestPuntReturnYards().equals(footballTeamStats.getLongestPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnTouchdowns().equals(footballTeamStats.getPuntReturnTouchdowns()));
        assertTrue(footballTeamStats1.getTotalReceptions().equals(footballTeamStats.getTotalReceptions()));
        assertTrue(footballTeamStats1.getTotalReceivingYards().equals(footballTeamStats.getTotalReceivingYards()));
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        assertTrue(footballTeamStats1.getAverageYardsPerReception().equals(footballTeamStats.getAverageYardsPerReception()));
        assertTrue(footballTeamStats1.getReceivingTouchdowns().equals(footballTeamStats.getReceivingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestReception().equals(footballTeamStats.getLongestReception()));
        assertTrue(footballTeamStats1.getReceivingTargets().equals(footballTeamStats.getReceivingTargets()));
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        assertTrue(footballTeamStats1.getRushingAttempts().equals(footballTeamStats.getRushingAttempts()));
        assertTrue(footballTeamStats1.getAverages().equals(footballTeamStats.getAverages()));
        assertTrue(footballTeamStats1.getRushingTouchdowns().equals(footballTeamStats.getRushingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestRun().equals(footballTeamStats.getLongestRun()));
        footballTeamStatsId = footballTeamStats1.getId();
        updatedFootballTeamStats.setId(footballTeamStatsId);
    }

    @Test
    @Order(2)
    void readFootballTeamStatsByFootballTeamStatsName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + FootballTeamStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&gameId=" + game.getId()
                + "&teamId=" + team.getId();
        ResponseEntity<FootballTeamStats[]> footballTeamStatsList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, FootballTeamStats[].class);
        assertTrue(footballTeamStatsList.getStatusCode().is2xxSuccessful());
        FootballTeamStats footballTeamStats1 = footballTeamStatsList.getBody()[0];
        assertTrue(footballTeamStats1.getGame().getId().equals(footballTeamStats.getGame().getId()));
        assertTrue(footballTeamStats1.getTeam().getId().equals(footballTeamStats.getTeam().getId()));
        assertTrue(footballTeamStats1.getTotalTackles().equals(footballTeamStats.getTotalTackles()));
        assertTrue(footballTeamStats1.getSoloTackles().equals(footballTeamStats.getSoloTackles()));
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        assertTrue(footballTeamStats1.getTotalSacks().equals(footballTeamStats.getTotalSacks()));
        assertTrue(footballTeamStats1.getTacklesForLoss().equals(footballTeamStats.getTacklesForLoss()));
        assertTrue(footballTeamStats1.getPassesDefended().equals(footballTeamStats.getPassesDefended()));
        assertTrue(footballTeamStats1.getQuarterbackHits().equals(footballTeamStats.getQuarterbackHits()));
        assertTrue(footballTeamStats1.getDefensiveTouchdowns().equals(footballTeamStats.getDefensiveTouchdowns()));
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        assertTrue(footballTeamStats1.getTotalFumbles().equals(footballTeamStats.getTotalFumbles()));
        assertTrue(footballTeamStats1.getFumblesLost().equals(footballTeamStats.getFumblesLost()));
        assertTrue(footballTeamStats1.getFumblesRecovered().equals(footballTeamStats.getFumblesRecovered()));
        assertTrue(footballTeamStats1.getInterceptions().equals(footballTeamStats.getInterceptions()));
        assertTrue(footballTeamStats1.getInterceptionYards().equals(footballTeamStats.getInterceptionYards()));
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        assertTrue(footballTeamStats1.getInterceptionTouchdowns().equals(footballTeamStats.getInterceptionTouchdowns()));
        assertTrue(footballTeamStats1.getNumberOfFieldGoals().equals(footballTeamStats.getNumberOfFieldGoals()));
        assertTrue(footballTeamStats1.getFieldGoalPercentage().equals(footballTeamStats.getFieldGoalPercentage()));
        assertTrue(footballTeamStats1.getLongestFieldGoal().equals(footballTeamStats.getLongestFieldGoal()));
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        assertTrue(footballTeamStats1.getExtraPoints().equals(footballTeamStats.getExtraPoints()));
        assertTrue(footballTeamStats1.getExtraPointAttempts().equals(footballTeamStats.getExtraPointAttempts()));
        assertTrue(footballTeamStats1.getPoints().equals(footballTeamStats.getPoints()));
        assertTrue(footballTeamStats1.getNumberOfKickReturns().equals(footballTeamStats.getNumberOfKickReturns()));
        assertTrue(footballTeamStats1.getTotalKickReturnYards().equals(footballTeamStats.getTotalKickReturnYards()));
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        assertTrue(footballTeamStats1.getNumberOfYardsPerKickReturn().equals(footballTeamStats.getNumberOfYardsPerKickReturn()));
        assertTrue(footballTeamStats1.getLongestKickReturnYards().equals(footballTeamStats.getLongestKickReturnYards()));
        assertTrue(footballTeamStats1.getKickReturnTouchdowns().equals(footballTeamStats.getKickReturnTouchdowns()));
        assertTrue(footballTeamStats1.getCompletions().equals(footballTeamStats.getCompletions()));
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        assertTrue(footballTeamStats1.getPassingAttempts().equals(footballTeamStats.getPassingAttempts()));
        assertTrue(footballTeamStats1.getPassingYards().equals(footballTeamStats.getPassingYards()));
        assertTrue(footballTeamStats1.getYardsPerPassAttempt().equals(footballTeamStats.getYardsPerPassAttempt()));
        assertTrue(footballTeamStats1.getPassingTouchdowns().equals(footballTeamStats.getPassingTouchdowns()));
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        assertTrue(footballTeamStats1.getInterceptionsThrown().equals(footballTeamStats.getInterceptionsThrown()));
        assertTrue(footballTeamStats1.getNumberOfTimesSacked().equals(footballTeamStats.getNumberOfTimesSacked()));
        assertTrue(footballTeamStats1.getSackedYardsLost().equals(footballTeamStats.getSackedYardsLost()));
        assertTrue(footballTeamStats1.getPasserRating().equals(footballTeamStats.getPasserRating()));
        assertTrue(footballTeamStats1.getAdjustedQbr().equals(footballTeamStats.getAdjustedQbr()));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        assertTrue(footballTeamStats1.getNumberOfPunts().equals(footballTeamStats.getNumberOfPunts()));
        assertTrue(footballTeamStats1.getTotalPuntYards().equals(footballTeamStats.getTotalPuntYards()));
        assertTrue(footballTeamStats1.getPuntYardsPerPunt().equals(footballTeamStats.getPuntYardsPerPunt()));
        assertTrue(footballTeamStats1.getTouchbacks().equals(footballTeamStats.getTouchbacks()));
        assertTrue(footballTeamStats1.getIn20().equals(footballTeamStats.getIn20()));
        assertTrue(footballTeamStats1.getLongestPuntYards().equals(footballTeamStats.getLongestPuntYards()));
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        assertTrue(footballTeamStats1.getNumberOfPuntReturns().equals(footballTeamStats.getNumberOfPuntReturns()));
        assertTrue(footballTeamStats1.getTotalPuntReturnYards().equals(footballTeamStats.getTotalPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnYardsPerPuntReturn().equals(footballTeamStats.getPuntReturnYardsPerPuntReturn()));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        assertTrue(footballTeamStats1.getLongestPuntReturnYards().equals(footballTeamStats.getLongestPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnTouchdowns().equals(footballTeamStats.getPuntReturnTouchdowns()));
        assertTrue(footballTeamStats1.getTotalReceptions().equals(footballTeamStats.getTotalReceptions()));
        assertTrue(footballTeamStats1.getTotalReceivingYards().equals(footballTeamStats.getTotalReceivingYards()));
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        assertTrue(footballTeamStats1.getAverageYardsPerReception().equals(footballTeamStats.getAverageYardsPerReception()));
        assertTrue(footballTeamStats1.getReceivingTouchdowns().equals(footballTeamStats.getReceivingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestReception().equals(footballTeamStats.getLongestReception()));
        assertTrue(footballTeamStats1.getReceivingTargets().equals(footballTeamStats.getReceivingTargets()));
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        assertTrue(footballTeamStats1.getRushingAttempts().equals(footballTeamStats.getRushingAttempts()));
        assertTrue(footballTeamStats1.getAverages().equals(footballTeamStats.getAverages()));
        assertTrue(footballTeamStats1.getRushingTouchdowns().equals(footballTeamStats.getRushingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestRun().equals(footballTeamStats.getLongestRun()));
    }

    @Test
    @Order(3)
    void readFootballTeamStatsByFootballTeamStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<FootballTeamStats[]> footballTeamStatsList = this.restTemplate.exchange(
                hostname + port + FootballTeamStatsPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&footballTeamStatsId=" + footballTeamStatsId, HttpMethod.GET, request,
                FootballTeamStats[].class);

        FootballTeamStats footballTeamStats1 = footballTeamStatsList.getBody()[0];
        assertTrue(footballTeamStats1.getGame().getId().equals(footballTeamStats.getGame().getId()));
        assertTrue(footballTeamStats1.getTeam().getId().equals(footballTeamStats.getTeam().getId()));
        assertTrue(footballTeamStats1.getTotalTackles().equals(footballTeamStats.getTotalTackles()));
        assertTrue(footballTeamStats1.getSoloTackles().equals(footballTeamStats.getSoloTackles()));
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        assertTrue(footballTeamStats1.getTotalSacks().equals(footballTeamStats.getTotalSacks()));
        assertTrue(footballTeamStats1.getTacklesForLoss().equals(footballTeamStats.getTacklesForLoss()));
        assertTrue(footballTeamStats1.getPassesDefended().equals(footballTeamStats.getPassesDefended()));
        assertTrue(footballTeamStats1.getQuarterbackHits().equals(footballTeamStats.getQuarterbackHits()));
        assertTrue(footballTeamStats1.getDefensiveTouchdowns().equals(footballTeamStats.getDefensiveTouchdowns()));
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        assertTrue(footballTeamStats1.getTotalFumbles().equals(footballTeamStats.getTotalFumbles()));
        assertTrue(footballTeamStats1.getFumblesLost().equals(footballTeamStats.getFumblesLost()));
        assertTrue(footballTeamStats1.getFumblesRecovered().equals(footballTeamStats.getFumblesRecovered()));
        assertTrue(footballTeamStats1.getInterceptions().equals(footballTeamStats.getInterceptions()));
        assertTrue(footballTeamStats1.getInterceptionYards().equals(footballTeamStats.getInterceptionYards()));
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        assertTrue(footballTeamStats1.getInterceptionTouchdowns().equals(footballTeamStats.getInterceptionTouchdowns()));
        assertTrue(footballTeamStats1.getNumberOfFieldGoals().equals(footballTeamStats.getNumberOfFieldGoals()));
        assertTrue(footballTeamStats1.getFieldGoalPercentage().equals(footballTeamStats.getFieldGoalPercentage()));
        assertTrue(footballTeamStats1.getLongestFieldGoal().equals(footballTeamStats.getLongestFieldGoal()));
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        assertTrue(footballTeamStats1.getExtraPoints().equals(footballTeamStats.getExtraPoints()));
        assertTrue(footballTeamStats1.getExtraPointAttempts().equals(footballTeamStats.getExtraPointAttempts()));
        assertTrue(footballTeamStats1.getPoints().equals(footballTeamStats.getPoints()));
        assertTrue(footballTeamStats1.getNumberOfKickReturns().equals(footballTeamStats.getNumberOfKickReturns()));
        assertTrue(footballTeamStats1.getTotalKickReturnYards().equals(footballTeamStats.getTotalKickReturnYards()));
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        assertTrue(footballTeamStats1.getNumberOfYardsPerKickReturn().equals(footballTeamStats.getNumberOfYardsPerKickReturn()));
        assertTrue(footballTeamStats1.getLongestKickReturnYards().equals(footballTeamStats.getLongestKickReturnYards()));
        assertTrue(footballTeamStats1.getKickReturnTouchdowns().equals(footballTeamStats.getKickReturnTouchdowns()));
        assertTrue(footballTeamStats1.getCompletions().equals(footballTeamStats.getCompletions()));
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        assertTrue(footballTeamStats1.getPassingAttempts().equals(footballTeamStats.getPassingAttempts()));
        assertTrue(footballTeamStats1.getPassingYards().equals(footballTeamStats.getPassingYards()));
        assertTrue(footballTeamStats1.getYardsPerPassAttempt().equals(footballTeamStats.getYardsPerPassAttempt()));
        assertTrue(footballTeamStats1.getPassingTouchdowns().equals(footballTeamStats.getPassingTouchdowns()));
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        assertTrue(footballTeamStats1.getInterceptionsThrown().equals(footballTeamStats.getInterceptionsThrown()));
        assertTrue(footballTeamStats1.getNumberOfTimesSacked().equals(footballTeamStats.getNumberOfTimesSacked()));
        assertTrue(footballTeamStats1.getSackedYardsLost().equals(footballTeamStats.getSackedYardsLost()));
        assertTrue(footballTeamStats1.getPasserRating().equals(footballTeamStats.getPasserRating()));
        assertTrue(footballTeamStats1.getAdjustedQbr().equals(footballTeamStats.getAdjustedQbr()));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        assertTrue(footballTeamStats1.getNumberOfPunts().equals(footballTeamStats.getNumberOfPunts()));
        assertTrue(footballTeamStats1.getTotalPuntYards().equals(footballTeamStats.getTotalPuntYards()));
        assertTrue(footballTeamStats1.getPuntYardsPerPunt().equals(footballTeamStats.getPuntYardsPerPunt()));
        assertTrue(footballTeamStats1.getTouchbacks().equals(footballTeamStats.getTouchbacks()));
        assertTrue(footballTeamStats1.getIn20().equals(footballTeamStats.getIn20()));
        assertTrue(footballTeamStats1.getLongestPuntYards().equals(footballTeamStats.getLongestPuntYards()));
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        assertTrue(footballTeamStats1.getNumberOfPuntReturns().equals(footballTeamStats.getNumberOfPuntReturns()));
        assertTrue(footballTeamStats1.getTotalPuntReturnYards().equals(footballTeamStats.getTotalPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnYardsPerPuntReturn().equals(footballTeamStats.getPuntReturnYardsPerPuntReturn()));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        assertTrue(footballTeamStats1.getLongestPuntReturnYards().equals(footballTeamStats.getLongestPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnTouchdowns().equals(footballTeamStats.getPuntReturnTouchdowns()));
        assertTrue(footballTeamStats1.getTotalReceptions().equals(footballTeamStats.getTotalReceptions()));
        assertTrue(footballTeamStats1.getTotalReceivingYards().equals(footballTeamStats.getTotalReceivingYards()));
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        assertTrue(footballTeamStats1.getAverageYardsPerReception().equals(footballTeamStats.getAverageYardsPerReception()));
        assertTrue(footballTeamStats1.getReceivingTouchdowns().equals(footballTeamStats.getReceivingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestReception().equals(footballTeamStats.getLongestReception()));
        assertTrue(footballTeamStats1.getReceivingTargets().equals(footballTeamStats.getReceivingTargets()));
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        assertTrue(footballTeamStats1.getRushingAttempts().equals(footballTeamStats.getRushingAttempts()));
        assertTrue(footballTeamStats1.getAverages().equals(footballTeamStats.getAverages()));
        assertTrue(footballTeamStats1.getRushingTouchdowns().equals(footballTeamStats.getRushingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestRun().equals(footballTeamStats.getLongestRun()));
    }

    @Test
    @Order(4)
    void updateFootballTeamStatsByFootballTeamStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<FootballTeamStats> request = new HttpEntity<>(updatedFootballTeamStats, headers);

        String url = hostname + port
                + FootballTeamStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&footballTeamStatsId=" + footballTeamStatsId;
        URI uri = new URI(url);
        ResponseEntity<FootballTeamStats[]> footballTeamStatsList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, FootballTeamStats[].class);
    }

    @Test
    @Order(5)
    void readUpdatedFootballTeamStatsByFootballTeamStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<FootballTeamStats[]> footballTeamStatsList = this.restTemplate.exchange(
                hostname + port
                        + FootballTeamStatsPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&footballTeamStatsId=" + footballTeamStatsId, HttpMethod.GET,
                request, FootballTeamStats[].class);
        assertTrue(footballTeamStatsList.getStatusCode().is2xxSuccessful());
        FootballTeamStats footballTeamStats1 = footballTeamStatsList.getBody()[0];
        assertTrue(footballTeamStats1.getGame().getId().equals(updatedFootballTeamStats.getGame().getId()));
        assertTrue(footballTeamStats1.getTeam().getId().equals(updatedFootballTeamStats.getTeam().getId()));
        assertTrue(footballTeamStats1.getTotalTackles().equals(updatedFootballTeamStats.getTotalTackles()));
        assertTrue(footballTeamStats1.getSoloTackles().equals(updatedFootballTeamStats.getSoloTackles()));
        // total_sacks, tackles_for_loss, passes_defended, quarterback_hits, defensive_touchdowns,
        assertTrue(footballTeamStats1.getTotalSacks().equals(updatedFootballTeamStats.getTotalSacks()));
        assertTrue(footballTeamStats1.getTacklesForLoss().equals(updatedFootballTeamStats.getTacklesForLoss()));
        assertTrue(footballTeamStats1.getPassesDefended().equals(updatedFootballTeamStats.getPassesDefended()));
        assertTrue(footballTeamStats1.getQuarterbackHits().equals(updatedFootballTeamStats.getQuarterbackHits()));
        assertTrue(footballTeamStats1.getDefensiveTouchdowns().equals(updatedFootballTeamStats.getDefensiveTouchdowns()));
        // total_fumbles, fumbles_lost, fumbles_recovered, interceptions, interception_yards,
        assertTrue(footballTeamStats1.getTotalFumbles().equals(updatedFootballTeamStats.getTotalFumbles()));
        assertTrue(footballTeamStats1.getFumblesLost().equals(updatedFootballTeamStats.getFumblesLost()));
        assertTrue(footballTeamStats1.getFumblesRecovered().equals(updatedFootballTeamStats.getFumblesRecovered()));
        assertTrue(footballTeamStats1.getInterceptions().equals(updatedFootballTeamStats.getInterceptions()));
        assertTrue(footballTeamStats1.getInterceptionYards().equals(updatedFootballTeamStats.getInterceptionYards()));
        // interception_touchdowns, number_of_field_goals, field_goal_percentage, longest_field_goal,
        assertTrue(footballTeamStats1.getInterceptionTouchdowns().equals(updatedFootballTeamStats.getInterceptionTouchdowns()));
        assertTrue(footballTeamStats1.getNumberOfFieldGoals().equals(updatedFootballTeamStats.getNumberOfFieldGoals()));
        assertTrue(footballTeamStats1.getFieldGoalPercentage().equals(updatedFootballTeamStats.getFieldGoalPercentage()));
        assertTrue(footballTeamStats1.getLongestFieldGoal().equals(updatedFootballTeamStats.getLongestFieldGoal()));
        // extra_points, extra_point_attempts, points, number_of_kick_returns, total_kick_return_yards,
        assertTrue(footballTeamStats1.getExtraPoints().equals(updatedFootballTeamStats.getExtraPoints()));
        assertTrue(footballTeamStats1.getExtraPointAttempts().equals(updatedFootballTeamStats.getExtraPointAttempts()));
        assertTrue(footballTeamStats1.getPoints().equals(updatedFootballTeamStats.getPoints()));
        assertTrue(footballTeamStats1.getNumberOfKickReturns().equals(updatedFootballTeamStats.getNumberOfKickReturns()));
        assertTrue(footballTeamStats1.getTotalKickReturnYards().equals(updatedFootballTeamStats.getTotalKickReturnYards()));
        // number_of_yards_per_kick_return, longest_kick_return_yards, kick_return_touchdowns, completions,
        assertTrue(footballTeamStats1.getNumberOfYardsPerKickReturn().equals(updatedFootballTeamStats.getNumberOfYardsPerKickReturn()));
        assertTrue(footballTeamStats1.getLongestKickReturnYards().equals(updatedFootballTeamStats.getLongestKickReturnYards()));
        assertTrue(footballTeamStats1.getKickReturnTouchdowns().equals(updatedFootballTeamStats.getKickReturnTouchdowns()));
        assertTrue(footballTeamStats1.getCompletions().equals(updatedFootballTeamStats.getCompletions()));
        // passing_attempts, passing_yards, yards_per_pass_attempt, passing_touchdowns,
        assertTrue(footballTeamStats1.getPassingAttempts().equals(updatedFootballTeamStats.getPassingAttempts()));
        assertTrue(footballTeamStats1.getPassingYards().equals(updatedFootballTeamStats.getPassingYards()));
        assertTrue(footballTeamStats1.getYardsPerPassAttempt().equals(updatedFootballTeamStats.getYardsPerPassAttempt()));
        assertTrue(footballTeamStats1.getPassingTouchdowns().equals(updatedFootballTeamStats.getPassingTouchdowns()));
        // interceptions_thrown, number_of_times_sacked, sacked_yards_lost, passer_rating, adjusted_qbr,
        assertTrue(footballTeamStats1.getInterceptionsThrown().equals(updatedFootballTeamStats.getInterceptionsThrown()));
        assertTrue(footballTeamStats1.getNumberOfTimesSacked().equals(updatedFootballTeamStats.getNumberOfTimesSacked()));
        assertTrue(footballTeamStats1.getSackedYardsLost().equals(updatedFootballTeamStats.getSackedYardsLost()));
        assertTrue(footballTeamStats1.getPasserRating().equals(updatedFootballTeamStats.getPasserRating()));
        assertTrue(footballTeamStats1.getAdjustedQbr().equals(updatedFootballTeamStats.getAdjustedQbr()));
        // number_of_punts, total_punt_yards, punt_yards_per_punt, touchbacks, in20, longest_punt_yards,
        assertTrue(footballTeamStats1.getNumberOfPunts().equals(updatedFootballTeamStats.getNumberOfPunts()));
        assertTrue(footballTeamStats1.getTotalPuntYards().equals(updatedFootballTeamStats.getTotalPuntYards()));
        assertTrue(footballTeamStats1.getPuntYardsPerPunt().equals(updatedFootballTeamStats.getPuntYardsPerPunt()));
        assertTrue(footballTeamStats1.getTouchbacks().equals(updatedFootballTeamStats.getTouchbacks()));
        assertTrue(footballTeamStats1.getIn20().equals(updatedFootballTeamStats.getIn20()));
        assertTrue(footballTeamStats1.getLongestPuntYards().equals(updatedFootballTeamStats.getLongestPuntYards()));
        // number_of_punt_returns, total_punt_return_yards, punt_return_yards_per_punt_return,
        assertTrue(footballTeamStats1.getNumberOfPuntReturns().equals(updatedFootballTeamStats.getNumberOfPuntReturns()));
        assertTrue(footballTeamStats1.getTotalPuntReturnYards().equals(updatedFootballTeamStats.getTotalPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnYardsPerPuntReturn().equals(updatedFootballTeamStats.getPuntReturnYardsPerPuntReturn()));
        // longest_punt_return_yards, punt_return_touchdowns, total_receptions, total_receiving_yards,
        assertTrue(footballTeamStats1.getLongestPuntReturnYards().equals(updatedFootballTeamStats.getLongestPuntReturnYards()));
        assertTrue(footballTeamStats1.getPuntReturnTouchdowns().equals(updatedFootballTeamStats.getPuntReturnTouchdowns()));
        assertTrue(footballTeamStats1.getTotalReceptions().equals(updatedFootballTeamStats.getTotalReceptions()));
        assertTrue(footballTeamStats1.getTotalReceivingYards().equals(updatedFootballTeamStats.getTotalReceivingYards()));
        // average_yards_per_reception, receiving_touchdowns, longest_reception, receiving_targets,
        assertTrue(footballTeamStats1.getAverageYardsPerReception().equals(updatedFootballTeamStats.getAverageYardsPerReception()));
        assertTrue(footballTeamStats1.getReceivingTouchdowns().equals(updatedFootballTeamStats.getReceivingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestReception().equals(updatedFootballTeamStats.getLongestReception()));
        assertTrue(footballTeamStats1.getReceivingTargets().equals(updatedFootballTeamStats.getReceivingTargets()));
        // rushing_attempts, averages, rushing_touchdowns, longest_run
        assertTrue(footballTeamStats1.getRushingAttempts().equals(updatedFootballTeamStats.getRushingAttempts()));
        assertTrue(footballTeamStats1.getAverages().equals(updatedFootballTeamStats.getAverages()));
        assertTrue(footballTeamStats1.getRushingTouchdowns().equals(updatedFootballTeamStats.getRushingTouchdowns()));
        assertTrue(footballTeamStats1.getLongestRun().equals(updatedFootballTeamStats.getLongestRun()));
        footballTeamStatsId = footballTeamStats1.getId();
    }

    @Test
    @Order(6)
    void deleteFootballTeamStatsByFootballTeamStatsId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + FootballTeamStatsPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&footballTeamStatsId=" + footballTeamStatsId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        teamService.deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                team.getTeamName(),
                levelOfCompetition.getId(),
                gameType.getId());

        gameService.deleteGameByGameName(
                game.getGameName());

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

        levelOfCompetitionService.deleteLevelOfCompetitionByLevelOfCompetitionName(
                levelOfCompetition.getLevelOfCompetitionName());

        seasonService.deleteSeasonBySeasonName(
                firstSeason.getSeasonName());

        seasonService.deleteSeasonBySeasonName(
                lastSeason.getSeasonName());

        teamTypeService.deleteTeamTypeByTeamTypeName(
                teamType.getTeamTypeName());

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
