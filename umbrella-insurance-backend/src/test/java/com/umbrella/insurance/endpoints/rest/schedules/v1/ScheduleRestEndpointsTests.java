package com.umbrella.insurance.endpoints.rest.schedules.v1;

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
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.schedules.v1.db.SchedulePrivilege;
import com.umbrella.insurance.core.models.schedules.v1.db.jpa.ScheduleService;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
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
public class ScheduleRestEndpointsTests {
    @Autowired
    private ScheduleRestEndpoints scheduleRestEndpoints;
    @Autowired
    private PersonService personService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private ZipCodeService zipCodeService;
    @Autowired
    private StateService stateService;
    @Autowired
    private SeasonService seasonService;

    @Test
    void contextLoads() throws Exception {
        assertThat(scheduleRestEndpoints).isNotNull();
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

    private static Long scheduleId;
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
    private static Season season = new Season();
    private static Game game = new Game();
    private static Schedule schedule = new Schedule();
    private static Schedule updatedSchedule = new Schedule();
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
    private GameService gameService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private SeasonTypeService seasonTypeService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private StreetAddressService streetService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Test
    @Order(1)
    void createSchedule() throws Exception {
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
        userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<Schedule> request = new HttpEntity<>(schedule, headers);

        streetAddress = streetAddressService.saveStreetAddress(streetAddress);
        location.setStreetAddress(streetAddress);

        season = seasonService.saveSeason(season);
        schedule.setSeason(season);
        updatedSchedule.setSeason(season);

        city = cityService.saveCity(city);
        location.setCity(city);

        state = stateService.saveState(state);
        location.setState(state);

        zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);

        countryService.saveCountry(country);
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

        String url = hostname + port
                + SchedulePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Schedule> schedule1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Schedule.class);
        assertTrue(schedule1.getStatusCode().is2xxSuccessful());
        assertTrue(schedule1.getBody().getGame().getId().equals(game.getId()));
        assertTrue(schedule1.getBody().getSeason().getId().equals(season.getId()));
        scheduleId = schedule1.getBody().getId();
        updatedSchedule.setId(scheduleId);
    }

    @Test
    @Order(2)
    void readScheduleByScheduleName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + SchedulePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&gameId=" + game.getId();
        ResponseEntity<Schedule[]> scheduleList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Schedule[].class);
        assertTrue(scheduleList.getStatusCode().is2xxSuccessful());
        Schedule schedule1 = scheduleList.getBody()[0];
        assertTrue(schedule1.getGame().getId().equals(game.getId()));
        assertTrue(schedule1.getSeason().getId().equals(season.getId()));
    }

    @Test
    @Order(3)
    void readScheduleByScheduleId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Schedule[]> scheduleList = this.restTemplate.exchange(
                hostname + port + SchedulePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&scheduleId=" + scheduleId, HttpMethod.GET, request, Schedule[].class);
        assertTrue(scheduleList.getStatusCode().is2xxSuccessful());
        Schedule schedule1 = scheduleList.getBody()[0];
        assertTrue(schedule1.getGame().getId().equals(game.getId()));
        assertTrue(schedule1.getSeason().getId().equals(season.getId()));
    }

    @Test
    @Order(4)
    void updateScheduleByScheduleId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(updatedSchedule, headers);

        String url = hostname + port
                + SchedulePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&scheduleId=" + scheduleId;
        URI uri = new URI(url);
        ResponseEntity<Schedule[]> scheduleList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Schedule[].class);
        assertTrue(scheduleList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedScheduleByScheduleId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Schedule[]> scheduleList = this.restTemplate.exchange(
                hostname + port
                        + SchedulePrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&scheduleId=" + scheduleId, HttpMethod.GET, request, Schedule[].class);
        assertTrue(scheduleList.getStatusCode().is2xxSuccessful());
        Schedule schedule1 = scheduleList.getBody()[0];
        assertTrue(schedule1.getGame().getId().equals(game.getId()));
        assertTrue(schedule1.getSeason().getId().equals(season.getId()));
        scheduleId = schedule1.getId();
    }

    @Test
    @Order(6)
    void deleteScheduleByScheduleId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        String url = hostname + port
                + SchedulePrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&scheduleId=" + scheduleId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        seasonService.deleteSeasonBySeasonName(season.getSeasonName());

        streetAddressService.deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                streetAddress.getStreetAddressLine1(),
                streetAddress.getStreetAddressLine2());

        cityService.deleteCityByCityName(
                city.getCityName());

        stateService.deleteStateByStateName(
                state.getStateName());

        zipCodeService.deleteZipCodeByZipCodeValue(zipCode.getZipCodeValue());

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

        gameService.deleteGameByGameName(game.getGameName());

        sessionService.deleteSession(session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn);
    }
}
