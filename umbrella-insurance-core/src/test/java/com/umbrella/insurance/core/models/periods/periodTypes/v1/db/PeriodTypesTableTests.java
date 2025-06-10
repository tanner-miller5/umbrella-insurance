package com.umbrella.insurance.core.models.periods.periodTypes.v1.db;

import com.umbrella.insurance.core.models.entities.PeriodType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa.PeriodTypeService;
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
public class PeriodTypesTableTests {
    private static String periodTypeName = "1234";
    private static String updatedPeriodTypeName = "12345";
    private static PeriodType periodType = new PeriodType();
    private static PeriodType updatedPeriodType = new PeriodType();
    static {
        periodType.setPeriodTypeName(periodTypeName);

        updatedPeriodType.setPeriodTypeName(updatedPeriodTypeName);

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
    private PeriodTypeService periodTypeService;

    @Test
    @Order(2)
    void insertPeriodTypeTest() throws SQLException {
        periodType = periodTypeService.savePeriodType(periodType);
        updatedPeriodType.setId(periodType.getId());
    }
    @Test
    @Order(3)
    void selectPeriodTypeByPeriodTypeNameTest() throws SQLException {
        PeriodType periodType1 = periodTypeService
                .getPeriodTypeByPeriodTypeName(periodTypeName).get();
        assertTrue(periodType1.getPeriodTypeName().equals(periodTypeName));
    }
    @Test
    @Order(4)
    void updatePeriodTypeByPeriodTypeNameTest() throws SQLException {
        updatedPeriodType = periodTypeService
                .updatePeriodType(updatedPeriodType);
    }
    @Test
    @Order(5)
    void selectUpdatedPeriodTypeByPeriodTypeNameTest() throws SQLException {
        PeriodType periodType1 = periodTypeService
                .getPeriodTypeByPeriodTypeName(updatedPeriodTypeName).get();
        assertTrue(periodType1.getPeriodTypeName().equals(updatedPeriodTypeName));
    }
    @Test
    @Order(6)
    void deletePeriodTypeByPeriodTypeNameTest() throws SQLException {
        periodTypeService.deletePeriodType(updatedPeriodType.getId());
    }


}
