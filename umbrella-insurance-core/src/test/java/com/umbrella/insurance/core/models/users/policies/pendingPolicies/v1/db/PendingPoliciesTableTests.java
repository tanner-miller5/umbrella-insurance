package com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.perils.v1.db.jpa.PerilService;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.users.fees.v1.db.jpa.FeeService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa.PendingPolicyStateService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa.PendingPolicyTypeService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa.PendingPolicyService;
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
public class PendingPoliciesTableTests {
    private static Double maximumPremiumAmount = 2.0;
    private static Date startDate = Date.valueOf("2023-01-02");
    private static Date endDate = Date.valueOf("2020-11-03");
    private static Double minimumCoverageAmount = 3.0;
    private static Double impliedProbability = 0.4;
    //true for insured, false for insured
    private static String perilName = "flood";
    private static String pendingInsuredPolicyName = "1234";
    private static Timestamp createdDateTime = Timestamp.valueOf("1111-11-11 11:11:1111");
    private static Timestamp canceledDateTime = Timestamp.valueOf("1112-11-11 11:11:1111");

    private static Double updatedMaximumPremiumAmount = 5.0;
    private static Date updatedStartDate = Date.valueOf("2020-11-12");
    private static Date updatedEndDate = Date.valueOf("2019-04-21");
    private static Double updatedMinimumCoverageAmount = 10.0;
    private static Double updatedImpliedProbability = 10.5;
    //true for insured, false for insured
    private static String updatedPendingInsuredPolicyName = "12345";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1113-11-11 11:11:1111");
    private static Timestamp updatedCanceledDateTime = Timestamp.valueOf("1114-11-11 11:11:1111");
    private static String unitName = "1234j";
    private static String locationName = "1234";
    private static String pendingPolicyStateName = "1";
    private static String feeName = "1234";
    private static Double feePercent = 2.0;
    private static Double fixedFee = 3.0;
    private static String sessionCode = "11";
    private static Timestamp startDateTime = Timestamp.valueOf("2022-11-11 11:11:11");
    private static Timestamp endDateTime = Timestamp.valueOf("2020-11-11 11:11:11");
    private static Long minutesToExpire = 2l;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String deviceName = "1234";
    private static String ipAddress = "2";
    private static String userAgent = "3";
    private static Person person = new Person();
    private static User user = new User();
    private static Device device = new Device();
    private static Session session = new Session();
    private static Fee fee = new Fee();
    private static PendingPolicyState pendingPolicyState = new PendingPolicyState();
    private static StreetAddress streetAddress = new StreetAddress();
    private static City city = new City();
    private static State state = new State();
    private static ZipCode zipCode = new ZipCode();
    private static Country country = new Country();
    private static Location location = new Location();
    private static Unit unit = new Unit();
    private static Peril peril = new Peril();
    private static PendingPolicy splitPendingPolicy1 = new PendingPolicy();
    private static PendingPolicy splitPendingPolicy2 = new PendingPolicy();
    private static PendingPolicy originalPendingPolicy = new PendingPolicy();
    private static AccountBalanceTransaction accountBalanceEscrowTransaction = new AccountBalanceTransaction();
    private static AccountBalanceTransaction accountBalanceCanceledEscrowTransaction = new AccountBalanceTransaction();
    private static PendingPolicyType pendingPolicyType = new PendingPolicyType();
    private static PendingPolicy pendingPolicy = new PendingPolicy();
    private static PendingPolicy updatedPendingPolicy = new PendingPolicy();
    static {
        peril.setPerilName(perilName);

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

        pendingPolicyState.setPendingPolicyStateName(pendingPolicyStateName);

        streetAddress.setStreetAddressLine1("1");
        streetAddress.setStreetAddressLine2("2");
        city.setCityName("3");
        state.setStateName("4");
        zipCode.setZipCodeValue("5");
        country.setCountryName("6");

        location.setLocationName(locationName);

        unit.setUnitName(unitName);

        pendingPolicy.setPremiumAmount(maximumPremiumAmount);
        pendingPolicy.setStartDate(startDate);
        pendingPolicy.setEndDate(endDate);
        pendingPolicy.setCoverageAmount(minimumCoverageAmount);
        pendingPolicy.setImpliedProbability(impliedProbability);
        pendingPolicy.setPendingPolicyName(pendingInsuredPolicyName);
        pendingPolicy.setCreatedDateTime(createdDateTime);
        pendingPolicy.setCanceledDateTime(canceledDateTime);

        updatedPendingPolicy.setPremiumAmount(updatedMaximumPremiumAmount);
        updatedPendingPolicy.setStartDate(updatedStartDate);
        updatedPendingPolicy.setEndDate(updatedEndDate);
        updatedPendingPolicy.setCoverageAmount(updatedMinimumCoverageAmount);
        updatedPendingPolicy.setImpliedProbability(updatedImpliedProbability);
        updatedPendingPolicy.setPendingPolicyName(updatedPendingInsuredPolicyName);
        updatedPendingPolicy.setCreatedDateTime(updatedCreatedDateTime);
        updatedPendingPolicy.setCanceledDateTime(updatedCanceledDateTime);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private PerilService perilService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private PendingPolicyStateService pendingPolicyStateService;

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
    private PersonService personService;

    @Autowired
    private PendingPolicyTypeService pendingPolicyTypeService;

    @Autowired
    private PendingPolicyService pendingPolicyService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

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
    private FeeService feeService;

    @Test
    @Order(2)
    void insertPendingPolicyTest() throws SQLException {
        //int count = PerilsTable.insertPeril(peril, connection);
        //assertTrue(count == 1);
        peril = perilService.getPerilByPerilName(peril.getPerilName()).get();
        pendingPolicy.setPeril(peril);
        updatedPendingPolicy.setPeril(peril);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        pendingPolicy.setSession(session);
        updatedPendingPolicy.setSession(session);

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
        pendingPolicy.setLocation(location);
        updatedPendingPolicy.setLocation(location);

        unit = unitService.saveUnit(unit);
        pendingPolicy.setUnit(unit);
        updatedPendingPolicy.setUnit(unit);
        fee.setUnit(unit);

        pendingPolicyState = pendingPolicyStateService.savePendingPolicyState(pendingPolicyState);
        pendingPolicy.setPendingPolicyState(pendingPolicyState);
        updatedPendingPolicy.setPendingPolicyState(pendingPolicyState);

        fee = feeService.saveFee(fee);
        pendingPolicy.setFee(fee);
        updatedPendingPolicy.setFee(fee);

        pendingPolicy = pendingPolicyService.savePendingPolicy(pendingPolicy);
        updatedPendingPolicy.setId(pendingPolicy.getId());
    }
    @Test
    @Order(3)
    void selectPendingPolicyByPendingPolicyNameTest() throws SQLException {
        PendingPolicy pendingPolicy1 =
                pendingPolicyService.getPendingPolicyByPendingPolicyName(
                pendingInsuredPolicyName).get();
        assertTrue(pendingPolicy1.getPremiumAmount().equals(maximumPremiumAmount));
        assertTrue(pendingPolicy1.getStartDate().equals(startDate));
        assertTrue(pendingPolicy1.getEndDate().equals(endDate));
        assertTrue(pendingPolicy1.getCoverageAmount().equals(minimumCoverageAmount));
        assertTrue(pendingPolicy1.getImpliedProbability().equals(impliedProbability));
        assertTrue(pendingPolicy1.getPeril().getId().equals(peril.getId()));
        assertTrue(pendingPolicy1.getLocation().getId().equals(location.getId()));
        assertTrue(pendingPolicy1.getUnit().getId().equals(unit.getId()));
        assertTrue(pendingPolicy1.getPendingPolicyState().getId().equals(
                pendingPolicyState.getId()));
        assertTrue(pendingPolicy1.getFee().getId().equals(fee.getId()));
        assertTrue(pendingPolicy1.getPendingPolicyName().equals(pendingInsuredPolicyName));
        //assertTrue(pendingPolicy1.getSplitPendingPolicy1().getId() ==
        //        splitPendingPolicy1.getId());
        //assertTrue(pendingPolicy1.getSplitPendingPolicy2().getId() ==
        //        splitPendingPolicy2.getId());
        //assertTrue(pendingPolicy1.getOriginalPendingPolicy().getId() ==
        //        originalPendingPolicy.getId());
        //assertTrue(pendingPolicy1.getAccountBalanceEscrowTransaction().getId() ==
        //        accountBalanceEscrowTransaction.getId());
        //assertTrue(pendingPolicy1.getAccountBalanceCanceledEscrowTransaction().getId() ==
        //        accountBalanceCanceledEscrowTransaction.getId());
        assertTrue(pendingPolicy1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingPolicy1.getCanceledDateTime().equals(canceledDateTime));
        //assertTrue(pendingPolicy1.getPendingPolicyType().getId() ==
        //        pendingPolicyType.getId());
    }
    @Test
    @Order(4)
    void updatePendingPolicyByPendingPolicyNameTest() throws SQLException {
        updatedPendingPolicy = pendingPolicyService.updatePendingPolicy(
                updatedPendingPolicy);
    }
    @Test
    @Order(5)
    void selectUpdatedPendingPolicyByPendingPolicyNameTest() throws SQLException {
        PendingPolicy pendingPolicy1 = pendingPolicyService
                .getPendingPolicyByPendingPolicyName(
                        updatedPendingInsuredPolicyName).get();
        assertTrue(pendingPolicy1.getPremiumAmount().equals(updatedMaximumPremiumAmount));
        assertTrue(pendingPolicy1.getStartDate().equals(updatedStartDate));
        assertTrue(pendingPolicy1.getEndDate().equals(updatedEndDate));
        assertTrue(pendingPolicy1.getCoverageAmount().equals(updatedMinimumCoverageAmount));
        assertTrue(pendingPolicy1.getImpliedProbability().equals(updatedImpliedProbability));
        assertTrue(pendingPolicy1.getPeril().getId().equals(peril.getId()));
        assertTrue(pendingPolicy1.getLocation().getId().equals(location.getId()));
        assertTrue(pendingPolicy1.getUnit().getId().equals(unit.getId()));
        assertTrue(pendingPolicy1.getPendingPolicyState().getId().equals(
                pendingPolicyState.getId()));
        assertTrue(pendingPolicy1.getFee().getId().equals(fee.getId()));
        assertTrue(pendingPolicy1.getPendingPolicyName().equals(updatedPendingInsuredPolicyName));
        //assertTrue(pendingPolicy1.getSplitPendingPolicy1().getId() ==
        //        splitPendingPolicy1.getId());
        //assertTrue(pendingPolicy1.getSplitPendingPolicy2().getId() ==
        //        splitPendingPolicy2.getId());
        //assertTrue(pendingPolicy1.getOriginalPendingPolicy().getId() ==
        //        originalPendingPolicy.getId());
        //assertTrue(pendingPolicy1.getAccountBalanceEscrowTransaction().getId() ==
        //        accountBalanceEscrowTransaction.getId());
        //assertTrue(pendingPolicy1.getAccountBalanceCanceledEscrowTransaction().getId() ==
        //        accountBalanceCanceledEscrowTransaction.getId());
        assertTrue(pendingPolicy1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(pendingPolicy1.getCanceledDateTime().equals(updatedCanceledDateTime));
        //assertTrue(pendingPolicy1.getPendingPolicyType().getId() ==
        //        pendingPolicyType.getId());
    }
    @Test
    @Order(6)
    void deletePendingPolicyByPendingPolicyNameTest() throws SQLException {
        pendingPolicyService.deletePendingPolicy(
                updatedPendingPolicy.getId());

        sessionService.deleteSession(
                session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());

        pendingPolicyStateService.deletePendingPolicyState(
                pendingPolicyState.getId());

        feeService.deleteFee(fee.getId());

        unitService.deleteUnit(unit.getId());

        streetAddressService.deleteStreetAddress(streetAddress.getId());

        cityService.deleteCity(city.getId());

        stateService.deleteState(state.getId());

        zipCodeService.deleteZipCode(
                zipCode.getId());

        countryService.deleteCountry(
                country.getId());

        locationService.deleteLocation(location.getId());

        //perilService.deletePeril(peril.getId());
    }
}
