package com.umbrella.insurance.core.models.periods.periodStatuses.v1.db;

import com.umbrella.insurance.core.models.entities.PeriodStatus;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa.PeriodStatusService;
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
public class PeriodStatusesTableTests {
    private static String periodStatusName = "1234x";
    private static String updatedPeriodStatusName = "12345";
    private static PeriodStatus periodStatus = new PeriodStatus();
    private static PeriodStatus updatedPeriodStatus = new PeriodStatus();
    static {
        periodStatus.setPeriodStatusName(periodStatusName);

        updatedPeriodStatus.setPeriodStatusName(updatedPeriodStatusName);
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
    private PeriodStatusService periodStatusService;

    @Test
    @Order(2)
    void insertPeriodStatusTest() throws SQLException {
        periodStatus = periodStatusService.savePeriodStatus(periodStatus);
        updatedPeriodStatus.setId(periodStatus.getId());
    }
    @Test
    @Order(3)
    void selectPeriodStatusByPeriodStatusNameTest() throws SQLException {
        PeriodStatus periodStatus1 = periodStatusService
                .getPeriodStatus(periodStatusName).get();
        assertTrue(periodStatus1.getPeriodStatusName().equals(periodStatusName));
    }
    @Test
    @Order(4)
    void updatePeriodStatusByPeriodStatusNameTest() throws SQLException {
        updatedPeriodStatus = periodStatusService
                .updatePeriodStatus(updatedPeriodStatus);
    }
    @Test
    @Order(5)
    void selectUpdatedPeriodStatusByPeriodStatusNameTest() throws SQLException {
        PeriodStatus periodStatus1 = periodStatusService
                .getPeriodStatus(updatedPeriodStatusName).get();
        assertTrue(periodStatus1.getPeriodStatusName().equals(updatedPeriodStatusName));
    }
    @Test
    @Order(6)
    void deletePeriodStatusByPeriodStatusNameTest() throws SQLException {
        periodStatusService.deletePeriodStatus(updatedPeriodStatus.getId());
    }


}
