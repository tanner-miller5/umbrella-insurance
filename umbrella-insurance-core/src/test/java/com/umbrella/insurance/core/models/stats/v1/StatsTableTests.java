package com.umbrella.insurance.core.models.stats.v1;

import com.umbrella.insurance.core.models.entities.Stat;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.stats.v1.db.jpa.StatService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatsTableTests {
    private static String statName = "1234";
    private static String updatedStatName = "12345";
    private static Stat stat = new Stat();
    private static Stat updatedStat = new Stat();
    static {
        stat.setStatName(statName);
        updatedStat.setStatName(updatedStatName);

    }

    private static Connection connection;
    private static Savepoint savepoint;
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
    private StatService statService;


    @Test
    @Order(3)
    void insertStatTest() throws SQLException {
        stat = statService.saveStat(stat);
        updatedStat.setId(stat.getId());
    }
    @Test
    @Order(4)
    void selectStatByStatNameTest() throws SQLException {
        Stat stat1 = statService.getStatByStatName(statName).get();
        assertTrue(stat1.getStatName().equals(statName));

    }
    @Test
    @Order(5)
    void updateStatByStatNameTest() throws SQLException {
        updatedStat = statService.updateStat(updatedStat);
    }
    @Test
    @Order(6)
    void selectUpdatedStatByStatNameTest() throws SQLException {
        Stat stat1 = statService.getStatByStatName(updatedStatName).get();
        assertTrue(stat1.getStatName().equals(updatedStatName));
    }
    @Test
    @Order(7)
    void deleteStatByStatNameTest() throws SQLException {
        statService.deleteStat(updatedStat.getId());
    }
}
