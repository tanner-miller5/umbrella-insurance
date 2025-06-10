package com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db;

import com.umbrella.insurance.core.models.charities.v1.db.jpa.CharityService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
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
import com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa.MatchedBetStateService;
import com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.jpa.MatchedBetService;
import com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa.PendingBetStateService;
import com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.jpa.PendingBetService;
import com.umbrella.insurance.core.models.users.bots.v1.db.jpa.BotService;
import com.umbrella.insurance.core.models.users.fees.v1.db.jpa.FeeService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MatchedBetsTableTests {
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
    private static Boolean isAuthAppVerified = false;
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
    private static Long minutesToExpire = 2l;
    private static String unitName = "1234f";
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
    private static Device device = new Device();
    private static Session session = new Session();
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
    static {
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
        user.setIsAuthAppVerified(isAuthAppVerified);

        device.setDeviceName(deviceName);
        device.setIpAddress(ipAddress);
        device.setUserAgent(userAgent);
        device.setCreatedDateTime(createdDateTime);

        session.setSessionCode(sessionCode);
        session.setStartDateTime(startDateTime);
        session.setEndDateTime(endDateTime);
        session.setMinutesToExpire(minutesToExpire);

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
    private static Savepoint savepoint;
    @Autowired
    private PendingBetStateService pendingBetStateService;
    @Autowired
    private ZipCodeService zipCodeService;
    @Autowired
    private SeasonTypeService seasonTypeService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private AnalystService analystService;
    @Autowired
    private BotService botService;

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
    private MatchedBetStateService matchedBetStateService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private FeeService feeService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private PendingBetService pendingBetService;

    @Autowired
    private CharityService charityService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private StateService stateService;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameStatusService gameStatusService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private MatchedBetService matchedBetService;

    @Test
    @Order(2)
    void insertMatchedBetTest() throws SQLException {
        matchedBetState = matchedBetStateService.saveMatchedBetState(matchedBetState);
        matchedBet.setMatchedBetState(matchedBetState);
        updatedMatchedBet.setMatchedBetState(matchedBetState);

        unit = unitService.saveUnit(unit);
        fee.setUnit(unit);
        bot.setUnit(unit);
        pendingBet1.setUnit(unit);
        pendingBet2.setUnit(unit);

        pendingBetState = pendingBetStateService.savePendingBetState(pendingBetState);
        pendingBet1.setPendingBetState(pendingBetState);
        pendingBet2.setPendingBetState(pendingBetState);

        fee = feeService.saveFee(fee);
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

        gameType = gameTypeService.saveGameType(gameType);
        game.setGameType(gameType);

        seasonType = seasonTypeService.saveSeasonType(seasonType);
        game.setSeasonType(seasonType);

        game = gameService.saveGame(game);
        pendingBet1.setGame(game);
        pendingBet2.setGame(game);

        specialty = specialtyService.saveSpecialty(
                specialty);
        analyst.setSpecialty(specialty);

        person = personService.savePerson(person);
        analyst.setPerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);
        bot.setUser(user);

        analyst = analystService.saveAnalyst(analyst);
        bot.setAnalyst(analyst);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        pendingBet1.setSession(session);
        pendingBet2.setSession(session);

        bot = botService.saveBot(bot);
        bot.setUnit(unit);
        bot.setAnalyst(analyst);
        bot.setUser(user);
        pendingBet1.setBot(bot);
        pendingBet2.setBot(bot);

        pendingBet1 = pendingBetService.savePendingBet(pendingBet1);
        matchedBet.setPendingBet1(pendingBet1);
        updatedMatchedBet.setPendingBet1(pendingBet1);

        pendingBet2 = pendingBetService.savePendingBet(pendingBet2);
        matchedBet.setPendingBet2(pendingBet2);
        updatedMatchedBet.setPendingBet2(pendingBet2);

        matchedBetService.saveMatchedBet(matchedBet);
        updatedMatchedBet.setId(matchedBet.getId());
    }
    @Test
    @Order(3)
    void selectMatchedBetByPendingBetIdTest() throws SQLException {
        MatchedBet matchedBet1 = matchedBetService.getMatchedBetByPendingBetId1(
                pendingBet1.getId()).get();
        assertTrue(matchedBet1.getPendingBet1().getId().equals(pendingBet1.getId()));
        assertTrue(matchedBet1.getPendingBet2().getId().equals(pendingBet2.getId()));
        assertTrue(matchedBet1.getOdds1().equals(odds1));
        assertTrue(matchedBet1.getOdds2().equals(odds2));
        assertTrue(matchedBet1.getWagerAmount1().equals(wagerAmount1));
        assertTrue(matchedBet1.getWagerAmount2().equals(wagerAmount2));
        assertTrue(matchedBet1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(matchedBet1.getFeeId1().getId().equals(matchedBet.getFeeId1().getId()));
        assertTrue(matchedBet1.getFeeId2().getId().equals(matchedBet.getFeeId2().getId()));
        assertTrue(matchedBet1.getId().equals(
                matchedBet.getId()));
    }
    @Test
    @Order(4)
    void updateMatchedBetByPendingBetIdTest() throws SQLException {
        updatedMatchedBet = matchedBetService.updateMatchedBet(
                updatedMatchedBet);
    }
    @Test
    @Order(5)
    void selectUpdatedMatchedBetByPendingBetIdTest() throws SQLException {
        MatchedBet matchedBet1 = matchedBetService.getMatchedBetByPendingBetId1(
                pendingBet1.getId()).get();
        assertTrue(matchedBet1.getPendingBet1().getId().equals(pendingBet1.getId()));
        assertTrue(matchedBet1.getPendingBet2().getId().equals(pendingBet2.getId()));
        assertTrue(matchedBet1.getOdds1().equals(updatedOdds1));
        assertTrue(matchedBet1.getOdds2().equals(updatedOdds2));
        assertTrue(matchedBet1.getWagerAmount1().equals(updatedWagerAmount1));
        assertTrue(matchedBet1.getWagerAmount2().equals(updatedWagerAmount2));
        assertTrue(matchedBet1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(matchedBet1.getFeeId1().getId().equals(matchedBet.getFeeId1().getId()));
        assertTrue(matchedBet1.getFeeId2().getId().equals(matchedBet.getFeeId2().getId()));
        assertTrue(matchedBet1.getMatchedBetState().getId().equals(
                matchedBet.getMatchedBetState().getId()));
    }
    @Test
    @Order(6)
    void deleteMatchedBetByPendingBetIdTest() throws SQLException {
        matchedBetService.deleteMatchedBet(
                matchedBet.getId());

        matchedBetStateService.deleteMatchedBetState(
                matchedBetState.getId());

        pendingBetService.deletePendingBet(
                pendingBet1.getId());

        pendingBetService.deletePendingBet(
                pendingBet2.getId());

        pendingBetStateService.deletePendingBetState(
                pendingBetState.getId());

        botService.deleteBot(bot.getId());

        analystService.deleteAnalyst(
                analyst.getId());

        specialtyService.deleteSpecialty(
                specialty.getId());

        sessionService.deleteSession(
                session.getId());

        deviceService.deleteDevice(
                device.getId());

        userService.deleteUser(
                user.getId());

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

        feeService.deleteFee(fee.getId());

        unitService.deleteUnit(unit.getId());

        charityService.deleteCharity(charity.getId());
    }

}
