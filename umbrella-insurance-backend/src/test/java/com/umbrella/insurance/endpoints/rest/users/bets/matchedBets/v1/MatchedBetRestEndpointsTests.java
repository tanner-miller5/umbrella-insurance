package com.umbrella.insurance.endpoints.rest.users.bets.matchedBets.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa.ApplicationRoleApplicationPrivilegeRelationshipService;
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
import com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa.MatchedBetStateService;
import com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.MatchedBetPrivilege;
import com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.jpa.MatchedBetService;
import com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa.PendingBetStateService;
import com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.jpa.PendingBetService;
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
public class MatchedBetRestEndpointsTests {
    @Autowired
    private MatchedBetRestEndpoints matchedBetRestEndpoints;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private SeasonTypeService seasonTypeService;
    @Autowired
    private GameTypeService gameTypeService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ZipCodeService zipCodeService;
    @Autowired
    private StateService stateService;
    @Autowired
    private CityService cityService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private AnalystService analystService;
    @Autowired
    private BotService botService;
    @Autowired
    private PendingBetService pendingBetService;
    @Autowired
    private PendingBetStateService pendingBetStateService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private FeeService feeService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private CharityService charityService;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private ApplicationRoleApplicationPrivilegeRelationshipService applicationRoleApplicationPrivilegeRelationshipService;
    @Autowired
    private MatchedBetStateService matchedBetStateService;
    @Autowired
    private MatchedBetService matchedBetService;
    @Autowired
    private GameStatusService gameStatusService;
    @Autowired
    private GameService gameService;
    @Autowired
    private StreetAddressService streetAddressService;


    @Test
    void contextLoads() throws Exception {
        assertThat(matchedBetRestEndpoints).isNotNull();
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

    private static Long matchedBetId;
    private static Timestamp createdDateTime = Timestamp.valueOf("2000-01-20 14:23:59");
    private static Double odds1 = 1.0;
    private static Double odds2 = 2.0;
    private static Double wagerAmount1 = 20.00;
    private static Double wagerAmount2= 10.00;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2001-02-22 13:24:58");
    private static Double updatedOdds1 = 3.0;
    private static Double updatedOdds2 = 4.0;
    private static Double updatedWagerAmount1 = 5.0;
    private static Double updatedWagerAmount2 = 6.0;

    private static String matchedBetStateName = "1234";
    private static String pendingBetName = "1234";
    private static String pendingBetName2 = "12345";
    private static Double minimumOdds = 1.0;
    private static Double wagerAmount = 2.0;
    private static Boolean isBotBet = false;
    private static Boolean isCharityBet = false;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static String pendingBetStateName = "1";
    private static String feeName = "1234";
    private static Double feePercent = 2.0;
    private static Double fixedFee = 3.0;
    private static String sessionCode = "11";
    private static String unitName = "1234l";
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
    private static Specialty specialty = new Specialty();
    private static Analyst analyst = new Analyst();
    private static User user = new User();
    private static Unit unit = new Unit();
    private static Bot bot = new Bot();
    private static Charity charity = new Charity();
    private static PendingBetState pendingBetState = new PendingBetState();
    private static Fee fee = new Fee();
    private static PendingBet pendingBet1 = new PendingBet();
    private static PendingBet pendingBet2 = new PendingBet();
    private static MatchedBetState matchedBetState = new MatchedBetState();
    private static MatchedBet matchedBet = new MatchedBet();
    private static MatchedBet updatedMatchedBet = new MatchedBet();
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

        pendingBet1.setPendingBetName(pendingBetName);
        pendingBet1.setCreatedDateTime(createdDateTime);
        pendingBet1.setMinimumOdds(minimumOdds);
        pendingBet1.setMaximumWagerAmount(wagerAmount);
        pendingBet1.setIsBotBet(isBotBet);
        pendingBet1.setIsCharityBet(isCharityBet);
        pendingBet1.setSplitPendingBetId1(null);
        pendingBet1.setSplitPendingBetId2(null);

        pendingBet2.setPendingBetName(pendingBetName2);
        pendingBet2.setCreatedDateTime(createdDateTime);
        pendingBet2.setMinimumOdds(minimumOdds);
        pendingBet2.setMaximumWagerAmount(wagerAmount);
        pendingBet2.setIsBotBet(isBotBet);
        pendingBet2.setIsCharityBet(isCharityBet);
        pendingBet2.setSplitPendingBetId1(null);
        pendingBet2.setSplitPendingBetId2(null);

        matchedBetState.setMatchedBetStateName(matchedBetStateName);

        matchedBet.setCreatedDateTime(createdDateTime);

        matchedBet.setOdds1(odds1);
        matchedBet.setOdds2(odds2);
        matchedBet.setWagerAmount1(wagerAmount1);
        matchedBet.setWagerAmount2(wagerAmount2);

        updatedMatchedBet.setCreatedDateTime(updatedCreatedDateTime);

        updatedMatchedBet.setOdds1(updatedOdds1);
        updatedMatchedBet.setOdds2(updatedOdds2);
        updatedMatchedBet.setWagerAmount1(updatedWagerAmount1);
        updatedMatchedBet.setWagerAmount2(updatedWagerAmount2);
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

    @Test
    @Order(1)
    void createMatchedBet() throws Exception {
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
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        matchedBetState = matchedBetStateService.saveMatchedBetState(matchedBetState);
        matchedBet.setMatchedBetState(matchedBetState);
        updatedMatchedBet.setMatchedBetState(matchedBetState);

        unit = unitService.saveUnit(unit);
        fee.setUnit(unit);
        bot.setUnit(unit);
        pendingBet1.setUnit(unit);
        pendingBet2.setUnit(unit);

        pendingBetStateService.savePendingBetState(pendingBetState);
        pendingBet1.setPendingBetState(pendingBetState);
        pendingBet2.setPendingBetState(pendingBetState);

        feeService.saveFee(fee);
        fee = feeService.getFeeByFeeName(feeName).get();
        fee.setUnit(unit);
        pendingBet1.setFee(fee);
        pendingBet2.setFee(fee);
        matchedBet.setFeeId1(fee);
        updatedMatchedBet.setFeeId1(fee);
        matchedBet.setFeeId2(fee);
        updatedMatchedBet.setFeeId2(fee);

        charity = charityService.saveCharity(charity);
        pendingBet1.setCharity(charity);
        pendingBet2.setCharity(charity);

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

        gameTypeService.saveGameType(gameType);
        game.setGameType(gameType);

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        pendingBet1.setGame(game);
        pendingBet2.setGame(game);

        specialty = specialtyService.saveSpecialty(
                specialty);
        analyst.setSpecialty(specialty);

        personService.savePerson(person);
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
        pendingBet1.setBot(bot);
        pendingBet2.setBot(bot);

        pendingBetService.savePendingBet(pendingBet1);
        matchedBet.setPendingBet1(pendingBet1);
        updatedMatchedBet.setPendingBet1(pendingBet1);

        pendingBetService.savePendingBet(pendingBet2);
        matchedBet.setPendingBet2(pendingBet2);
        updatedMatchedBet.setPendingBet2(pendingBet2);

        headers.set("session", session.getSessionCode());
        HttpEntity<MatchedBet> request = new HttpEntity<>(matchedBet, headers);

        String url = hostname + port
                + MatchedBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<MatchedBet> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, MatchedBet.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        MatchedBet matchedBet1 = response.getBody();
        //assertTrue(matchedBet1.getPendingBet1().getId().equals(pendingBet1.getId()));
        //assertTrue(matchedBet1.getPendingBet2().getId().equals(pendingBet2.getId()));
        assertTrue(matchedBet1.getOdds1().equals(odds1));
        assertTrue(matchedBet1.getOdds2().equals(odds2));
        assertTrue(matchedBet1.getWagerAmount1().equals(wagerAmount1));
        assertTrue(matchedBet1.getWagerAmount2().equals(wagerAmount2));
        assertTrue(matchedBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(matchedBet1.getFeeId1().getId().equals(matchedBet.getFeeId1().getId()));
        assertTrue(matchedBet1.getFeeId2().getId().equals(matchedBet.getFeeId2().getId()));
        assertTrue(matchedBet1.getMatchedBetState().getId().equals(
                matchedBet.getMatchedBetState().getId()));
        matchedBetId = matchedBet1.getId();
        updatedMatchedBet.setId(matchedBetId);
    }

    @Test
    @Order(2)
    void readMatchedBetByMatchedBetName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + MatchedBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingBetId=" + pendingBet1.getId();
        ResponseEntity<MatchedBet[]> matchedBetList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, MatchedBet[].class);
        assertTrue(matchedBetList.getStatusCode().is2xxSuccessful());
        MatchedBet matchedBet1 = matchedBetList.getBody()[0];
        //assertTrue(matchedBet1.getPendingBet1().getId().equals(pendingBet1.getId()));
        //assertTrue(matchedBet1.getPendingBet2().getId().equals(pendingBet2.getId()));
        assertTrue(matchedBet1.getOdds1().equals(odds1));
        assertTrue(matchedBet1.getOdds2().equals(odds2));
        assertTrue(matchedBet1.getWagerAmount1().equals(wagerAmount1));
        assertTrue(matchedBet1.getWagerAmount2().equals(wagerAmount2));
        assertTrue(matchedBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(matchedBet1.getFeeId1().getId().equals(matchedBet.getFeeId1().getId()));
        assertTrue(matchedBet1.getFeeId2().getId().equals(matchedBet.getFeeId2().getId()));
        assertTrue(matchedBet1.getMatchedBetState().getId().equals(
                matchedBet.getMatchedBetState().getId()));
    }

    @Test
    @Order(3)
    void readMatchedBetByMatchedBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<MatchedBet[]> matchedBetList = this.restTemplate.exchange(
                hostname + port + MatchedBetPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&matchedBetId=" + matchedBetId, HttpMethod.GET, request, MatchedBet[].class);
        assertTrue(matchedBetList.getStatusCode().is2xxSuccessful());
        MatchedBet matchedBet1 = matchedBetList.getBody()[0];
        //assertTrue(matchedBet1.getPendingBet1().getId().equals(pendingBet1.getId()));
        //assertTrue(matchedBet1.getPendingBet2().getId().equals(pendingBet2.getId()));
        assertTrue(matchedBet1.getOdds1().equals(odds1));
        assertTrue(matchedBet1.getOdds2().equals(odds2));
        assertTrue(matchedBet1.getWagerAmount1().equals(wagerAmount1));
        assertTrue(matchedBet1.getWagerAmount2().equals(wagerAmount2));
        assertTrue(matchedBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(matchedBet1.getFeeId1().getId().equals(matchedBet.getFeeId1().getId()));
        assertTrue(matchedBet1.getFeeId2().getId().equals(matchedBet.getFeeId2().getId()));
        assertTrue(matchedBet1.getMatchedBetState().getId().equals(
                matchedBet.getMatchedBetState().getId()));
    }

    @Test
    @Order(4)
    void updateMatchedBetByMatchedBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<MatchedBet> request = new HttpEntity<>(updatedMatchedBet, headers);

        String url = hostname + port
                + MatchedBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&matchedBetId=" + matchedBetId;
        URI uri = new URI(url);
        ResponseEntity<MatchedBet[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, MatchedBet[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedMatchedBetByMatchedBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<MatchedBet[]> matchedBetList = this.restTemplate.exchange(
                hostname + port
                        + MatchedBetPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&matchedBetId=" + matchedBetId, HttpMethod.GET, request, MatchedBet[].class);
        assertTrue(matchedBetList.getStatusCode().is2xxSuccessful());
        MatchedBet matchedBet1 = matchedBetList.getBody()[0];
        //assertTrue(matchedBet1.getPendingBet1().getId().equals(pendingBet1.getId()));
        //assertTrue(matchedBet1.getPendingBet2().getId().equals(pendingBet2.getId()));
        assertTrue(matchedBet1.getOdds1().equals(updatedOdds1));
        assertTrue(matchedBet1.getOdds2().equals(updatedOdds2));
        assertTrue(matchedBet1.getWagerAmount1().equals(updatedWagerAmount1));
        assertTrue(matchedBet1.getWagerAmount2().equals(updatedWagerAmount2));
        assertTrue(matchedBet1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(matchedBet1.getFeeId1().getId().equals(matchedBet.getFeeId1().getId()));
        assertTrue(matchedBet1.getFeeId2().getId().equals(matchedBet.getFeeId2().getId()));
        assertTrue(matchedBet1.getMatchedBetState().getId().equals(
                matchedBet.getMatchedBetState().getId()));
        matchedBetId = matchedBet1.getId();
    }

    @Test
    @Order(6)
    void deleteMatchedBetByMatchedBetId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + MatchedBetPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&matchedBetId=" + matchedBetId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        matchedBetStateService.deleteMatchedBetStateByMatchedBetStateName(
                matchedBetStateName);

        pendingBetService.deletePendingBetByPendingBetName(
                pendingBet1.getPendingBetName());

        pendingBetService.deletePendingBetByPendingBetName(
                pendingBet2.getPendingBetName());

        pendingBetStateService.deletePendingBetStateByPendingBetStateName(
                pendingBetState.getPendingBetStateName());

        botService.deleteBotByBotName(bot.getBotName());

        analystService.deleteAnalystByPersonId(
                person.getId());

        specialtyService.deleteBySpecialtyName(
                specialty.getSpecialtyName());

        userService.deleteUserByPersonId(
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

        gameService.deleteGameByGameName(gameName);

        feeService.deleteFeeByFeeName(feeName);

        unitService.deleteUnitByUnitName(unitName);

        charityService.deleteCharityByCharityName(charity.getCharityName());

        sessionService.deleteSession(session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn2);
    }
}
