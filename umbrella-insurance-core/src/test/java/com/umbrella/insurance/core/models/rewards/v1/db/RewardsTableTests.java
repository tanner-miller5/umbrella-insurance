package com.umbrella.insurance.core.models.rewards.v1.db;

import com.umbrella.insurance.core.models.entities.Reward;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.rewards.v1.db.jpa.RewardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RewardsTableTests {
    private static String rewardName = "1234";
    private static String updatedRewardName = "12345";
    private static Reward reward = new Reward();
    private static Reward updatedReward = new Reward();
    static {
        reward.setRewardName(rewardName);

        updatedReward.setRewardName(updatedRewardName);

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
    public RewardService rewardService;

    @Test
    @Order(2)
    void insertRewardTest() throws SQLException {
        reward = rewardService.saveReward(reward);
        updatedReward.setId(reward.getId());
    }
    @Test
    @Order(3)
    void selectRewardByRewardName() throws SQLException {
        Reward reward1 = rewardService.getRewardByName(rewardName).get();
        assertTrue(reward1.getRewardName().equals(rewardName));
    }
    @Test
    @Order(4)
    void updateRewardByRewardNameTest() throws SQLException {
        rewardService.updateReward(updatedReward);
    }
    @Test
    @Order(5)
    void selectRewardByRewardNameTest() throws SQLException {
        rewardService.getRewardByName(updatedRewardName);
        assertTrue(updatedReward.getRewardName().equals(updatedRewardName));
    }
    @Test
    @Order(6)
    void deleteRewardByRewardNameTest() throws SQLException {
        rewardService.deleteReward(updatedReward.getId());
    }

}
