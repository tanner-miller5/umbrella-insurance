package com.umbrella.insurance.endpoints.rest.users.bots.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
import com.umbrella.insurance.core.models.people.analysts.v1.db.jpa.AnalystService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.bots.v1.db.BotPrivilege;
import com.umbrella.insurance.core.models.users.bots.v1.db.jpa.BotService;
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
public class BotRestEndpointsTests {
    @Autowired
    private BotRestEndpoints botRestEndpoints;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private BotService botService;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private AnalystService analystService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private PasswordService passwordService;

    @Test
    void contextLoads() throws Exception {
        assertThat(botRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Long botId;

    private static String emailAddress2 = "133";
    private static String phoneNumber2 = "233";
    private static String username2 = "333";
    private static String password2 = "533";
    private static String ssn2 = "12333";
    private static String surname2 = "12233";
    private static String middle2 = "middle33";
    private static String first2 = "first33";
    private static Date dateOfBirth2 = Date.valueOf("1111-11-16");

    private static String botName = "1234";
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-11-11 12:12:12");
    private static Boolean isDeleted = true;
    private static Boolean isDisabled = true;
    private static Timestamp deletedDateTime = Timestamp.valueOf("2022-11-11 12:12:12");
    private static Double amountFunded = 3.0;
    private static String updatedBotName = "12345";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2018-10-11 12:59:12");
    private static Boolean updatedIsDeleted = false;
    private static Boolean updatedIsDisabled = false;
    private static Timestamp updatedDeletedDateTime = Timestamp.valueOf("2012-11-01 12:12:12");
    private static Double updatedAmountFunded = 1000.00;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static String unitName = "1234k";
    private static Unit unit = new Unit();
    private static Specialty specialty = new Specialty();
    private static Person person = new Person();
    private static Analyst analyst = new Analyst();
    private static User user = new User();
    private static Bot bot = new Bot();
    private static Bot updatedBot = new Bot();
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

        unit.setUnitName(unitName);

        specialty.setSpecialtyName("test");

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

        bot.setBotName(botName);
        bot.setCreatedDateTime(createdDateTime);
        bot.setIsDeleted(isDeleted);
        bot.setIsDisabled(isDisabled);
        bot.setDeletedDateTime(deletedDateTime);
        bot.setAmountFunded(amountFunded);

        updatedBot.setBotName(updatedBotName);
        updatedBot.setCreatedDateTime(updatedCreatedDateTime);
        updatedBot.setIsDeleted(updatedIsDeleted);
        updatedBot.setIsDisabled(updatedIsDisabled);
        updatedBot.setDeletedDateTime(updatedDeletedDateTime);
        updatedBot.setAmountFunded(updatedAmountFunded);
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
    void createBot() throws Exception {
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

        person = personService.savePerson(person);
        user.setPerson(person);
        analyst.setPerson(person);

        userService.saveUser(user);
        bot.setUser(user);
        updatedBot.setUser(user);

        specialty = specialtyService.saveSpecialty(specialty);

        analystService.saveAnalyst(analyst);
        bot.setAnalyst(analyst);
        updatedBot.setAnalyst(analyst);

        unit = unitService.saveUnit(unit);
        bot.setUnit(unit);
        updatedBot.setUnit(unit);

        headers.set("session", session.getSessionCode());
        HttpEntity<Bot> request = new HttpEntity<>(bot, headers);

        String url = hostname + port
                + BotPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Bot> response = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Bot.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Bot bot1 = response.getBody();
        assertTrue(bot1.getBotName().equals(botName));
        botId = bot1.getId();
        updatedBot.setId(botId);
    }

    @Test
    @Order(2)
    void readBotByBotName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + BotPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&botName=" + botName;
        ResponseEntity<Bot[]> botList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Bot[].class);
        assertTrue(botList.getStatusCode().is2xxSuccessful());
        Bot bot1 = botList.getBody()[0];
        assertTrue(bot1.getBotName().equals(botName));
    }

    @Test
    @Order(3)
    void readBotByBotId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Bot[]> botList = this.restTemplate.exchange(
                hostname + port + BotPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&botId=" + botId, HttpMethod.GET, request, Bot[].class);
        assertTrue(botList.getStatusCode().is2xxSuccessful());
        Bot bot1 = botList.getBody()[0];
        assertTrue(bot1.getBotName().equals(botName));
    }

    @Test
    @Order(4)
    void updateBotByBotId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Bot> request = new HttpEntity<>(updatedBot, headers);

        String url = hostname + port
                + BotPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&botId=" + botId;
        URI uri = new URI(url);
        ResponseEntity<Bot[]> response = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Bot[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedBotByBotId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Bot[]> botList = this.restTemplate.exchange(
                hostname + port
                        + BotPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&botId=" + botId, HttpMethod.GET, request, Bot[].class);
        assertTrue(botList.getStatusCode().is2xxSuccessful());
        Bot bot1 = botList.getBody()[0];
        assertTrue(bot1.getBotName().equals(updatedBotName));
        botId = bot1.getId();
    }

    @Test
    @Order(6)
    void deleteBotByBotId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + BotPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&botId=" + botId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        userService.deleteUserByPersonId(user.getPerson().getId());

        analystService.deleteAnalystByPersonId(analyst.getPerson().getId());

        personService.deletePersonBySsn(person.getSsn());

        specialtyService.deleteBySpecialtyName(
                specialty.getSpecialtyName());

        unitService.deleteUnitByUnitName(unit.getUnitName());

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
