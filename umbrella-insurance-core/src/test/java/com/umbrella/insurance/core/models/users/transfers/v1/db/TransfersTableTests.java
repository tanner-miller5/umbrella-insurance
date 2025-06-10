package com.umbrella.insurance.core.models.users.transfers.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Transfer;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.transfers.v1.db.jpa.TransferService;
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
public class TransfersTableTests {
    private static Double amount = 20.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("2023-10-12 11:11:11");
    private static Boolean isVoided = true;
    private static Timestamp voidedDateTime = Timestamp.valueOf("2024-11-12 10:09:11");
    private static Timestamp postedDateTime = Timestamp.valueOf("2000-04-05 07:08:09");
    private static String transferName = "1234";
    private static Double updatedAmount = 40.0;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2001-04-05 07:08:09");
    private static Boolean updatedIsVoided = false;
    private static Timestamp updatedVoidedDateTime = Timestamp.valueOf("2002-04-05 07:08:09");
    private static Timestamp updatedPostedDateTime = Timestamp.valueOf("2100-04-05 07:08:09");
    private static String updatedTransferName = "12345";
    private static Transfer transfer = new Transfer();
    private static Transfer updatedTransfer = new Transfer();
    static {
        transfer.setAmount(amount);
        transfer.setCreatedDateTime(createdDateTime);
        transfer.setIsVoided(isVoided);
        transfer.setVoidedDateTime(voidedDateTime);
        transfer.setPostedDateTime(postedDateTime);
        transfer.setTransferName(transferName);

        updatedTransfer.setAmount(updatedAmount);
        updatedTransfer.setCreatedDateTime(updatedCreatedDateTime);
        updatedTransfer.setIsVoided(updatedIsVoided);
        updatedTransfer.setVoidedDateTime(updatedVoidedDateTime);
        updatedTransfer.setPostedDateTime(updatedPostedDateTime);
        updatedTransfer.setTransferName(updatedTransferName);
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

    @Test
    @Order(2)
    void insertTransferTest() throws SQLException {
        transfer = transferService.saveTransfer(transfer);
        updatedTransfer.setId(transfer.getId());
    }
    @Test
    @Order(3)
    void selectTransferByTransferNameTest() throws SQLException {
        Transfer transfer1 = transferService.
                getTransferByTransferName(transferName).get();
        assertTrue(transfer1.getTransferName().equals(transferName));
        //assertTrue(transfer1.getUser().getId() == null);
        //assertTrue(transfer1.getUnit().getId() == null);
        assertTrue(transfer1.getAmount().equals(amount));
        assertTrue(transfer1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(transfer1.getIsVoided().equals(isVoided));
        //assertTrue(transfer1.getVoidedEvent().getId() == null);
        assertTrue(transfer1.getVoidedDateTime().equals(voidedDateTime));
        assertTrue(transfer1.getPostedDateTime().equals(postedDateTime));
    }
    @Test
    @Order(4)
    void updateTransferByTransferNameTest() throws SQLException {
        updatedTransfer = transferService.updateTransfer(updatedTransfer);
    }
    @Test
    @Order(5)
    void selectUpdatedTransferByTransferNameTest() throws SQLException {
        Transfer transfer1 =
                transferService.getTransferByTransferName(
                        updatedTransferName).get();
        assertTrue(transfer1.getTransferName().equals(updatedTransferName));
        //assertTrue(transfer1.getUser().getId() == null);
        //assertTrue(transfer1.getUnit().getId() == null);
        assertTrue(transfer1.getAmount().equals(updatedAmount));
        assertTrue(transfer1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(transfer1.getIsVoided().equals(updatedIsVoided));
        //assertTrue(transfer1.getVoidedEvent().getId() == null);
        assertTrue(transfer1.getVoidedDateTime().equals(updatedVoidedDateTime));
        assertTrue(transfer1.getPostedDateTime().equals(updatedPostedDateTime));
    }
    @Test
    @Order(6)
    void deleteTransferTransactionByTransferTransactionNameTest() throws SQLException {
        transferService.deleteTransfer(updatedTransfer.getId());
    }

}
