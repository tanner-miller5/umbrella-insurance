package com.umbrella.insurance.endpoints.rest.records.teamRecords.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Country;
import com.umbrella.insurance.core.models.entities.Location;
import com.umbrella.insurance.core.models.entities.State;
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
import com.umbrella.insurance.core.models.records.teamRecords.v1.db.TeamRecordPrivilege;
import com.umbrella.insurance.core.models.records.teamRecords.v1.db.jpa.TeamRecordService;
import com.umbrella.insurance.core.models.records.v1.db.jpa.RecordService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.entities.Record;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamRecordRestEndpointsTests {
    @Autowired
    private TeamRecordRestEndpoints teamRecordRestEndpoints;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private RecordService recordService;
    @Autowired
    private GameTypeService gameTypeService;
    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;
    @Autowired
    private CityService cityService;
    @Autowired
    private StateService stateService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private TeamTypeService teamTypeService;
    @Autowired
    private PersonService personService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(teamRecordRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static Long teamRecordId = 1l;
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
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    static {
        createUserRequest.setPassword(password);
        createUserRequest.setUsername(username);
        createUserRequest.setSsn(ssn);
        createUserRequest.setPhoneNumber(phoneNumber);
        createUserRequest.setSurname(surname);
        createUserRequest.setMiddleName(middle);
        createUserRequest.setFirstName(first);
        createUserRequest.setEmailAddress(emailAddress);
        createUserRequest.setDateOfBirth(dateOfBirth);
        createUserRequest.setConsentedToEmails(false);
        createUserRequest.setConsentedToTexts(false);
        createUserRequest.setConsentedToTermsAndConditions(true);

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
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private TeamRecordService teamRecordService;

    @Autowired
    private TeamService teamService;

    @Test
    @Order(1)
    void createTeamRecordRecord() throws Exception {
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
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<TeamRecord> request = new HttpEntity<>(teamRecord, headers);

        record = recordService.saveRecord(record);
        teamRecord.setRecord(record);
        updatedTeamRecord.setRecord(record);

        gameType = gameTypeService.saveGameType(gameType);
        team.setGameType(gameType);

        levelOfCompetition = levelOfCompetitionService.saveLevelOfCompetition(levelOfCompetition);
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

        String url = hostname + port
                + TeamRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<TeamRecord> teamRecord1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, TeamRecord.class);
        assertTrue(teamRecord1.getStatusCode().is2xxSuccessful());
        assertTrue(teamRecord1.getBody().getRecord().getId().equals(record.getId()));
        assertTrue(teamRecord1.getBody().getTeam().getId().equals(teamRecord.getTeam().getId()));
        assertTrue(teamRecord1.getBody().getSeason().getId().equals(season.getId()));
        teamRecordId = teamRecord1.getBody().getId();
        updatedTeamRecord.setId(teamRecordId);
    }

    @Test
    @Order(2)
    void readTeamRecordRecordByTeamRecordName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TeamRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&seasonId=" + season.getId()
                + "&teamId=" + teamRecord.getTeam().getId();
        ResponseEntity<TeamRecord[]> teamRecordList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, TeamRecord[].class);
        assertTrue(teamRecordList.getStatusCode().is2xxSuccessful());
        TeamRecord teamRecord1 = teamRecordList.getBody()[0];
        assertTrue(teamRecord1.getSeason().getId().equals(season.getId()));
        assertTrue(teamRecord1.getTeam().getId().equals(team.getId()));
    }

    @Test
    @Order(3)
    void readTeamRecordRecordByTeamRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<TeamRecord[]> teamRecordList = this.restTemplate.exchange(
                hostname + port + TeamRecordPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&teamRecordId=" + teamRecordId, HttpMethod.GET, request, TeamRecord[].class);
        assertTrue(teamRecordList.getStatusCode().is2xxSuccessful());
        TeamRecord teamRecord1 = teamRecordList.getBody()[0];
        assertTrue(teamRecord1.getSeason().getId().equals(season.getId()));
        assertTrue(teamRecord1.getTeam().getId().equals(teamRecord.getTeam().getId()));
    }

    @Test
    @Order(4)
    void updateTeamRecordRecordByTeamRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<TeamRecord> request = new HttpEntity<>(updatedTeamRecord, headers);

        String url = hostname + port
                + TeamRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&teamRecordId=" + teamRecordId;
        URI uri = new URI(url);
        ResponseEntity<TeamRecord[]> teamRecordRecordList = this.restTemplate.exchange(
                uri, HttpMethod.PUT, request, TeamRecord[].class);
        assertTrue(teamRecordRecordList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedTeamRecordRecordByTeamRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<TeamRecord[]> teamRecordList = this.restTemplate.exchange(
                hostname + port
                        + TeamRecordPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&teamRecordId=" + teamRecordId, HttpMethod.GET, request, TeamRecord[].class);
        assertTrue(teamRecordList.getStatusCode().is2xxSuccessful());
        TeamRecord teamRecord1 = teamRecordList.getBody()[0];
        assertTrue(teamRecord1.getSeason().getId().equals(season.getId()));
        assertTrue(teamRecord1.getTeam().getId().equals(team.getId()));
        teamRecordId = teamRecord1.getId();
    }

    @Test
    @Order(6)
    void deleteTeamRecordRecordByTeamRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + TeamRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&teamRecordId=" + teamRecordId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        recordService.deleteRecordByRecordName(
                record.getRecordName());

        teamService.deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                team.getTeamName(), levelOfCompetition.getId(),
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
                season.getSeasonName());

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

        personService.deletePersonBySsn(ssn);
    }
}
