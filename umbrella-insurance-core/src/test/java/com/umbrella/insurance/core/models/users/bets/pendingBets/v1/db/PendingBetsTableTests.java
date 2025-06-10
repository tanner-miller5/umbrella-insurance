package com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db;

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
public class PendingBetsTableTests {
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
    private static String unitName = "1234i";
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
    private static Device device = new Device();
    private static Session session = new Session();
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
    private static Savepoint savepoint;
    @Autowired
    private FeeService feeService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private GameStatusService gameStatusService;
    @Autowired
    private GameTypeService gameTypeService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private PersonService personService;
    @Autowired
    private AnalystService analystService;

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
    private UnitService unitService;

    @Autowired
    private PendingBetService pendingBetService;

    @Autowired
    private PendingBetStateService pendingBetStateService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CharityService charityService;

    @Autowired
    private BotService botService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private StateService stateService;

    @Autowired
    private SeasonTypeService seasonTypeService;


    @Test
    @Order(2)
    void insertPendingBetTest() throws SQLException {
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
        pendingBet.setGame(game);
        updatedPendingBet.setGame(game);

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
        pendingBet.setSession(session);
        updatedPendingBet.setSession(session);

        bot = botService.saveBot(bot);
        bot.setUnit(unit);
        bot.setAnalyst(analyst);
        bot.setUser(user);
        pendingBet.setBot(bot);
        updatedPendingBet.setBot(bot);

        pendingBet = pendingBetService.savePendingBet(pendingBet);
        updatedPendingBet.setId(pendingBet.getId());
    }
    @Test
    @Order(3)
    void selectPendingBetByPendingBetNameTest() throws SQLException {
        PendingBet pendingBet1 = pendingBetService.getPendingBetByPendingBetName(pendingBetName).get();
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
        //assertTrue(pendingBet1.getOriginalPendingBet().getId() == originalPendingPolicy.getId());
        //assertTrue(pendingBet1.getAccountBalanceEscrowTransaction().getId() == accountBalanceEscrowTransaction.getId());
        //assertTrue(pendingBet1.getAccountBalanceCanceledEscrowTransaction().getId() == accountBalanceCanceledEscrowTransaction.getId());
        assertTrue(pendingBet1.getCanceledDateTime().equals(canceledDateTime));
    }
    @Test
    @Order(4)
    void updatePendingBetByPendingBetNameTest() throws SQLException {
        updatedPendingBet = pendingBetService.updatePendingBet(
                updatedPendingBet);
    }
    @Test
    @Order(5)
    void selectUpdatedPendingBetByPendingBetNameTest() throws SQLException {
        PendingBet pendingBet1 = pendingBetService.getPendingBetByPendingBetName(
                updatedPendingBetName).get();
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
        //assertTrue(pendingBet1.getOriginalPendingBet().getId() == originalPendingPolicy
        //        .getId());
        //assertTrue(pendingBet1.getAccountBalanceEscrowTransaction().getId() ==
        //        accountBalanceEscrowTransaction.getId());
        //assertTrue(pendingBet1.getAccountBalanceCanceledEscrowTransaction().getId() ==
        //        accountBalanceCanceledEscrowTransaction.getId());
        //assertTrue(pendingBet1.getCanceledDateTime().equals(canceledDateTime));
    }
    @Test
    @Order(6)
    void deletePendingBetByPendingBetNameTest() throws SQLException {
        pendingBetService.deletePendingBet(updatedPendingBet.getId());

        pendingBetStateService.deletePendingBetState(
                pendingBetState.getId());

        botService.deleteBot(bot.getId());

        analystService.deleteAnalyst(analyst.getId());

        specialtyService.deleteSpecialty(specialty.getId());

        sessionService.deleteSession(session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());

        streetAddressService.deleteStreetAddress(streetAddress.getId());

        cityService.deleteCity(city.getId());

        stateService.deleteState(state.getId());

        zipCodeService.deleteZipCode(zipCode.getId());

        countryService.deleteCountry(country.getId());

        locationService.deleteLocation(location.getId());

        gameStatusService.deleteGameStatus(gameStatus.getId());

        gameTypeService.deleteGameType(gameType.getId());

        seasonTypeService.deleteSeasonType(seasonType.getId());

        gameService.deleteGame(game.getId());

        feeService.deleteFee(fee.getId());

        unitService.deleteUnit(unit.getId());

        charityService.deleteCharity(charity.getId());
    }

}
