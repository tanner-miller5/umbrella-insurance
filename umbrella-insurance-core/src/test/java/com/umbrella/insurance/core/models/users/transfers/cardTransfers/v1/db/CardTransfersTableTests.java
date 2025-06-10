package com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.CardOnFile;
import com.umbrella.insurance.core.models.entities.CardTransfer;
import com.umbrella.insurance.core.models.entities.Transfer;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa.CardOnFileService;
import com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.jpa.CardTransferService;
import com.umbrella.insurance.core.models.users.transfers.v1.db.jpa.TransferService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardTransfersTableTests {
    private static Double amount = 20.0;
    private static Timestamp createdDateTime = Timestamp.valueOf("2023-10-12 11:11:11");
    private static Boolean isVoided = true;
    private static Timestamp voidedDateTime = Timestamp.valueOf("2024-11-12 10:09:11");
    private static Timestamp postedDateTime = Timestamp.valueOf("2000-04-05 07:08:09");
    private static String transferName = "1234";
    private static String cardNumber = "1";
    private static Date expirationDate = Date.valueOf("2022-10-09");
    private static String cvv = "123";
    private static String phoneNumber = "11111111";
    private static Timestamp deletedDateTime = Timestamp.valueOf("2992-08-24 01:13:11");
    private static Boolean isDeleted = true;
    private static CardOnFile cardOnFile = new CardOnFile();
    private static Transfer transfer = new Transfer();
    private static CardTransfer cardTransfer = new CardTransfer();
    private static CardTransfer updatedCardTransfer = new CardTransfer();
    static {
        transfer.setAmount(amount);
        transfer.setCreatedDateTime(createdDateTime);
        transfer.setIsVoided(isVoided);
        transfer.setVoidedDateTime(voidedDateTime);
        transfer.setPostedDateTime(postedDateTime);
        transfer.setTransferName(transferName);

        cardOnFile.setCardNumber(cardNumber);
        cardOnFile.setExpirationDate(expirationDate);
        cardOnFile.setCvv(cvv);
        //cardOnFile.setUser(userId);
        cardOnFile.setPhoneNumber(phoneNumber);
        //cardOnFile.setLocationId(locationId);
        cardOnFile.setCreatedDateTime(createdDateTime);
        cardOnFile.setDeletedDateTime(deletedDateTime);
        cardOnFile.setIsDeleted(isDeleted);
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
    private CardTransferService cardTransferService;

    @Autowired
    private CardOnFileService cardOnFileService;

    @Test
    @Order(2)
    void insertCardTransferTest() throws SQLException {
        transfer = transferService.saveTransfer(transfer);
        cardTransfer.setTransfer(transfer);
        updatedCardTransfer.setTransfer(transfer);


        cardOnFile = cardOnFileService.saveCardOnFile(cardOnFile);
        cardTransfer.setCardOnFile(cardOnFile);
        updatedCardTransfer.setCardOnFile(cardOnFile);


        cardTransfer = cardTransferService.saveCardTransfer(cardTransfer);
        updatedCardTransfer.setId(cardTransfer.getId());
    }
    @Test
    @Order(3)
    void selectCardTransferByTransferIdTest() throws SQLException {
        CardTransfer cardTransfer1 =
                cardTransferService.getCardTransferByTransferId(
                        transfer.getId()).get();
        assertTrue(cardTransfer1.getCardOnFile().getId().equals(cardTransfer.getCardOnFile().getId()));
        assertTrue(cardTransfer1.getTransfer().getId().equals(transfer.getId()));
    }
    @Test
    @Order(4)
    void updateCardTransferByTransferIdTest() throws SQLException {
        updatedCardTransfer = cardTransferService.updateCardTransfer(
                        updatedCardTransfer);
    }
    @Test
    @Order(5)
    void selectUpdatedCardTransferByTransferId() throws SQLException {
        CardTransfer cardTransfer1 =
                cardTransferService.getCardTransferByTransferId(
                        updatedCardTransfer.getTransfer().getId()).get();
        assertTrue(cardTransfer1.getCardOnFile().getId().equals(cardOnFile.getId()));
        assertTrue(cardTransfer1.getTransfer().getId().equals(transfer.getId()));
    }
    @Test
    @Order(6)
    void deleteCardTransferByTransferIdTest() throws SQLException {
        cardTransferService.deleteCardTransfer(
                        updatedCardTransfer.getId());

        transferService.deleteTransfer(
                transfer.getId());

        cardOnFileService.deleteCardOnFile(cardOnFile.getId());
    }

}
