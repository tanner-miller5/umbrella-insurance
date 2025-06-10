package com.umbrella.insurance.core.models.exchangeRates.v1.db;

import com.umbrella.insurance.core.models.entities.ExchangeRate;
import com.umbrella.insurance.core.models.entities.Unit;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exchangeRates.v1.db.jpa.ExchangeRateService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
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
public class ExchangeRatesTableTests {
    private static Double unit1ToUnit2Ratio = 3.3;
    private static Double updatedUnit1ToUnit2Ratio = 2.2;
    private static Unit unit1 = new Unit();
    private static Unit unit2 = new Unit();
    private static ExchangeRate exchangeRate = new ExchangeRate();
    private static ExchangeRate updatedExchangeRate = new ExchangeRate();
    static {
        unit1.setUnitName("USD");
        unit2.setUnitName("Yen");

        exchangeRate.setUnit1ToUnit2Ratio(unit1ToUnit2Ratio);

        updatedExchangeRate.setUnit1ToUnit2Ratio(updatedUnit1ToUnit2Ratio);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private UnitService unitService;

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

    @Test
    @Order(4)
    void insertExchangeRateTest() throws SQLException {
        unit1 = unitService.saveUnit(unit1);
        exchangeRate.setUnit1(unit1);
        updatedExchangeRate.setUnit1(unit1);

        unit2 = unitService.saveUnit(unit2);
        exchangeRate.setUnit2(unit2);
        updatedExchangeRate.setUnit2(unit2);

        exchangeRate = exchangeRateService.saveExchangeRate(
                exchangeRate);
        updatedExchangeRate.setId(exchangeRate.getId());

    }
    @Test
    @Order(5)
    void selectExchangeRateByUnit1IdAndUnit2IdTest() throws SQLException {
        ExchangeRate exchangeRate1 = exchangeRateService.getExchangeRateByUnit1AndUnit2(
                unit1, unit2).get();
        assertTrue(exchangeRate1.getUnit1().getId().equals(unit1.getId()));
        assertTrue(exchangeRate1.getUnit2().getId().equals(unit2.getId()));
        assertTrue(Math.abs(exchangeRate1.getUnit1ToUnit2Ratio()-unit1ToUnit2Ratio) <= 0.000001);
    }
    @Test
    @Order(6)
    void updateExchangeRateByUnit1IdAndUnit2IdTest() throws SQLException {
        updatedExchangeRate = exchangeRateService.updateExchangeRate(
                updatedExchangeRate);
    }
    @Test
    @Order(7)
    void selectUpdatedExchangeRateByUnit1IdAndUnit2IdTest() throws SQLException {
        ExchangeRate exchangeRate1 = exchangeRateService.getExchangeRateByUnit1AndUnit2(
                unit1, unit2).get();
        assertTrue(exchangeRate1.getUnit1().getId().equals(unit1.getId()));
        assertTrue(exchangeRate1.getUnit2().getId().equals(unit2.getId()));
        assertTrue(Math.abs(exchangeRate1.getUnit1ToUnit2Ratio()-updatedUnit1ToUnit2Ratio) <= 0.000001);
    }
    @Test
    @Order(8)
    void deleteExchangeRateByUnit1IdAndUnit2IdTest() throws SQLException {
        exchangeRateService.deleteExchangeRate(
                updatedExchangeRate.getId());

        unitService.deleteUnit(unit1.getId());

        unitService.deleteUnit(unit2.getId());

    }
}
