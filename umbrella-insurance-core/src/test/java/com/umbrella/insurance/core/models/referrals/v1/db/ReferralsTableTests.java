package com.umbrella.insurance.core.models.referrals.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Referral;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.referrals.v1.db.jpa.ReferralService;
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
public class ReferralsTableTests {
    private static String referralName = "1234";
    private static String updatedReferralName = "12345";
    private static Referral referral = new Referral();
    private static Referral updatedReferral = new Referral();
    static {
        referral.setReferralName(referralName);

        updatedReferral.setReferralName(updatedReferralName);

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
    private ReferralService referralService;


    @Test
    @Order(2)
    void insertReferralTest() throws SQLException {
        referral = referralService.saveReferral(referral);
        updatedReferral.setId(referral.getId());
    }
    @Test
    @Order(3)
    void selectReferralByReferralNameTest() throws SQLException {
        Referral referral1 = referralService.getReferralByReferralName(
                referralName).get();
        assertTrue(referral1.getReferralName().equals(referralName));
    }
    @Test
    @Order(4)
    void updateReferralByReferralNameTest() throws SQLException {
        updatedReferral = referralService.updateReferral(
                updatedReferral);
    }
    @Test
    @Order(5)
    void selectUpdatedReferralByReferralNameTest() throws SQLException {
        Referral referral1 = referralService
                .getReferralByReferralName(updatedReferralName).get();
        assertTrue(referral1.getReferralName().equals(updatedReferralName));
    }
    @Test
    @Order(6)
    void deleteReferralByReferralNameTest() throws SQLException {
        referralService.deleteReferral(updatedReferral.getId());
    }

}
