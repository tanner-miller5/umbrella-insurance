package com.umbrella.insurance.endpoints.rest.users.policies.matchedPolicies.v1;


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
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa.MatchedPolicyStateService;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.MatchedPolicyPrivilege;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.jpa.MatchedPolicyService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa.PendingPolicyStateService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.PendingPolicyTypeEnum;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa.PendingPolicyTypeService;
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
public class MatchedPolicyRestEndpointsTests {
    @Autowired
    private MatchedPolicyRestEndpoints matchedPolicyRestEndpoints;
    @Autowired
    private StreetAddressService streetAddressService;
    @Autowired
    private StateService stateService;
    @Autowired
    private PendingPolicyService pendingPolicyService;

    @Test
    void contextLoads() throws Exception {
        assertThat(matchedPolicyRestEndpoints).isNotNull();
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

    private static Long matchedPolicyId;
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
    private static Timestamp canceledDateTime = Timestamp.valueOf("1112-11-11 11:11:1111");
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
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private MatchedPolicyService matchedPolicyService;

    @Autowired
    private PendingPolicyStateService pendingPolicyStateService;

    @Autowired
    private MatchedPolicyStateService matchedPolicyStateService;

    @Autowired
    private PerilService perilService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private FeeService feeService;

    @Autowired
    private PendingPolicyTypeService pendingPolicyTypeService;

    @Test
    @Order(1)
    void createMatchedPolicy() throws Exception {
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



        matchedPolicyState = matchedPolicyStateService.saveMatchedPolicyState(matchedPolicyState);
        matchedPolicy.setMatchedPolicyState(matchedPolicyState);
        updatedMatchedPolicy.setMatchedPolicyState(matchedPolicyState);

        peril = perilService.getPerilByPerilName(peril.getPerilName()).get();
        pendingInsuredPolicy.setPeril(peril);
        pendingInsurerPolicy.setPeril(peril);

        person = personService.savePerson(person);
        person = personService.getPersonBySsn(person.getSsn()).get();
        user.setPerson(person);

        user = userService.saveUser(user);

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

        headers.set("session", session.getSessionCode());
        HttpEntity<MatchedPolicy> request = new HttpEntity<>(matchedPolicy, headers);

        String url = hostname + port
                + MatchedPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<MatchedPolicy> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, MatchedPolicy.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        MatchedPolicy matchedPolicy1 = response.getBody();
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
        matchedPolicyId = matchedPolicy1.getId();
        updatedMatchedPolicy.setId(matchedPolicyId);
    }

    @Test
    @Order(2)
    void readMatchedPolicyByMatchedPolicyName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + MatchedPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&pendingInsurerPolicyId=" + pendingInsurerPolicy.getId();
        ResponseEntity<MatchedPolicy[]> matchedPolicyList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, MatchedPolicy[].class);
        assertTrue(matchedPolicyList.getStatusCode().is2xxSuccessful());
        MatchedPolicy matchedPolicy1 = matchedPolicyList.getBody()[0];
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
    @Order(3)
    void readMatchedPolicyByMatchedPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<MatchedPolicy[]> matchedPolicyList = this.restTemplate.exchange(
                hostname + port + MatchedPolicyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&matchedPolicyId=" + matchedPolicyId, HttpMethod.GET, request, MatchedPolicy[].class);
        assertTrue(matchedPolicyList.getStatusCode().is2xxSuccessful());
        MatchedPolicy matchedPolicy1 = matchedPolicyList.getBody()[0];
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
    void updateMatchedPolicyByMatchedPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<MatchedPolicy> request = new HttpEntity<>(updatedMatchedPolicy, headers);

        String url = hostname + port
                + MatchedPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&matchedPolicyId=" + matchedPolicyId;
        URI uri = new URI(url);
        ResponseEntity<MatchedPolicy[]> matchedPolicyList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, MatchedPolicy[].class);
        assertTrue(matchedPolicyList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedMatchedPolicyByMatchedPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<MatchedPolicy[]> matchedPolicyList = this.restTemplate.exchange(
                hostname + port
                        + MatchedPolicyPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&matchedPolicyId=" + matchedPolicyId, HttpMethod.GET, request, MatchedPolicy[].class);
        assertTrue(matchedPolicyList.getStatusCode().is2xxSuccessful());
        MatchedPolicy matchedPolicy1 = matchedPolicyList.getBody()[0];
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
        matchedPolicyId = matchedPolicy1.getId();
    }

    @Test
    @Order(6)
    void deleteMatchedPolicyByMatchedPolicyId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + MatchedPolicyPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&matchedPolicyId=" + matchedPolicyId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        pendingPolicyService.deletePendingPolicyByPendingPolicyName(
                pendingInsurerPolicy.getPendingPolicyName());


        pendingPolicyService.deletePendingPolicyByPendingPolicyName(
                pendingInsuredPolicy.getPendingPolicyName());


        //PendingPolicyTypesTable.deletePendingPolicyTypeByPendingPolicyTypeName(
        //        pendingInsuredPolicyType.getPendingPolicyTypeName(), connection);
        //

        //PendingPolicyTypesTable.deletePendingPolicyTypeByPendingPolicyTypeName(
        //        pendingInsurerPolicyType.getPendingPolicyTypeName(), connection);
        //

        matchedPolicyStateService.deleteMatchedPolicyStateByMatchedPolicyStateName(
                matchedPolicyState.getMatchedPolicyStateName());


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
