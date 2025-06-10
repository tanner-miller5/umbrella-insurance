package com.umbrella.insurance.endpoints.rest.users.bets.pendingBets.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.charities.v1.db.jpa.CharityService;
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
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
import com.umbrella.insurance.core.models.people.analysts.v1.db.jpa.AnalystService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa.PendingBetStateService;
import com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.PendingBetPrivilege;
import com.umbrella.insurance.core.models.users.bots.v1.db.jpa.BotService;
import com.umbrella.insurance.core.models.users.fees.v1.db.jpa.FeeService;
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
import java.math.BigInteger;
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
public class PendingBetRestEndpointsTests {
    @Autowired
    private PendingBetRestEndpoints pendingBetRestEndpoints;
    @Autowired
    private StreetAddressService streetAddressService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private FeeService feeService;
    @Autowired
    private CharityService charityService;
    @Autowired
    private CityService cityService;
    @Autowired
    private StateService stateService;
    @Autowired
    private ZipCodeService zipCodeService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private BotService botService;

    @Test
    void contextLoads() throws Exception {
        assertThat(pendingBetRestEndpoints).isNotNull();
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

    private static Long pendingBetId;
    private static String pendingBetName = "1234";
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-12-12 12:12:12");
    private static Timestamp canceledDateTime = Timestamp.valueOf("1112-11-11 11:11:1111");
    private static Double minimumOdds = 1.0;
    private static Double wagerAmount = 2.0;
    private static Boolean isBotBet = false;
    private static Boolean isCharityBet = false;
    private static String updatedPendingBetName = "12345";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2020-11-10 10:10:14");
    private static Double updatedMinimumOdds = 5.0;
    private static Double updatedWagerAmount = 100.02;
    private static Boolean updatedIsBotBet = true;
    private static Boolean updatedIsCharityBet = true;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static String deviceName = "1234";
    private static String ipAddress = "2";
    private static String userAgent = "3";
    private static String pendingBetStateName = "1";
    private static String feeName = "1234";
    private static Double feePercent = 2.0;
    private static Double fixedFee = 3.0;
    private static String sessionCode = "11";
    private static Timestamp startDateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static Timestamp endDateTime = Timestamp.valueOf("2020-11-11 11:11:11");
    private static BigInteger minutesToExpire = BigInteger.valueOf(2);
    private static String unitName = "1234";
    private static Timestamp dateTime = Timestamp.valueOf("2023-12-12 12:12:10");
    private static String gameName = "1";
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
    private static Person person = new Person();
    private static Specialty specialty = new Specialty();
    private static Analyst analyst = new Analyst();
    private static User user = new User();
    private static Unit unit = new Unit();
    private static Bot bot = new Bot();
    private static Charity charity = new Charity();
    private static PendingBetState pendingBetState = new PendingBetState();
    private static Fee fee = new Fee();
    private static PendingPolicy originalPendingPolicy = new PendingPolicy();
    private static AccountBalanceTransaction accountBalanceEscrowTransaction = new AccountBalanceTransaction();
    private static AccountBalanceTransaction accountBalanceCanceledEscrowTransaction = new AccountBalanceTransaction();
    private static PendingBet pendingBet = new PendingBet();
    private static PendingBet updatedPendingBet = new PendingBet();
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

        specialty.setSpecialtyName("test");

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

        pendingBetState.setPendingBetStateName(pendingBetStateName);

        charity.setCharityName("kids");

        bot.setAmountFunded(20.0);
        bot.setBotName("botName");
        bot.setCreatedDateTime(Timestamp.valueOf("2011-11-11 11:11:11"));
        bot.setCreatedDateTime(Timestamp.valueOf("2012-12-11 11:11:11"));
        bot.setIsDeleted(false);
        bot.setIsDisabled(false);

        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(false);

        fee.setFeeName(feeName);
        fee.setFeePercent(feePercent);
        fee.setFixedFee(fixedFee);

        unit.setUnitName(unitName);

        pendingBet.setPendingBetName(pendingBetName);
        pendingBet.setCreatedDateTime(createdDateTime);
        pendingBet.setMinimumOdds(minimumOdds);
        pendingBet.setMaximumWagerAmount(wagerAmount);
        pendingBet.setIsBotBet(isBotBet);
        pendingBet.setIsCharityBet(isCharityBet);
        pendingBet.setSplitPendingBetId1(null);
        pendingBet.setSplitPendingBetId2(null);
        pendingBet.setCanceledDateTime(canceledDateTime);

        updatedPendingBet.setPendingBetName(updatedPendingBetName);
        updatedPendingBet.setCreatedDateTime(updatedCreatedDateTime);
        updatedPendingBet.setMinimumOdds(updatedMinimumOdds);
        updatedPendingBet.setMaximumWagerAmount(updatedWagerAmount);
        updatedPendingBet.setIsBotBet(updatedIsBotBet);
        updatedPendingBet.setIsCharityBet(updatedIsCharityBet);
        updatedPendingBet.setSplitPendingBetId1(null);
        updatedPendingBet.setSplitPendingBetId2(null);
        updatedPendingBet.setCanceledDateTime(canceledDateTime);
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
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private SeasonTypeService seasonTypeService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PendingBetStateService pendingBetStateService;

    @Autowired
    private AnalystService analystService;

    @Autowired
    private PasswordService passwordService;

    @Test
    @Order(1)
    void createPendingBet() throws Exception {
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
        pendingBet.setSession(session);
        updatedPendingBet.setSession(session);

        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        unit = unitService.saveUnit(unit);
        fee.setUnit(unit);
        bot.setUnit(unit);
        pendingBet.setUnit(unit);
        updatedPendingBet.setUnit(unit);

        pendingBetState = pendingBetStateService.savePendingBetState(pendingBetState);
        pendingBet.setPendingBetState(pendingBetState);
        updatedPendingBet.setPendingBetState(pendingBetState);

        fee = feeService.saveFee(fee);
        fee.setUnit(unit);
        pendingBet.setFee(fee);
        updatedPendingBet.setFee(fee);

        charity = charityService.saveCharity(charity);
        pendingBet.setCharity(charity);
        updatedPendingBet.setCharity(charity);


        streetAddressService.saveStreetAddress(streetAddress);
        location.setStreetAddress(streetAddress);

        city = cityService.saveCity(city);
        location.setCity(city);

        state = stateService.saveState(state);
        location.setState(state);

        zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);

        country = countryService.saveCountry(country);
        location.setCountry(country);

        location = locationService.saveLocation(location);
        game.setLocation(location);

        gameStatus = gameStatusService.saveGameStatus(gameStatus);
        game.setGameStatus(gameStatus);

        gameTypeService.saveGameType(gameType);
        game.setGameType(gameType);

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        pendingBet.setGame(game);
        updatedPendingBet.setGame(game);

        specialtyService.saveSpecialty(specialty);
        analyst.setSpecialty(specialty);

        person = personService.savePerson(person);
        analyst.setPerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        bot.setUser(user);

        analyst = analystService.saveAnalyst(analyst);
        bot.setAnalyst(analyst);

        bot = botService.saveBot(bot);
        bot.setUnit(unit);
        bot.setAnalyst(analyst);
        bot.setUser(user);
        pendingBet.setBot(bot);
        updatedPendingBet.setBot(bot);


        headers.set("session", session.getSessionCode());
        HttpEntity<PendingBet> request = new HttpEntity<>(pendingBet, headers);

        String url = hostname + port
                + PendingBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<PendingBet> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, PendingBet.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        PendingBet pendingBet1 = response.getBody();
        assertTrue(pendingBet1.getPendingBetName().equals(pendingBetName));
        assertTrue(pendingBet1.getSession().getId().equals(session.getId()));
        assertTrue(pendingBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingBet1.getGame().getId().equals(game.getId()));
        assertTrue(pendingBet1.getMaximumWagerAmount().equals(wagerAmount));
        assertTrue(pendingBet1.getMinimumOdds().equals(minimumOdds));
        assertTrue(pendingBet1.getUnit().getId().equals(unit.getId()));
        assertTrue(pendingBet1.getBot().getId().equals(bot.getId()));
        assertTrue(pendingBet1.getIsBotBet().equals(isBotBet));
        assertTrue(pendingBet1.getIsCharityBet().equals(isCharityBet));
        assertTrue(pendingBet1.getCharity().getId().equals(charity.getId()));
        assertTrue(pendingBet1.getPendingBetState().getId().equals(pendingBetState.getId()));
        assertTrue(pendingBet1.getFee().getId().equals(fee.getId()));
        assertTrue(pendingBet1.getCanceledDateTime().equals(canceledDateTime));
        pendingBetId = pendingBet1.getId();
        updatedPendingBet.setId(pendingBetId);
    }

    @Test
    @Order(2)
    void readPendingBetByPendingBetName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PendingBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingBetName=" + pendingBetName;
        ResponseEntity<PendingBet[]> pendingBetList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, PendingBet[].class);
        assertTrue(pendingBetList.getStatusCode().is2xxSuccessful());
        PendingBet pendingBet1 = pendingBetList.getBody()[0];
        assertTrue(pendingBet1.getPendingBetName().equals(pendingBetName));
        assertTrue(pendingBet1.getSession().getId().equals(session.getId()));
        assertTrue(pendingBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingBet1.getGame().getId().equals(game.getId()));
        assertTrue(pendingBet1.getMaximumWagerAmount().equals(wagerAmount));
        assertTrue(pendingBet1.getMinimumOdds().equals(minimumOdds));
        assertTrue(pendingBet1.getUnit().getId().equals(unit.getId()));
        assertTrue(pendingBet1.getBot().getId().equals(bot.getId()));
        assertTrue(pendingBet1.getIsBotBet().equals(isBotBet));
        assertTrue(pendingBet1.getIsCharityBet().equals(isCharityBet));
        assertTrue(pendingBet1.getCharity().getId().equals(charity.getId()));
        assertTrue(pendingBet1.getPendingBetState().getId().equals(pendingBetState.getId()));
        assertTrue(pendingBet1.getFee().getId().equals(fee.getId()));
        assertTrue(pendingBet1.getCanceledDateTime().equals(canceledDateTime));
    }

    @Test
    @Order(3)
    void readPendingBetByPendingBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<PendingBet[]> pendingBetList = this.restTemplate.exchange(
                hostname + port + PendingBetPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&pendingBetId=" + pendingBetId, HttpMethod.GET, request, PendingBet[].class);
        assertTrue(pendingBetList.getStatusCode().is2xxSuccessful());
        PendingBet pendingBet1 = pendingBetList.getBody()[0];
        assertTrue(pendingBet1.getPendingBetName().equals(pendingBetName));
        assertTrue(pendingBet1.getSession().getId().equals(session.getId()));
        assertTrue(pendingBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingBet1.getGame().getId().equals(game.getId()));
        assertTrue(pendingBet1.getMaximumWagerAmount().equals(wagerAmount));
        assertTrue(pendingBet1.getMinimumOdds().equals(minimumOdds));
        assertTrue(pendingBet1.getUnit().getId().equals(unit.getId()));
        assertTrue(pendingBet1.getBot().getId().equals(bot.getId()));
        assertTrue(pendingBet1.getIsBotBet().equals(isBotBet));
        assertTrue(pendingBet1.getIsCharityBet().equals(isCharityBet));
        assertTrue(pendingBet1.getCharity().getId().equals(charity.getId()));
        assertTrue(pendingBet1.getPendingBetState().getId().equals(pendingBetState.getId()));
        assertTrue(pendingBet1.getFee().getId().equals(fee.getId()));
        assertTrue(pendingBet1.getCanceledDateTime().equals(canceledDateTime));
    }

    @Test
    @Order(4)
    void updatePendingBetByPendingBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<PendingBet> request = new HttpEntity<>(updatedPendingBet, headers);

        String url = hostname + port
                + PendingBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingBetId=" + pendingBetId;
        URI uri = new URI(url);
        ResponseEntity<PendingBet[]> pendingBetList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, PendingBet[].class);
        assertTrue(pendingBetList.getStatusCode().is2xxSuccessful());

    }

    @Test
    @Order(5)
    void readUpdatedPendingBetByPendingBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<PendingBet[]> pendingBetList = this.restTemplate.exchange(
                hostname + port
                        + PendingBetPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&pendingBetId=" + pendingBetId, HttpMethod.GET, request, PendingBet[].class);
        assertTrue(pendingBetList.getStatusCode().is2xxSuccessful());
        PendingBet pendingBet1 = pendingBetList.getBody()[0];
        assertTrue(pendingBet1.getPendingBetName().equals(updatedPendingBetName));
        assertTrue(pendingBet1.getSession().getId().equals(session.getId()));
        assertTrue(pendingBet1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(pendingBet1.getGame().getId().equals(game.getId()));
        assertTrue(pendingBet1.getMaximumWagerAmount().equals(updatedWagerAmount));
        assertTrue(pendingBet1.getMinimumOdds().equals(updatedMinimumOdds));
        assertTrue(pendingBet1.getUnit().getId().equals(unit.getId()));
        assertTrue(pendingBet1.getBot().getId().equals(bot.getId()));
        assertTrue(pendingBet1.getIsBotBet().equals(updatedIsBotBet));
        assertTrue(pendingBet1.getIsCharityBet().equals(updatedIsCharityBet));
        assertTrue(pendingBet1.getCharity().getId().equals(charity.getId()));
        assertTrue(pendingBet1.getPendingBetState().getId().equals(pendingBetState.getId()));
        assertTrue(pendingBet1.getFee().getId().equals(fee.getId()));
        assertTrue(pendingBet1.getCanceledDateTime().equals(canceledDateTime));
        pendingBetId = pendingBet1.getId();
    }

    @Test
    @Order(6)
    void deletePendingBetByPendingBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PendingBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingBetId=" + pendingBetId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        pendingBetStateService.deletePendingBetStateByPendingBetStateName(
                pendingBetState.getPendingBetStateName());

        botService.deleteBotByBotName(bot.getBotName());

        analystService.deleteAnalystByPersonId(person.getId());

        specialtyService.deleteBySpecialtyName(specialty.getSpecialtyName());

        userService.deleteUserByPersonId(person.getId());

        personService.deletePersonBySsn(person.getSsn());

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
                gameName);

        feeService.deleteFeeByFeeName(feeName);

        unitService.deleteUnitByUnitName(unitName);

        charityService.deleteCharityByCharityName(charity.getCharityName());

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
