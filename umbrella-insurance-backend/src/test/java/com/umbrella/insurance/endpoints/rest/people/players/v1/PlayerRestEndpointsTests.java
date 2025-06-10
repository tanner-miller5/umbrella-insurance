package com.umbrella.insurance.endpoints.rest.people.players.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.people.players.v1.db.PlayerPrivilege;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
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
public class PlayerRestEndpointsTests {
    @Autowired
    private PlayerRestEndpoints playerRestEndpoints;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private GameTypeService gameTypeService;
    @Autowired
    private PersonService personService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private ApplicationRoleService applicationRoleService;
    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws Exception {
        assertThat(playerRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";
    @Autowired
    private TestRestTemplate restTemplate;

    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static String password = "5";
    private static String ssn = "123";
    private static String surname = "122";
    private static String middle = "middle";
    private static String first = "first";
    private static Date dateOfBirth = Date.valueOf("1111-11-11");

    private static String weight = "1";
    private static String college = "2";
    private static String height = "3";
    private static String draftInfo = "4";
    private static String playerStatus = "5";
    private static String experience = "6";
    private static String birthPlace = "7";
    private static String playerPosition = "8";
    private static String jerseyNumber = "9";
    private static String updatedWeight = " 10";
    private static String updatedCollege = "11";
    private static String updatedHeight = "12";
    private static String updatedDraftInfo = "13";
    private static String updatedPlayerStatus = "14";
    private static String updatedExperience = "15";
    private static String updatedBirthPlace = "16";
    private static String updatedPlayerPosition = "17";
    private static String updatedJerseyNumber = "18";
    private static String gameTypeName = "19";
    private static Long playerId;
    private static GameType gameType = new GameType();
    private static Person person = new Person();
    private static Player player = new Player();
    private static Player updatedPlayer = new Player();
    private static CreateUserRequest createUserRequest = new CreateUserRequest();
    private static Session session = new Session();
    static {
        gameType.setGameTypeName(gameTypeName);

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

        player.setWeight(weight);
        player.setCollege(college);
        player.setHeight(height);
        player.setDraftInfo(draftInfo);
        player.setPlayerStatus(playerStatus);
        player.setExperience(experience);
        player.setBirthplace(birthPlace);
        player.setPlayerPosition(playerPosition);
        player.setJerseyNumber(jerseyNumber);

        updatedPlayer.setWeight(updatedWeight);
        updatedPlayer.setCollege(updatedCollege);
        updatedPlayer.setHeight(updatedHeight);
        updatedPlayer.setDraftInfo(updatedDraftInfo);
        updatedPlayer.setPlayerStatus(updatedPlayerStatus);
        updatedPlayer.setExperience(updatedExperience);
        updatedPlayer.setBirthplace(updatedBirthPlace);
        updatedPlayer.setPlayerPosition(updatedPlayerPosition);
        updatedPlayer.setJerseyNumber(updatedJerseyNumber);
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
    void createPlayer() throws Exception {
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
        User user = userService.getUserByUsername(username).get();
        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer_support.toString()).get();
        UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
        userApplicationRoleRelationship.setApplicationRole(applicationRole);
        userApplicationRoleRelationship.setUser(user);
        userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                userApplicationRoleRelationship);

        headers.set("session", session.getSessionCode());
        HttpEntity<Player> request = new HttpEntity<>(player, headers);

        gameType = gameTypeService.saveGameType(gameType);
        player.setGameType(gameType);
        updatedPlayer.setGameType(gameType);

        person = personService.getPersonBySsn(
                ssn).get();
        player.setPerson(person);
        updatedPlayer.setPerson(person);

        String url = hostname + port
                + PlayerPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString();
        ResponseEntity<Player> player1 = this.restTemplate.exchange(
                url, HttpMethod.POST, request, Player.class);
        assertTrue(player1.getStatusCode().is2xxSuccessful());
        assertTrue(player1.getBody().getPerson().getId().equals(player.getPerson().getId()));
        assertTrue(player1.getBody().getPlayerPosition().equals(player.getPlayerPosition()));
        assertTrue(player1.getBody().getBirthplace().equals(player.getBirthplace()));
        assertTrue(player1.getBody().getExperience().equals(player.getExperience()));
        assertTrue(player1.getBody().getJerseyNumber().equals(player.getJerseyNumber()));
        assertTrue(player1.getBody().getCollege().equals(player.getCollege()));
        assertTrue(player1.getBody().getWeight().equals(player.getWeight()));
        assertTrue(player1.getBody().getHeight().equals(player.getHeight()));
        assertTrue(player1.getBody().getGameType().getId().equals(gameType.getId()));
        assertTrue(player1.getBody().getPlayerStatus().equals(player.getPlayerStatus()));
        assertTrue(player1.getBody().getDraftInfo().equals(player.getDraftInfo()));
        playerId = player1.getBody().getId();
        updatedPlayer.setId(playerId);
    }

    @Test
    @Order(2)
    void readPlayerByPersonId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PlayerPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&personId=" + player.getPerson().getId();
        ResponseEntity<Player[]> playerList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Player[].class);
        assertTrue(playerList.getStatusCode().is2xxSuccessful());
        Player player1 = playerList.getBody()[0];
        assertTrue(player1.getPerson().getId().equals(player.getPerson().getId()));
        assertTrue(player1.getPlayerPosition().equals(player.getPlayerPosition()));
        assertTrue(player1.getBirthplace().equals(player.getBirthplace()));
        assertTrue(player1.getExperience().equals(player.getExperience()));
        assertTrue(player1.getJerseyNumber().equals(player.getJerseyNumber()));
        assertTrue(player1.getCollege().equals(player.getCollege()));
        assertTrue(player1.getWeight().equals(player.getWeight()));
        assertTrue(player1.getHeight().equals(player.getHeight()));
        assertTrue(player1.getGameType().getId().equals(gameType.getId()));
        assertTrue(player1.getPlayerStatus().equals(player.getPlayerStatus()));
        assertTrue(player1.getDraftInfo().equals(player.getDraftInfo()));
    }

    @Test
    @Order(3)
    void readPlayerByPlayerId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Player[]> playerList = this.restTemplate.exchange(
                hostname + port + PlayerPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&playerId=" + playerId, HttpMethod.GET, request, Player[].class);
        Player player1 = playerList.getBody()[0];
        assertTrue(player1.getPerson().getId().equals(player.getPerson().getId()));
        assertTrue(player1.getPlayerPosition().equals(player.getPlayerPosition()));
        assertTrue(player1.getBirthplace().equals(player.getBirthplace()));
        assertTrue(player1.getExperience().equals(player.getExperience()));
        assertTrue(player1.getJerseyNumber().equals(player.getJerseyNumber()));
        assertTrue(player1.getCollege().equals(player.getCollege()));
        assertTrue(player1.getWeight().equals(player.getWeight()));
        assertTrue(player1.getHeight().equals(player.getHeight()));
        assertTrue(player1.getGameType().getId().equals(gameType.getId()));
        assertTrue(player1.getPlayerStatus().equals(player.getPlayerStatus()));
        assertTrue(player1.getDraftInfo().equals(player.getDraftInfo()));
    }

    @Test
    @Order(4)
    void updatePlayerByPlayerId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Player> request = new HttpEntity<>(updatedPlayer, headers);

        String url = hostname + port
                + PlayerPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&playerId=" + playerId;
        URI uri = new URI(url);
        ResponseEntity<Player[]> playerList = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Player[].class);
        assertTrue(playerList.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(5)
    void readUpdatedPlayerByPlayerId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<Player[]> playerList = this.restTemplate.exchange(
                hostname + port
                        + PlayerPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&playerId=" + playerId, HttpMethod.GET, request, Player[].class);
        assertTrue(playerList.getStatusCode().is2xxSuccessful());
        Player player1 = playerList.getBody()[0];
        assertTrue(player1.getPerson().getId().equals(updatedPlayer.getPerson().getId()));
        assertTrue(player1.getPlayerPosition().equals(updatedPlayer.getPlayerPosition()));
        assertTrue(player1.getBirthplace().equals(updatedPlayer.getBirthplace()));
        assertTrue(player1.getExperience().equals(updatedPlayer.getExperience()));
        assertTrue(player1.getJerseyNumber().equals(updatedPlayer.getJerseyNumber()));
        assertTrue(player1.getCollege().equals(updatedPlayer.getCollege()));
        assertTrue(player1.getWeight().equals(updatedPlayer.getWeight()));
        assertTrue(player1.getHeight().equals(updatedPlayer.getHeight()));
        assertTrue(player1.getGameType().getId().equals(updatedPlayer.getGameType().getId()));
        assertTrue(player1.getPlayerStatus().equals(updatedPlayer.getPlayerStatus()));
        assertTrue(player1.getDraftInfo().equals(updatedPlayer.getDraftInfo()));
        playerId = player1.getId();
    }

    @Test
    @Order(6)
    void deletePlayerByPlayerId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15");
        headers.set("session", session.getSessionCode());
        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        String url = hostname + port
                + PlayerPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&playerId=" + playerId;
        URI uri = new URI(url);
        ResponseEntity<Object> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        sessionService.deleteSession(session.getId());

        passwordService.deletePasswordByUserId(session.getUser().getId());

        ApplicationRole applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                ApplicationRoleEnum.customer.name()).get();

        userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                session.getUser().getId(), applicationRole.getId());

        userService.deleteUser(session.getUser().getId());

        personService.deletePersonBySsn(ssn);

        gameTypeService.deleteByGameTypeName(gameTypeName);
    }
}
