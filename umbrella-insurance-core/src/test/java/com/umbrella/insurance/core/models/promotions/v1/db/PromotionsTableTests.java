package com.umbrella.insurance.core.models.promotions.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Promotion;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.promotions.v1.db.jpa.PromotionService;
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
public class PromotionsTableTests {
    private static String promotionName = "1234";
    private static String updatedPromotionName = "12345";
    private static Promotion promotion = new Promotion();
    private static Promotion updatedPromotion = new Promotion();
    static {
        promotion.setPromotionName(promotionName);

        updatedPromotion.setPromotionName(updatedPromotionName);

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
    private PromotionService promotionService;


    @Test
    @Order(2)
    void insertPromotionTest() throws SQLException {
        promotion = promotionService.savePromotion(promotion);
        updatedPromotion.setId(promotion.getId());
    }
    @Test
    @Order(3)
    void selectPromotionByPromotionNameTest() throws SQLException {
        Promotion promotion1 = promotionService
                .getPromotionByPromotionName(promotionName).get();
        assertTrue(promotion1.getPromotionName().equals(promotionName));
    }
    @Test
    @Order(4)
    void updatePromotionByPromotionNameTest() throws SQLException {
        updatedPromotion = promotionService
                .updatePromotion(updatedPromotion);

    }
    @Test
    @Order(5)
    void selectUpdatedPromotionByPromotionNameTest() throws SQLException {
        Promotion promotion1 = promotionService
                .getPromotionByPromotionName(updatedPromotionName).get();
        assertTrue(promotion1.getPromotionName().equals(updatedPromotionName));
    }
    @Test
    @Order(6)
    void deletePromotionByPromotionNameTest() throws SQLException {
        promotionService.deletePromotion(updatedPromotion.getId());

    }

}
