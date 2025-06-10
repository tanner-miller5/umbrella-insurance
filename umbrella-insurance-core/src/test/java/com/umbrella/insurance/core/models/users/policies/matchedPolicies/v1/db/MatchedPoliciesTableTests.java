package com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db;

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
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa.MatchedPolicyStateService;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.jpa.MatchedPolicyService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa.PendingPolicyStateService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.PendingPolicyTypeEnum;
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
public class MatchedPoliciesTableTests {
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-11-10 10:11:22");
    private static Double premium = 1.0;
    private static Double coverageAmount = 2.0;
    private static Date policyStartDate = Date.valueOf("2001-01-11");
    private static Date policyEndDate = Date.valueOf("2011-01-10");
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2001-01-10 10:09:39");
    private static Double updatedPremium = 5.0;
    private static Double updatedCoverageAmount = 6.0;
    private static Date updatedPolicyStartDate = Date.valueOf("2012-01-10");
    private static Date updatedPolicyEndDate = Date.valueOf("2013-01-10");
    private static Double premiumAmount = 2.0;
    private static Date startDate = Date.valueOf("2023-01-02");
    private static Date endDate = Date.valueOf("2020-11-03");
    private static Double impliedProbability = 0.4;
    //true for insured, false for insurer
    private static String perilName = "flood";
    private static String pendingPolicyName = "1234";
    private static String unitName = "1234g";
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
    private static Timestamp canceledDateTime = Timestamp.valueOf("1112-11-11 11:11:1111");
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
    private static PendingPolicy pendingInsuredPolicy = new PendingPolicy();
    private static PendingPolicy pendingInsurerPolicy = new PendingPolicy();
    private static PendingPolicyType pendingInsuredPolicyType = new PendingPolicyType();
    private static PendingPolicyType pendingInsurerPolicyType = new PendingPolicyType();
    private static PendingPolicy originalPendingPolicyType = new PendingPolicy();
    private static AccountBalanceTransaction accountBalanceEscrowTransaction = new AccountBalanceTransaction();
    private static AccountBalanceTransaction accountBalanceCanceledEscrowTransaction = new AccountBalanceTransaction();
    private static MatchedPolicyState matchedPolicyState = new MatchedPolicyState();
    private static MatchedPolicy matchedPolicy = new MatchedPolicy();
    private static MatchedPolicy updatedMatchedPolicy = new MatchedPolicy();
    static {
        pendingInsuredPolicyType.setPendingPolicyTypeName(PendingPolicyTypeEnum.INSURED.toString());
        pendingInsurerPolicyType.setPendingPolicyTypeName(PendingPolicyTypeEnum.INSURER.toString());

        matchedPolicyState.setMatchedPolicyStateName("23");

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

        pendingInsurerPolicy.setPremiumAmount(Double.valueOf(20.0));
        pendingInsurerPolicy.setStartDate(startDate);
        pendingInsurerPolicy.setEndDate(endDate);
        pendingInsurerPolicy.setCoverageAmount(Double.valueOf(30.0));
        pendingInsurerPolicy.setImpliedProbability(impliedProbability);
        pendingInsurerPolicy.setPendingPolicyName("pendingInsurerPolicyName");
        pendingInsurerPolicy.setCreatedDateTime(createdDateTime);
        pendingInsurerPolicy.setCanceledDateTime(canceledDateTime);

        pendingInsuredPolicy.setPremiumAmount(Double.valueOf(40.0));
        pendingInsuredPolicy.setStartDate(startDate);
        pendingInsuredPolicy.setEndDate(endDate);
        pendingInsuredPolicy.setCoverageAmount(Double.valueOf(50.0));
        pendingInsuredPolicy.setImpliedProbability(impliedProbability);
        pendingInsuredPolicy.setPendingPolicyName("pendingInsuredPolicyName");
        pendingInsuredPolicy.setCreatedDateTime(createdDateTime);
        pendingInsuredPolicy.setCanceledDateTime(canceledDateTime);


        matchedPolicy.setCreatedDateTime(createdDateTime);
        matchedPolicy.setPremium(premium);
        matchedPolicy.setCoverageAmount(coverageAmount);
        matchedPolicy.setPolicyStartDate(policyStartDate);
        matchedPolicy.setPolicyEndDate(policyEndDate);
        matchedPolicy.setImpliedProbability(impliedProbability);

        updatedMatchedPolicy.setCreatedDateTime(updatedCreatedDateTime);

        updatedMatchedPolicy.setPremium(updatedPremium);
        updatedMatchedPolicy.setCoverageAmount(updatedCoverageAmount);
        updatedMatchedPolicy.setPolicyStartDate(updatedPolicyStartDate);
        updatedMatchedPolicy.setPolicyEndDate(updatedPolicyEndDate);
        updatedMatchedPolicy.setImpliedProbability(impliedProbability);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private PersonService personService;
    @Autowired
    private PendingPolicyStateService pendingPolicyStateService;
    @Autowired
    private FeeService feeService;
    @Autowired
    private MatchedPolicyService matchedPolicyService;

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
    private MatchedPolicyStateService matchedPolicyStateService;

    @Autowired
    private PerilService perilService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SessionService sessionService;

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
    private UnitService unitService;

    @Autowired
    private PendingPolicyService pendingPolicyService;

    @Autowired
    private PendingPolicyTypeService pendingPolicyTypeService;

    @Test
    @Order(2)
    void insertMatchedPolicyTest() throws SQLException {
        matchedPolicyState = matchedPolicyStateService.saveMatchedPolicyState(matchedPolicyState);
        matchedPolicy.setMatchedPolicyState(matchedPolicyState);
        updatedMatchedPolicy.setMatchedPolicyState(matchedPolicyState);


        //count = PerilsTable.insertPeril(peril, connection);
        //assertTrue(count == 1);
        peril = perilService.getPerilByPerilName(peril.getPerilName()).get();
        pendingInsuredPolicy.setPeril(peril);
        pendingInsurerPolicy.setPeril(peril);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        session.setUser(user);

        device = deviceService.saveDevice(device);
        session.setDevice(device);

        session = sessionService.saveSession(session);
        pendingInsuredPolicy.setSession(session);
        pendingInsurerPolicy.setSession(session);

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
        pendingInsuredPolicy.setLocation(location);
        pendingInsurerPolicy.setLocation(location);

        unit = unitService.saveUnit(unit);
        pendingInsuredPolicy.setUnit(unit);
        pendingInsurerPolicy.setUnit(unit);
        fee.setUnit(unit);

        pendingPolicyState = pendingPolicyStateService.savePendingPolicyState(pendingPolicyState);
        pendingInsuredPolicy.setPendingPolicyState(pendingPolicyState);
        pendingInsurerPolicy.setPendingPolicyState(pendingPolicyState);

        fee = feeService.saveFee(fee);
        pendingInsuredPolicy.setFee(fee);
        pendingInsurerPolicy.setFee(fee);
        matchedPolicy.setInsurerFee(fee);
        matchedPolicy.setInsuredFee(fee);
        updatedMatchedPolicy.setInsurerFee(fee);
        updatedMatchedPolicy.setInsuredFee(fee);

        //count = PendingPolicyTypesTable.insertPendingPolicyType(pendingInsuredPolicyType, connection);
        //assertTrue(count == 1);
        pendingInsuredPolicyType = pendingPolicyTypeService
                .getPendingPolicyTypeByPendingPolicyTypeName(
                        pendingInsuredPolicyType.getPendingPolicyTypeName()).get();
        pendingInsuredPolicy.setPendingPolicyType(pendingInsuredPolicyType);
        pendingInsuredPolicy.setPendingPolicyType(pendingInsuredPolicyType);

        //count = PendingPolicyTypesTable.insertPendingPolicyType(pendingInsurerPolicyType, connection);
        //assertTrue(count == 1);
        pendingInsurerPolicyType = pendingPolicyTypeService
                .getPendingPolicyTypeByPendingPolicyTypeName(
                        pendingInsurerPolicyType.getPendingPolicyTypeName()).get();
        pendingInsurerPolicy.setPendingPolicyType(pendingInsurerPolicyType);
        pendingInsurerPolicy.setPendingPolicyType(pendingInsurerPolicyType);

        pendingInsuredPolicy = pendingPolicyService.savePendingPolicy(pendingInsuredPolicy);
        matchedPolicy.setPendingInsuredPolicy(pendingInsuredPolicy);
        updatedMatchedPolicy.setPendingInsuredPolicy(pendingInsuredPolicy);

        pendingInsurerPolicy = pendingPolicyService.savePendingPolicy(pendingInsurerPolicy);
        matchedPolicy.setPendingInsurerPolicy(pendingInsurerPolicy);
        updatedMatchedPolicy.setPendingInsurerPolicy(pendingInsurerPolicy);

        matchedPolicy = matchedPolicyService.saveMatchedPolicy(matchedPolicy);
        updatedMatchedPolicy.setId(matchedPolicy.getId());
    }
    @Test
    @Order(3)
    void selectMatchedPolicyByPendingInsurerPolicyIdTest() throws SQLException {
        MatchedPolicy matchedPolicy1 =
                matchedPolicyService.getMatchedPolicyByPendingInsurerPolicyId(
                        pendingInsurerPolicy.getId()).get();
        assertTrue(matchedPolicy1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(matchedPolicy1.getPendingInsuredPolicy().getId().equals(
                pendingInsuredPolicy.getId()));
        assertTrue(matchedPolicy1.getPendingInsurerPolicy().getId().equals(
                pendingInsurerPolicy.getId()));
        assertTrue(matchedPolicy1.getPremium().equals(premium));
        assertTrue(matchedPolicy1.getCoverageAmount().equals(coverageAmount));
        assertTrue(matchedPolicy1.getMatchedPolicyState().getId().equals(matchedPolicyState.getId()));
        assertTrue(matchedPolicy1.getPolicyStartDate().equals(policyStartDate));
        assertTrue(matchedPolicy1.getPolicyEndDate().equals(policyEndDate));
    }
    @Test
    @Order(4)
    void updateMatchedPolicyByPendingInsurerPolicyIdTest() throws SQLException {
        updatedMatchedPolicy = matchedPolicyService.updateMatchedPolicy(
                updatedMatchedPolicy);
    }
    @Test
    @Order(5)
    void selectUpdatedMatchedPolicyByPendingInsurerPolicyIdTest() throws SQLException {
        MatchedPolicy matchedPolicy1 =
                matchedPolicyService.getMatchedPolicyByPendingInsurerPolicyId(
                        pendingInsurerPolicy.getId()).get();
        assertTrue(matchedPolicy1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(matchedPolicy1.getPendingInsuredPolicy().getId().equals(
                pendingInsuredPolicy.getId()));
        assertTrue(matchedPolicy1.getPendingInsurerPolicy().getId().equals(
                pendingInsurerPolicy.getId()));
        assertTrue(matchedPolicy1.getPremium().equals(updatedPremium));
        assertTrue(matchedPolicy1.getCoverageAmount().equals(updatedCoverageAmount));
        assertTrue(matchedPolicy1.getMatchedPolicyState().getId().equals(
                matchedPolicyState.getId()));
        assertTrue(matchedPolicy1.getPolicyStartDate().equals(updatedPolicyStartDate));
        assertTrue(matchedPolicy1.getPolicyEndDate().equals(updatedPolicyEndDate));
    }
    @Test
    @Order(6)
    void deleteMatchedPolicyByPendingInsurerPolicyIdTest() throws SQLException {
        matchedPolicyService.deleteMatchedPolicy(
                matchedPolicy.getId());

        pendingPolicyService.deletePendingPolicy(pendingInsuredPolicy.getId());

        pendingPolicyService.deletePendingPolicy(pendingInsurerPolicy.getId());

        //count = PendingPolicyTypesTable.deletePendingPolicyTypeByPendingPolicyTypeName(
        //        pendingInsuredPolicyType.getPendingPolicyTypeName(), connection);
        //assertTrue(count == 1);

        //count = PendingPolicyTypesTable.deletePendingPolicyTypeByPendingPolicyTypeName(
        //        pendingInsurerPolicyType.getPendingPolicyTypeName(), connection);
        //assertTrue(count == 1);

        matchedPolicyStateService.deleteMatchedPolicyState(
                matchedPolicyState.getId());

        sessionService.deleteSession(session.getId());

        deviceService.deleteDevice(device.getId());

        userService.deleteUser(user.getId());

        personService.deletePerson(person.getId());

        pendingPolicyStateService.deletePendingPolicyState(pendingPolicyState.getId());

        feeService.deleteFee(fee.getId());

        unitService.deleteUnit(unit.getId());

        streetAddressService.deleteStreetAddress(streetAddress.getId());

        cityService.deleteCity(city.getId());

        stateService.deleteState(state.getId());

        zipCodeService.deleteZipCode(zipCode.getId());

        countryService.deleteCountry(country.getId());

        locationService.deleteLocation(location.getId());

        //count = PerilsTable.deletePerilByPerilName(
        //        peril.getPerilName(), connection);
        //assertTrue(count == 1);
    }

}
