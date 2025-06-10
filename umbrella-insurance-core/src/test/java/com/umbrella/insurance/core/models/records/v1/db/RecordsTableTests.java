package com.umbrella.insurance.core.models.records.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.records.v1.db.jpa.RecordService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.umbrella.insurance.core.models.entities.Record;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecordsTableTests {
    private static String recordName = "1234";
    private static Long wins = 1l;
    private static Long ties = 2l;
    private static Long losses = 3l;
    private static String updatedRecordName = "12345";
    private static Long updatedWins  = 5l;
    private static Long updatedTies = 6l;
    private static Long updatedLosses = 7l;
    private static Record record = new Record();
    private static Record updatedRecord = new Record();
    static {
        record.setRecordName(recordName);
        record.setWins(wins);
        record.setTies(ties);
        record.setLosses(losses);

        updatedRecord.setRecordName(updatedRecordName);
        updatedRecord.setWins(updatedWins);
        updatedRecord.setTies(updatedTies);
        updatedRecord.setLosses(updatedLosses);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private RecordService recordService;

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

    @Test
    @Order(2)
    void insertRecordTest() throws SQLException {
        record = recordService.saveRecord(record);
        updatedRecord.setId(record.getId());
    }
    @Test
    @Order(3)
    void selectRecordByRecordNameTest() throws SQLException {
        Record recordRecord1 = recordService.getRecordByRecordName(recordName).get();
        assertTrue(recordRecord1.getRecordName().equals(recordName));
        assertTrue(recordRecord1.getWins().equals(wins));
        assertTrue(recordRecord1.getLosses().equals(losses));
        assertTrue(recordRecord1.getTies().equals(ties));
    }
    @Test
    @Order(4)
    void updateRecordByRecordNameTest() throws SQLException {
            updatedRecord = recordService.updateRecord(updatedRecord);
    }
    @Test
    @Order(5)
    void selectUpdatedRecordByRecordNameTest() throws SQLException {
        Record recordRecord1 = recordService.getRecordByRecordName(updatedRecordName).get();
        assertTrue(recordRecord1.getRecordName().equals(updatedRecordName));
        assertTrue(recordRecord1.getWins().equals(updatedWins));
        assertTrue(recordRecord1.getLosses().equals(updatedLosses));
        assertTrue(recordRecord1.getTies().equals(updatedTies));
    }
    @Test
    @Order(6)
    void deleteRecordByRecordNameTest() throws SQLException {
        recordService.deleteRecord(updatedRecord.getId());
    }

}
