package com.umbrella.insurance.core.models.teamTransactions.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa.LevelOfCompetitionService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa.TeamTransactionTypeService;
import com.umbrella.insurance.core.models.teamTransactions.v1.db.jpa.TeamTransactionService;
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
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamTransactionsTableTests {
    private static Date dateOfTeamTransaction = Date.valueOf("2002-12-11");
    private static String description = "d";
    private static Date updatedDateOfTeamTransaction = Date.valueOf("2023-01-13");
    private static String updatedDescription = "123";
    private static String teamName = "1234";
    private static String logoName = "1";
    private static byte[] blackBoxJpgImageByteArray = HexFormat.of()
            .parseHex("ffd8ffe000104a46494600010201004800480000ffe100ca4578696600004d4d002a000000080006011200030000000100010000011a00050000000100000056011b0005000000010000005e01280003000000010002000002130003000000010001000087690004000000010000006600000000000000480000000100000048000000010007900000070000000430323231910100070000000401020300a00000070000000430313030a00100030000000100010000a00200040000000100000094a00300040000000100000094a40600030000000100000000000000000000ffe207d84943435f50524f46494c45000101000007c86170706c022000006d6e74725247422058595a2007d900020019000b001a000b616373704150504c000000006170706c000000000000000000000000000000000000f6d6000100000000d32d6170706c00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000b64657363000001080000006f6473636d000001780000058a637072740000070400000038777470740000073c000000147258595a00000750000000146758595a00000764000000146258595a0000077800000014725452430000078c0000000e636861640000079c0000002c625452430000078c0000000e675452430000078c0000000e64657363000000000000001447656e65726963205247422050726f66696c6500000000000000000000001447656e65726963205247422050726f66696c6500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006d6c7563000000000000001f0000000c736b534b00000028000001846461444b00000024000001ac6361455300000024000001d07669564e00000024000001f4707442520000002600000218756b55410000002a0000023e6672465500000028000002686875485500000028000002907a68545700000012000002b86b6f4b5200000016000002ca6e624e4f00000026000002e06373435a00000022000003066865494c0000001e00000328726f524f0000002400000346646544450000002c0000036a6974495400000028000003967376534500000026000002e07a68434e00000012000003be6a614a500000001a000003d0656c475200000022000003ea7074504f000000260000040c6e6c4e4c000000280000043265734553000000260000040c74685448000000240000045a74725452000000220000047e6669464900000028000004a06872485200000028000004c8706c504c0000002c000004f072755255000000220000051c656e5553000000260000053e617245470000002600000564005601610065006f006200650063006e00fd0020005200470042002000700072006f00660069006c00470065006e006500720065006c0020005200470042002d00700072006f00660069006c00500065007200660069006c0020005200470042002000670065006e00e800720069006300431ea500750020006800ec006e006800200052004700420020004300680075006e006700500065007200660069006c0020005200470042002000470065006e00e9007200690063006f0417043004330430043b044c043d043804390020043f0440043e044404300439043b002000520047004200500072006f00660069006c0020006700e9006e00e900720069007100750065002000520056004200c1006c00740061006c00e1006e006f00730020005200470042002000700072006f00660069006c901a752800520047004282725f6963cf8ff0c77cbc1800200052004700420020d504b85cd30cc77c00470065006e0065007200690073006b0020005200470042002d00700072006f00660069006c004f006200650063006e00fd0020005200470042002000700072006f00660069006c05e405e805d505e405d905dc0020005200470042002005db05dc05dc05d900500072006f00660069006c0020005200470042002000670065006e00650072006900630041006c006c00670065006d00650069006e006500730020005200470042002d00500072006f00660069006c00500072006f00660069006c006f0020005200470042002000670065006e0065007200690063006f666e901a00520047004263cf8ff065874ef64e00822c0020005200470042002030d730ed30d530a130a430eb039303b503bd03b903ba03cc002003c003c103bf03c603af03bb002000520047004200500065007200660069006c0020005200470042002000670065006e00e9007200690063006f0041006c00670065006d00650065006e0020005200470042002d00700072006f006600690065006c0e420e1b0e230e440e1f0e250e4c002000520047004200200e170e310e480e270e440e1b00470065006e0065006c0020005200470042002000500072006f00660069006c00690059006c00650069006e0065006e0020005200470042002d00700072006f006600690069006c006900470065006e006500720069010d006b00690020005200470042002000700072006f00660069006c0055006e006900770065007200730061006c006e0079002000700072006f00660069006c0020005200470042041e04310449043804390020043f0440043e04440438043b044c002000520047004200470065006e00650072006900630020005200470042002000500072006f00660069006c00650645064406410020062a06390631064a0641002000520047004200200627064406390627064500007465787400000000436f707972696768742032303037204170706c6520496e632e2c20616c6c207269676874732072657365727665642e0058595a20000000000000f35200010000000116cf58595a20000000000000744d00003dee000003d058595a200000000000005a750000ac730000173458595a20000000000000281a0000159f0000b83663757276000000000000000101cd0000736633320000000000010c42000005defffff326000007920000fd91fffffba2fffffda3000003dc0000c06cffc00011080094009403012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffdb0043000101010101010201010203020202030403030303040604040404040607060606060606070707070707070708080808080809090909090b0b0b0b0b0b0b0b0b0bffdb004301020202030303050303050b0806080b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0bffdd0004000affda000c03010002110311003f00ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd0ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd1ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd2ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd3ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd4ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd5ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd6ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd7ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd0ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd9");
    private static String teamTransactionTypeName = "1234e";
    private static TeamTransactionType teamTransactionType = new TeamTransactionType();
    private static TeamType teamType = new TeamType();
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
    private static Team team = new Team();
    private static TeamTransaction teamTransaction = new TeamTransaction();
    private static TeamTransaction updatedTeamTransaction = new TeamTransaction();
    static {
        teamTransactionType.setTeamTransactionTypeName(teamTransactionTypeName);
        gameType.setGameTypeName("2");

        levelOfCompetition.setLevelOfCompetitionName("pro");

        firstSeason.setEndDate(Date.valueOf("1111-11-11"));
        firstSeason.setStartDate(Date.valueOf("2000-10-10"));
        firstSeason.setSeasonName("3");

        lastSeason.setEndDate(Date.valueOf("1115-11-11"));
        lastSeason.setStartDate(Date.valueOf("2001-10-10"));
        lastSeason.setSeasonName("4");

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
        team.setTeamName(teamName);
        team.setLogoName(logoName);
        team.setLogoImage(blackBoxJpgImageByteArray);
        teamTransaction.setDateOfTeamTransaction(dateOfTeamTransaction);
        teamTransaction.setDescription(description);

        updatedTeamTransaction.setDateOfTeamTransaction(updatedDateOfTeamTransaction);
        updatedTeamTransaction.setDescription(updatedDescription);
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
    private TeamTransactionService teamTransactionService;

    @Autowired
    private TeamTransactionTypeService teamTransactionTypeService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private TeamTypeService teamTypeService;

    @Test
    @Order(2)
    void insertTeamTransactionTest() throws SQLException {
        teamTransactionType = teamTransactionTypeService.saveTeamTransactionType(teamTransactionType);
        teamTransaction.setTeamTransactionType(teamTransactionType);
        updatedTeamTransaction.setTeamTransactionType(teamTransactionType);

        teamType = teamTypeService.saveTeamType(teamType);
        team.setTeamType(teamType);

        gameType = gameTypeService.saveGameType(gameType);
        team.setGameType(gameType);

        levelOfCompetition = levelOfCompetitionService.saveLevelOfCompetition(levelOfCompetition);
        team.setLevelOfCompetition(levelOfCompetition);

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

        team = teamService.saveTeam(team);
        teamTransaction.setTeam(team);
        updatedTeamTransaction.setTeam(team);

        teamTransaction = teamTransactionService.saveTeamTransaction(teamTransaction);
        updatedTeamTransaction.setId(teamTransaction.getId());
    }
    @Test
    @Order(3)
    void selectTeamTransactionByDescriptionAndTeamIdTest() throws SQLException {
        TeamTransaction teamTransaction1 = teamTransactionService
                .getTeamTransactionByTeamTransactionByDescriptionAndTeamId(
                description, team.getId()).get();
        assertTrue(teamTransaction1.getDateOfTeamTransaction().equals(dateOfTeamTransaction));
        assertTrue(teamTransaction1.getTeamTransactionType().getId().equals(teamTransactionType.getId()));
        assertTrue(teamTransaction1.getDescription().equals(description));
        assertTrue(teamTransaction1.getTeam().getId().equals(team.getId()));
    }
    @Test
    @Order(4)
    void updateTeamTransactionByDescriptionAndTeamIdTest() throws SQLException {
        teamTransactionService.updateTeamTransaction(
                updatedTeamTransaction);
    }
    @Test
    @Order(5)
    void selectUpdatedTeamTransactionByDescriptionAndTeamIdTest() throws SQLException {
        TeamTransaction teamTransaction1 = teamTransactionService
                .getTeamTransactionByTeamTransactionByDescriptionAndTeamId(
                updatedDescription, team.getId()).get();
        assertTrue(teamTransaction1.getDateOfTeamTransaction().equals(updatedDateOfTeamTransaction));
        assertTrue(teamTransaction1.getTeamTransactionType().getId().equals(teamTransactionType.getId()));
        assertTrue(teamTransaction1.getDescription().equals(updatedDescription));
        assertTrue(teamTransaction1.getTeam().getId().equals(team.getId()));
    }
    @Test
    @Order(6)
    void deleteTeamTransactionByDescriptionAndTeamIdTest() throws SQLException {
        teamTransactionService.deleteTeamTransaction(updatedTeamTransaction.getId());

        teamService.deleteTeam(team.getId());

        gameTypeService.deleteGameType(gameType.getId());

        levelOfCompetitionService.deleteLevelOfCompetition(levelOfCompetition.getId());

        streetAddressService.deleteStreetAddress(streetAddress.getId());

        cityService.deleteCity(city.getId());

        stateService.deleteState(state.getId());

        zipCodeService.deleteZipCode(zipCode.getId());

        countryService.deleteCountry(country.getId());

        locationService.deleteLocation(location.getId());

        seasonService.deleteSeason(
                firstSeason.getId());

        seasonService.deleteSeason(lastSeason.getId());

        teamTypeService.deleteTeamType(teamType.getId());

        teamTransactionTypeService.deleteTeamTransactionType(teamTransactionType.getId());
    }

}
