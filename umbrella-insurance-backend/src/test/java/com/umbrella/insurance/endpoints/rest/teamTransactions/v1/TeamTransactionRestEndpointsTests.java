package com.umbrella.insurance.endpoints.rest.teamTransactions.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
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
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa.TeamTransactionTypeService;
import com.umbrella.insurance.core.models.teamTransactions.v1.db.TeamTransactionPrivilege;
import com.umbrella.insurance.core.models.teamTransactions.v1.db.jpa.TeamTransactionService;
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
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamTransactionRestEndpointsTests {
    @Autowired
    private TeamTransactionRestEndpoints teamTransactionRestEndpoints;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(teamTransactionRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress2 = "133";
    private static String phoneNumber2 = "233";
    private static String username2 = "333";
    private static String password2 = "533";
    private static String ssn2 = "12333";
    private static String surname2 = "12233";
    private static String middle2 = "middle33";
    private static String first2 = "first33";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-16");

    private static Long teamTransactionId;
    private static Date dateOfTeamTransaction = Date.valueOf("2002-12-11");
    private static String description = "d";
    private static Date updatedDateOfTeamTransaction = Date.valueOf("2023-01-13");
    private static String updatedDescription = "123";
    private static String teamName = "1234";
    private static String logoName = "1";
    private static byte[] blackBoxJpgImageByteArray = HexFormat.of()
            .parseHex("ffd8ffe000104a46494600010201004800480000ffe100ca4578696600004d4d002a000000080006011200030000000100010000011a00050000000100000056011b0005000000010000005e01280003000000010002000002130003000000010001000087690004000000010000006600000000000000480000000100000048000000010007900000070000000430323231910100070000000401020300a00000070000000430313030a00100030000000100010000a00200040000000100000094a00300040000000100000094a40600030000000100000000000000000000ffe207d84943435f50524f46494c45000101000007c86170706c022000006d6e74725247422058595a2007d900020019000b001a000b616373704150504c000000006170706c000000000000000000000000000000000000f6d6000100000000d32d6170706c00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000b64657363000001080000006f6473636d000001780000058a637072740000070400000038777470740000073c000000147258595a00000750000000146758595a00000764000000146258595a0000077800000014725452430000078c0000000e636861640000079c0000002c625452430000078c0000000e675452430000078c0000000e64657363000000000000001447656e65726963205247422050726f66696c6500000000000000000000001447656e65726963205247422050726f66696c6500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006d6c7563000000000000001f0000000c736b534b00000028000001846461444b00000024000001ac6361455300000024000001d07669564e00000024000001f4707442520000002600000218756b55410000002a0000023e6672465500000028000002686875485500000028000002907a68545700000012000002b86b6f4b5200000016000002ca6e624e4f00000026000002e06373435a00000022000003066865494c0000001e00000328726f524f0000002400000346646544450000002c0000036a6974495400000028000003967376534500000026000002e07a68434e00000012000003be6a614a500000001a000003d0656c475200000022000003ea7074504f000000260000040c6e6c4e4c000000280000043265734553000000260000040c74685448000000240000045a74725452000000220000047e6669464900000028000004a06872485200000028000004c8706c504c0000002c000004f072755255000000220000051c656e5553000000260000053e617245470000002600000564005601610065006f006200650063006e00fd0020005200470042002000700072006f00660069006c00470065006e006500720065006c0020005200470042002d00700072006f00660069006c00500065007200660069006c0020005200470042002000670065006e00e800720069006300431ea500750020006800ec006e006800200052004700420020004300680075006e006700500065007200660069006c0020005200470042002000470065006e00e9007200690063006f0417043004330430043b044c043d043804390020043f0440043e044404300439043b002000520047004200500072006f00660069006c0020006700e9006e00e900720069007100750065002000520056004200c1006c00740061006c00e1006e006f00730020005200470042002000700072006f00660069006c901a752800520047004282725f6963cf8ff0c77cbc1800200052004700420020d504b85cd30cc77c00470065006e0065007200690073006b0020005200470042002d00700072006f00660069006c004f006200650063006e00fd0020005200470042002000700072006f00660069006c05e405e805d505e405d905dc0020005200470042002005db05dc05dc05d900500072006f00660069006c0020005200470042002000670065006e00650072006900630041006c006c00670065006d00650069006e006500730020005200470042002d00500072006f00660069006c00500072006f00660069006c006f0020005200470042002000670065006e0065007200690063006f666e901a00520047004263cf8ff065874ef64e00822c0020005200470042002030d730ed30d530a130a430eb039303b503bd03b903ba03cc002003c003c103bf03c603af03bb002000520047004200500065007200660069006c0020005200470042002000670065006e00e9007200690063006f0041006c00670065006d00650065006e0020005200470042002d00700072006f006600690065006c0e420e1b0e230e440e1f0e250e4c002000520047004200200e170e310e480e270e440e1b00470065006e0065006c0020005200470042002000500072006f00660069006c00690059006c00650069006e0065006e0020005200470042002d00700072006f006600690069006c006900470065006e006500720069010d006b00690020005200470042002000700072006f00660069006c0055006e006900770065007200730061006c006e0079002000700072006f00660069006c0020005200470042041e04310449043804390020043f0440043e04440438043b044c002000520047004200470065006e00650072006900630020005200470042002000500072006f00660069006c00650645064406410020062a06390631064a0641002000520047004200200627064406390627064500007465787400000000436f707972696768742032303037204170706c6520496e632e2c20616c6c207269676874732072657365727665642e0058595a20000000000000f35200010000000116cf58595a20000000000000744d00003dee000003d058595a200000000000005a750000ac730000173458595a20000000000000281a0000159f0000b83663757276000000000000000101cd0000736633320000000000010c42000005defffff326000007920000fd91fffffba2fffffda3000003dc0000c06cffc00011080094009403012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffdb0043000101010101010201010203020202030403030303040604040404040607060606060606070707070707070708080808080809090909090b0b0b0b0b0b0b0b0b0bffdb004301020202030303050303050b0806080b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0bffdd0004000affda000c03010002110311003f00ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd0ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd1ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd2ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd3ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd4ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd5ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd6ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd7ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd0ff003ffa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803fffd9");
    private static String teamTransactionTypeName = "1234";
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
    private TeamTransactionService teamTransactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private TeamTransactionTypeService teamTransactionTypeService;

    @Autowired
    private TeamTypeService teamTypeService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;

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
    private SeasonService seasonService;

    @Autowired
    private TeamService teamService;

    @Test
    @Order(1)
    void createTeamTransaction() throws Exception {
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

        zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);

        country = countryService.saveCountry(country);
        location.setCountry(country);

        locationService.saveLocation(location);
        team.setLocation(location);

        team = teamService.saveTeam(team);
        teamTransaction.setTeam(team);
        updatedTeamTransaction.setTeam(team);

        headers.set("session", session.getSessionCode());
        HttpEntity<TeamTransaction> request = new HttpEntity<>(teamTransaction, headers);

        String url = hostname + port
                + TeamTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<TeamTransaction> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, TeamTransaction.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        TeamTransaction teamTransaction1 = response.getBody();
        assertTrue(teamTransaction1.getDateOfTeamTransaction().equals(dateOfTeamTransaction));
        assertTrue(teamTransaction1.getTeamTransactionType().getId().equals(teamTransactionType.getId()));
        assertTrue(teamTransaction1.getDescription().equals(description));
        assertTrue(teamTransaction1.getTeam().getId().equals(team.getId()));
        teamTransactionId = teamTransaction1.getId();
        updatedTeamTransaction.setId(teamTransactionId);
    }

    @Test
    @Order(2)
    void readTeamTransactionByTeamTransactionName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TeamTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&description=" + description
                + "&teamId=" + team.getId();
        ResponseEntity<TeamTransaction[]> teamTransactionList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, TeamTransaction[].class);
        TeamTransaction teamTransaction1 = teamTransactionList.getBody()[0];
        assertTrue(teamTransaction1.getDateOfTeamTransaction().equals(dateOfTeamTransaction));
        assertTrue(teamTransaction1.getTeamTransactionType().getId().equals(teamTransactionType.getId()));
        assertTrue(teamTransaction1.getDescription().equals(description));
        assertTrue(teamTransaction1.getTeam().getId().equals(team.getId()));
    }

    @Test
    @Order(3)
    void readTeamTransactionByTeamTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<TeamTransaction[]> teamTransactionList = this.restTemplate.exchange(
                hostname + port + TeamTransactionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&teamTransactionId=" + teamTransactionId, HttpMethod.GET, request, TeamTransaction[].class);
        TeamTransaction teamTransaction1 = teamTransactionList.getBody()[0];
        assertTrue(teamTransaction1.getDateOfTeamTransaction().equals(dateOfTeamTransaction));
        assertTrue(teamTransaction1.getTeamTransactionType().getId().equals(teamTransactionType.getId()));
        assertTrue(teamTransaction1.getDescription().equals(description));
        assertTrue(teamTransaction1.getTeam().getId().equals(team.getId()));
    }

    @Test
    @Order(4)
    void updateTeamTransactionByTeamTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(updatedTeamTransaction, headers);

        String url = hostname + port
                + TeamTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&teamTransactionId=" + teamTransactionId;
        URI uri = new URI(url);
        ResponseEntity<TeamTransaction[]> teamTransactionList = this.restTemplate.exchange(uri,
                HttpMethod.PUT, request, TeamTransaction[].class);
        assertTrue(teamTransactionList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedTeamTransactionByTeamTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<TeamTransaction[]> teamTransactionList = this.restTemplate.exchange(
                hostname + port
                        + TeamTransactionPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&teamTransactionId=" + teamTransactionId, HttpMethod.GET, request, TeamTransaction[].class);
        assertTrue(teamTransactionList.getStatusCode().is2xxSuccessful());
        TeamTransaction teamTransaction1 = teamTransactionList.getBody()[0];
        assertTrue(teamTransaction1.getDateOfTeamTransaction().equals(updatedDateOfTeamTransaction));
        assertTrue(teamTransaction1.getTeamTransactionType().getId().equals(teamTransactionType.getId()));
        assertTrue(teamTransaction1.getDescription().equals(updatedDescription));
        assertTrue(teamTransaction1.getTeam().getId().equals(team.getId()));
        teamTransactionId = teamTransaction1.getId();
    }

    @Test
    @Order(6)
    void deleteTeamTransactionByTeamTransactionId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TeamTransactionPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&teamTransactionId=" + teamTransactionId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        teamService.deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                team.getTeamName(),
                levelOfCompetition.getId(),
                gameType.getId());

        gameTypeService.deleteByGameTypeName(
                gameType.getGameTypeName());

        levelOfCompetitionService.deleteLevelOfCompetitionByLevelOfCompetitionName(
                levelOfCompetition.getLevelOfCompetitionName());

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

        seasonService.deleteSeasonBySeasonName(
                firstSeason.getSeasonName());

        seasonService.deleteSeasonBySeasonName(
                lastSeason.getSeasonName());

        teamTypeService.deleteTeamTypeByTeamTypeName(
                teamType.getTeamTypeName());

        teamTransactionTypeService.deleteTeamTransactionTypeByTeamTransactionTypeName(
                teamTransactionType.getTeamTransactionTypeName());


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
