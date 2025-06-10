package com.umbrella.insurance.endpoints.rest.stats.v1;

import com.umbrella.insurance.config.TestConfig;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.entities.Stat;
import com.umbrella.insurance.core.models.stats.v1.StatEnum;
import com.umbrella.insurance.core.models.stats.v1.db.StatPrivilege;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.stats.v1.db.jpa.StatService;
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
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatRestEndpointsTests {

    @Autowired
    private StatRestEndpoints statRestEndpoints;

    @Test
    void contextLoads() throws Exception {
        assertThat(statRestEndpoints).isNotNull();
    }

    @LocalServerPort
    private int port;

    private String hostname = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Long statId;
    private static String statName = "123";
    private static String updatedStatName = "1234";
    private static Stat stat = new Stat();
    private static Stat updatedStat = new Stat();


    static {

        stat.setStatName(statName);
        updatedStat.setStatName(updatedStatName);
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
        //Database.copyOutManagerTest(connection, StatsTable.STATS_TABLE_NAME);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        //StatsTable.deleteAllStats(connection);
        //Database.copyInManagerTest(connection, StatsTable.STATS_TABLE_NAME);
        Database.closeConnection(connection);
    }

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private StatService statService;

    //@Test
    @Order(1)
    void createStat() throws Exception {
        stat = statService.saveStat(stat);
        assertTrue(stat.getStatName().equals(statName));
        statId = stat.getId();
        updatedStat.setId(statId);
    }

    @Test
    @Order(2)
    void readStatByStatName() throws Exception {
        String url = hostname + port
                + StatPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&statName=" + StatEnum.adjustedQbr.name();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Stat> request = new HttpEntity<>(stat, headers);
        ResponseEntity<Stat[]> statList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Stat[].class);
        Stat stat1 = statList.getBody()[0];
        assertTrue(stat1.getStatName().equals(StatEnum.adjustedQbr.name()));
    }

    //@Test
    @Order(3)
    void readStatByStatId() throws Exception {
        String url = hostname + port + StatPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&statId=" + statId;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Stat> request = new HttpEntity<>(stat, headers);
        ResponseEntity<Stat[]> statList = this.restTemplate.exchange(
                url, HttpMethod.GET, request, Stat[].class);
        Stat stat1 = statList.getBody()[0];
        assertTrue(stat1.getStatName().equals(statName));
    }

    //@Test
    @Order(4)
    void updateStatByStatId() throws Exception {
        String url = hostname + port
                + StatPrivilege.PATH
                + "?env=" + TestConfig.testEnv.toString()
                + "&statId=" + statId;
        URI uri = new URI(url);
        this.restTemplate.put(uri, updatedStat);
    }

    //@Test
    @Order(5)
    void readUpdatedStatByStatId() throws Exception {
        Stat[] statList = this.restTemplate.getForObject(
                hostname + port
                        + StatPrivilege.PATH
                        + "?env=" + TestConfig.testEnv.toString()
                        + "&statId=" + statId, Stat[].class);
        Stat stat1 = statList[0];
        assertTrue(stat1.getStatName().equals(updatedStatName));
        statId = stat1.getId();
    }

    //@Test
    @Order(6)
    void deleteStatByStatId() throws Exception {

        statService.deleteStatByStatName(
                stat.getStatName());

    }
}
