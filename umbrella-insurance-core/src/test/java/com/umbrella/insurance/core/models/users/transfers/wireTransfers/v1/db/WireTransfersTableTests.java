package com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Transfer;
import com.umbrella.insurance.core.models.entities.WireTransfer;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.transfers.v1.db.jpa.TransferService;
import com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db.jpa.WireTransferService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WireTransfersTableTests {
    private static Long transferId;
    private static Double amount = 20.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("2023-10-12 11:11:11");
    private static Boolean isVoided = true;
    private static Timestamp voidedDateTime = Timestamp.valueOf("2024-11-12 10:09:11");
    private static Timestamp postedDateTime = Timestamp.valueOf("2000-04-05 07:08:09");
    private static String transferName = "1234";
    private static Transfer transfer = new Transfer();
    private static WireTransfer wireTransfer = new WireTransfer();
    private static WireTransfer updatedWireTransfer = new WireTransfer();
    static {
        transfer.setAmount(amount);
        transfer.setCreatedDateTime(createdDateTime);
        transfer.setIsVoided(isVoided);
        transfer.setVoidedDateTime(voidedDateTime);
        transfer.setPostedDateTime(postedDateTime);
        transfer.setTransferName(transferName);

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
    private TransferService transferService;

    @Autowired
    private WireTransferService wireTransferService;

    @Test
    @Order(2)
    void insertWireTransferTest() throws SQLException {
        transfer = transferService.saveTransfer(transfer);
        wireTransfer.setTransfer(transfer);
        updatedWireTransfer.setTransfer(transfer);

        wireTransfer = wireTransferService.saveWireTransfer(wireTransfer);
        updatedWireTransfer.setId(wireTransfer.getId());
        transferId = wireTransfer.getTransfer().getId();
    }
    @Test
    @Order(3)
    void selectWireTransferByTransferIdTest() throws SQLException {
        WireTransfer wireTransfer1 = wireTransferService.getWireTransferByTransferId(
                transferId).get();
        assertTrue(wireTransfer1.getTransfer().getId().equals(transferId));
    }
    @Test
    @Order(4)
    void updateWireTransferByTransferIdTest() throws SQLException {
        wireTransferService.updateWireTransfer(
                updatedWireTransfer);
    }
    @Test
    @Order(5)
    void selectUpdatedWireTransferByTransferId() throws SQLException {
        WireTransfer wireTransfer1 = wireTransferService.getWireTransferByTransferId(
                transferId).get();
        assertTrue(wireTransfer1.getTransfer().getId().equals(transferId));
    }
    @Test
    @Order(6)
    void deleteWireTransferByTransferIdTest() throws SQLException {
        wireTransferService.deleteWireTransfer(
                updatedWireTransfer.getId());

        transferService.deleteTransfer(transferId);
    }

}
