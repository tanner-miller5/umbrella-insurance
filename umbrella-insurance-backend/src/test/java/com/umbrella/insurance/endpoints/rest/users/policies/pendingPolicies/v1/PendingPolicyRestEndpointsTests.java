package com.umbrella.insurance.endpoints.rest.users.policies.pendingPolicies.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
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
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.fees.v1.db.jpa.FeeService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa.PendingPolicyStateService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.PendingPolicyPrivilege;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa.PendingPolicyService;
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
public class PendingPolicyRestEndpointsTests {
    @Autowired
    private PendingPolicyRestEndpoints pendingPolicyRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(pendingPolicyRestEndpoints).isNotNull();
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

    private static Long pendingPolicyId;
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
    private static String unitName = "1234";
    private static String locationName = "1234";
    private static String pendingPolicyStateName = "1";
    private static String feeName = "1234";
    private static Double feePercent = 2.0;
    private static Double fixedFee = 3.0;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Person person = new Person();
    private static User user = new User();
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
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    static {
        peril.setPerilName(perilName);

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
    private PasswordService passwordService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private UserService userService;

    @Autowired
    private PerilService perilService;

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
    private FeeService feeService;

    @Autowired
    private PendingPolicyStateService pendingPolicyStateService;

    @Test
    @Order(1)
    void createPendingPolicy() throws Exception {
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
        User user2 = userService.getUserByUsername(username2).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship2 = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship2.setApplicationRole(applicationRole);
        userApplicationRoleRelationship2.setUser(user2);
        userApplicationRoleRelationship2 = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship2);

        peril = perilService.getPerilByPerilName(peril.getPerilName()).get();
        pendingPolicy.setPeril(peril);
        updatedPendingPolicy.setPeril(peril);

        person = personService.savePerson(person);
        user.setPerson(person);

        user = userService.saveUser(user);
        user = userService.getUserByPersonId(user.getPerson().getId()).get();

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

        headers.set("session", session.getSessionCode());
        HttpEntity<PendingPolicy> request = new HttpEntity<>(pendingPolicy, headers);

        String url = hostname + port
                + PendingPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<PendingPolicy> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, PendingPolicy.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        PendingPolicy pendingPolicy1 = response.getBody();
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

        assertTrue(pendingPolicy1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingPolicy1.getCanceledDateTime().equals(canceledDateTime));
        pendingPolicyId = pendingPolicy1.getId();
        updatedPendingPolicy.setId(pendingPolicyId);
    }

    @Test
    @Order(2)
    void readPendingPolicyByPendingPolicyName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PendingPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingPolicyName=" + pendingPolicy.getPendingPolicyName();
        ResponseEntity<PendingPolicy[]> pendingPolicyList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, PendingPolicy[].class);
        PendingPolicy pendingPolicy1 = pendingPolicyList.getBody()[0];
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
        assertTrue(pendingPolicy1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingPolicy1.getCanceledDateTime().equals(canceledDateTime));
    }

    @Test
    @Order(3)
    void readPendingPolicyByPendingPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<PendingPolicy[]> pendingPolicyList = this.restTemplate.exchange(
                hostname + port + PendingPolicyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&pendingPolicyId=" + pendingPolicyId, HttpMethod.GET, request, PendingPolicy[].class);
        assertTrue(pendingPolicyList.getStatusCode().is2xxSuccessful());
        PendingPolicy pendingPolicy1 = pendingPolicyList.getBody()[0];
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
        assertTrue(pendingPolicy1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(pendingPolicy1.getCanceledDateTime().equals(canceledDateTime));
    }

    @Test
    @Order(4)
    void updatePendingPolicyByPendingPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<PendingPolicy> request = new HttpEntity<>(updatedPendingPolicy, headers);

        String url = hostname + port
                + PendingPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingPolicyId=" + pendingPolicyId;
        URI uri = new URI(url);
        ResponseEntity<PendingPolicy[]> pendingPolicyList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, PendingPolicy[].class);
        assertTrue(pendingPolicyList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedPendingPolicyByPendingPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<PendingPolicy[]> pendingPolicyList = this.restTemplate.exchange(
                hostname + port
                        + PendingPolicyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&pendingPolicyId=" + pendingPolicyId, HttpMethod.GET, request, PendingPolicy[].class);
        assertTrue(pendingPolicyList.getStatusCode().is2xxSuccessful());
        PendingPolicy pendingPolicy1 = pendingPolicyList.getBody()[0];
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
        assertTrue(pendingPolicy1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(pendingPolicy1.getCanceledDateTime().equals(updatedCanceledDateTime));
        pendingPolicyId = pendingPolicy1.getId();
    }

    @Test
    @Order(6)
    void deletePendingPolicyByPendingPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PendingPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingPolicyId=" + pendingPolicyId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        userService.deleteUserByPersonId(person.getId());


        personService.deletePersonBySsn(person.getSsn());


        pendingPolicyStateService.deletePendingPolicyStateByPendingPolicyStateName(
                pendingPolicyState.getPendingPolicyStateName());


        feeService.deleteFeeByFeeName(fee.getFeeName());


        unitService.deleteUnitByUnitName(unit.getUnitName());


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
