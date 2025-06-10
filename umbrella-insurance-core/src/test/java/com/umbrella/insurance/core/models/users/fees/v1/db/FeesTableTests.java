package com.umbrella.insurance.core.models.users.fees.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Fee;
import com.umbrella.insurance.core.models.entities.Unit;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.units.v1.UnitEnum;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.users.fees.v1.db.jpa.FeeService;
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
public class FeesTableTests {
    private static String feeName = "1234";
    private static Double feePercent = 2.0;
    private static Double fixedFee = 3.0;
    private static Unit unit = new Unit();
    private static String updatedFeeName = "12345";
    private static Double updatedFeePercent = 4.0;
    private static Double updatedFixedFee = 5.0;
    private static Fee fee = new Fee();
    private static Fee updatedFee = new Fee();
    static {
        unit.setUnitName(UnitEnum.usd.name());

        fee.setFeeName(feeName);
        fee.setFeePercent(feePercent);
        fee.setFixedFee(fixedFee);

        updatedFee.setFeeName(updatedFeeName);
        updatedFee.setFeePercent(updatedFeePercent);
        updatedFee.setFixedFee(updatedFixedFee);

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
    private FeeService feeService;

    @Autowired
    private UnitService unitService;

    @Test
    @Order(2)
    void insertFeeTest() throws SQLException {
        unit = unitService.getUnitByUnitName(unit.getUnitName()).get();
        fee.setUnit(unit);
        updatedFee.setUnit(unit);

        fee = feeService.saveFee(fee);
        updatedFee.setId(fee.getId());
    }
    @Test
    @Order(3)
    void selectFeeByFeeNameTest() throws SQLException {
        Fee fee1 = feeService.getFeeByFeeName(feeName).get();
        assertTrue(fee1.getFeeName().equals(feeName));
        assertTrue(fee1.getFeePercent().equals(feePercent));
        assertTrue(fee1.getFixedFee().equals(fixedFee));
        assertTrue(fee1.getUnit().getId().equals(unit.getId()));
    }
    @Test
    @Order(4)
    void updateFeeByFeeNameTest() throws SQLException {
        updatedFee = feeService.updateFee(updatedFee);
    }
    @Test
    @Order(5)
    void selectUpdatedFeeByFeeNameTest() throws SQLException {
        Fee fee1 = feeService.getFeeByFeeName(updatedFeeName).get();
        assertTrue(fee1.getFeeName().equals(updatedFeeName));
        assertTrue(fee1.getFeePercent().equals(updatedFeePercent));
        assertTrue(fee1.getFixedFee().equals(updatedFixedFee));
        assertTrue(fee1.getUnit().getId().equals(unit.getId()));
    }
    @Test
    @Order(6)
    void deleteFeeByFeeNameTest() throws SQLException {
        feeService.deleteFee(updatedFee.getId());
    }

}
