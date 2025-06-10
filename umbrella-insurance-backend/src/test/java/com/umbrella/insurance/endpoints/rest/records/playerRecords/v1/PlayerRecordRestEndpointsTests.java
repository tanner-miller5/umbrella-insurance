package com.umbrella.insurance.endpoints.rest.records.playerRecords.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.people.players.v1.db.jpa.PlayerService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.records.playerRecords.v1.db.PlayerRecordPrivilege;
import com.umbrella.insurance.core.models.records.v1.db.jpa.RecordService;
import com.umbrella.insurance.core.models.rewards.v1.db.jpa.RewardService;
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

import com.umbrella.insurance.core.models.entities.Record;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerRecordRestEndpointsTests {
    @Autowired
    private PlayerRecordRestEndpoints playerRecordRestEndpoints;
    @Autowired
    private RewardService rewardService;

    @Test
    void contextLoads() throws Exception {
        assertThat(playerRecordRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Long playerRecordId;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static GameType gameType = new GameType();
    private static Season season = new Season();
    private static Record record = new Record();
    private static Player player = new Player();
    private static Person person = new Person();
    private static PlayerRecord playerRecord = new PlayerRecord();
    private static PlayerRecord updatedPlayerRecord = new PlayerRecord();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    static {
        record.setLosses(1l);
        record.setTies(2l);
        record.setWins(3l);
        record.setRecordName("1");

        gameType.setGameTypeName("2");

        season.setEndDate(Date.valueOf("1116-11-11"));
        season.setStartDate(Date.valueOf("2002-10-10"));
        season.setSeasonName("6");

        playerRecord.setPlayer(player);

        playerRecord.getPlayer().setPlayerPosition("11");
        playerRecord.getPlayer().setBirthplace("12");
        playerRecord.getPlayer().setExperience("10");
        playerRecord.getPlayer().setJerseyNumber("00");
        playerRecord.getPlayer().setPlayerStatus("12");
        playerRecord.getPlayer().setDraftInfo("13");
        playerRecord.getPlayer().setHeight("14");
        playerRecord.getPlayer().setCollege("15");
        playerRecord.getPlayer().setWeight("16");

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
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private RecordService recordService;

    @Test
    @Order(1)
    void createPlayerRecord() throws Exception {
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
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<PlayerRecord> request = new HttpEntity<>(playerRecord, headers);

        season = seasonService.saveSeason(season);
        playerRecord.setSeason(season);
        updatedPlayerRecord.setSeason(season);

        person = personService.getPersonBySsn(ssn).get();
        player.setPerson(person);

        gameType = gameTypeService.saveGameType(gameType);
        player.setGameType(gameType);

        player = playerService.savePlayer(player);
        playerRecord.setPlayer(player);
        updatedPlayerRecord.setPlayer(playerRecord.getPlayer());

        record = recordService.saveRecord(record);
        playerRecord.setRecord(record);
        updatedPlayerRecord.setRecord(record);

        String url = hostname + port
                + PlayerRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<PlayerRecord> playerRecord1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, PlayerRecord.class);
        assertTrue(playerRecord1.getBody().getSeason().getId().equals(season.getId()));
        assertTrue(playerRecord1.getBody().getPlayer().getId().equals(player.getId()));
        playerRecordId = playerRecord1.getBody().getId();
        updatedPlayerRecord.setId(playerRecordId);
    }

    @Test
    @Order(2)
    void readPlayerRecordBySeasonIdAndPlayerId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PlayerRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&seasonId=" + season.getId()
                + "&playerId=" + playerRecord.getPlayer().getId();
        ResponseEntity<PlayerRecord[]> playerRecordList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, PlayerRecord[].class);
        assertTrue(playerRecordList.getStatusCode().is2xxSuccessful());
        PlayerRecord playerRecord1 = playerRecordList.getBody()[0];
        assertTrue(playerRecord1.getSeason().getId().equals(season.getId()));
        assertTrue(playerRecord1.getPlayer().getId().equals(player.getId()));
    }

    @Test
    @Order(3)
    void readPlayerRecordByPlayerRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<PlayerRecord[]> playerRecordList = this.restTemplate.exchange(
                hostname + port + PlayerRecordPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&playerRecordId=" + playerRecordId, HttpMethod.GET, request, PlayerRecord[].class);
        assertTrue(playerRecordList.getStatusCode().is2xxSuccessful());
        PlayerRecord playerRecord1 = playerRecordList.getBody()[0];
        assertTrue(playerRecord1.getSeason().getId().equals(season.getId()));
        assertTrue(playerRecord1.getPlayer().getId().equals(playerRecord.getPlayer().getId()));
    }

    @Test
    @Order(4)
    void updatePlayerRecordByPlayerRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(updatedPlayerRecord, headers);

        String url = hostname + port
                + PlayerRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&playerRecordId=" + playerRecordId;
        URI uri = new URI(url);
        ResponseEntity<PlayerRecord[]> playerRecordList = this.restTemplate.exchange(
                uri, HttpMethod.PUT, request, PlayerRecord[].class);
        assertTrue(playerRecordList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedPlayerRecordByPlayerRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<PlayerRecord[]> playerRecordList = this.restTemplate.exchange(
                hostname + port
                        + PlayerRecordPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&playerRecordId=" + playerRecordId, HttpMethod.GET, request, PlayerRecord[].class);
        assertTrue(playerRecordList.getStatusCode().is2xxSuccessful());
        PlayerRecord playerRecord1 = playerRecordList.getBody()[0];
        assertTrue(playerRecord1.getSeason().getId().equals(season.getId()));
        assertTrue(playerRecord1.getPlayer().getId().equals(playerRecord.getPlayer().getId()));
        playerRecordId = playerRecord1.getPlayer().getId();
        updatedPlayerRecord.setId(playerRecordId);
    }

    @Test
    @Order(6)
    void deletePlayerRecordByPlayerRecordId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PlayerRecordPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&playerRecordId=" + playerRecordId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        recordService.deleteRecordByRecordName(record.getRecordName());

        playerService.deleteByPersonId(
                playerRecord.getPlayer().getPerson().getId());

        rewardService.deleteRewardByRewardName(
                record.getRecordName());

        gameTypeService.deleteByGameTypeName(
                gameType.getGameTypeName());

        seasonService.deleteSeasonBySeasonName(
                season.getSeasonName());

        sessionService.deleteSession(
                session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn);
    }
}
