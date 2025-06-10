package com.umbrella.insurance.core.models.units.v1.db;

import com.umbrella.insurance.core.models.entities.Unit;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
public class UnitsTableTests {
    private static String unitName = "1234h";
    private static String updatedUnitName = "12345";
    private static Unit unit = new Unit();
    private static Unit updatedUnit = new Unit();
    static {
        unit.setUnitName(unitName);

        updatedUnit.setUnitName(updatedUnitName);
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
    private UnitService unitService;

    @Test
    @Order(2)
    void insertUnitTest() throws SQLException {
        unit = unitService.saveUnit(unit);
        updatedUnit.setId(unit.getId());
    }
    @Test
    @Order(3)
    void selectUnitByUnitNameTest() throws SQLException {
        Unit unit1 = unitService.getUnitByUnitName(unitName).get();
        assertTrue(unit1.getUnitName().equals(unitName));
    }
    @Test
    @Order(4)
    void updateUnitByUnitNameTest() throws SQLException {
        updatedUnit = unitService.updateUnit(updatedUnit);
    }
    @Test
    @Order(5)
    void selectUpdatedUnitByUnitNameTest() throws SQLException {
        Unit unit1 = unitService.getUnitByUnitName(updatedUnitName).get();
        assertTrue(unit1.getUnitName().equals(updatedUnitName));
    }
    @Test
    @Order(6)
    void deleteUnitByUnitNameTest() throws SQLException {
        unitService.deleteUnit(updatedUnit.getId());
    }
}
