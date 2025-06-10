package com.umbrella.insurance.core.models.users.cardsOnFile.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.CardOnFile;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa.CardOnFileService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardsOnFileTableTests {
    private static String cardNumber = "1";
    private static Date expirationDate = Date.valueOf("2022-10-09");
    private static String cvv = "123";
    //private static BigInteger userId = BigInteger.valueOf(2);
    private static String phoneNumber = "11111111";
    //private static BigInteger locationId = BigInteger.valueOf(3);//billingAddress;
    private static Timestamp createdDateTime = Timestamp.valueOf("1992-07-24 07:11:11");
    private static Timestamp deletedDateTime = Timestamp.valueOf("2992-08-24 01:13:11");
    private static Boolean isDeleted = true;
    private static String updatedCardNumber = "5433";
    private static Date updatedExpirationDate = Date.valueOf("1111-10-11");
    private static String updatedCvv = "543";
    //private static BigInteger updatedUserId = BigInteger.valueOf(4);
    private static String updatedPhoneNumber = "9999";
    //private static BigInteger updatedLocationId = BigInteger.valueOf(5);//billingAddress;
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("1000-12-12 13:13:13");
    private static Timestamp updatedDeletedDateTime = Timestamp.valueOf("1999-1-2 14:15:16");
    private static Boolean updatedIsDeleted = false;
    private static CardOnFile cardOnFile = new CardOnFile();
    private static CardOnFile updatedCardOnFile = new CardOnFile();
    static {
        cardOnFile.setCardNumber(cardNumber);
        cardOnFile.setExpirationDate(expirationDate);
        cardOnFile.setCvv(cvv);
        //cardOnFile.setUser(user);
        cardOnFile.setPhoneNumber(phoneNumber);
        //cardOnFile.setLocationId(locationId);
        cardOnFile.setCreatedDateTime(createdDateTime);
        cardOnFile.setDeletedDateTime(deletedDateTime);
        cardOnFile.setIsDeleted(isDeleted);

        updatedCardOnFile.setCardNumber(updatedCardNumber);
        updatedCardOnFile.setExpirationDate(updatedExpirationDate);
        updatedCardOnFile.setCvv(updatedCvv);
        //updatedCardOnFile.setUserId(updatedUserId);
        updatedCardOnFile.setPhoneNumber(updatedPhoneNumber);
        //updatedCardOnFile.setLocationId(updatedLocationId);
        updatedCardOnFile.setCreatedDateTime(updatedCreatedDateTime);
        updatedCardOnFile.setDeletedDateTime(updatedDeletedDateTime);
        updatedCardOnFile.setIsDeleted(updatedIsDeleted);

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
    private CardOnFileService cardOnFileService;

    @Test
    @Order(2)
    void insertCardOnFileTest() throws SQLException {
        cardOnFile = cardOnFileService.saveCardOnFile(cardOnFile);
        updatedCardOnFile.setId(cardOnFile.getId());
    }
    @Test
    @Order(3)
    void selectCardOnFileByCardNumberTest() throws SQLException {
        CardOnFile cardOnFile1 = cardOnFileService.getCardOnFileByCardNumber(cardNumber).get();
        assertTrue(cardOnFile1.getCardNumber().equals(cardNumber));
        assertTrue(cardOnFile1.getExpirationDate().equals(expirationDate));
        assertTrue(cardOnFile1.getCvv().equals(cvv));
        //assertTrue(cardOnFile1.getUserId().equals(userId));
        assertTrue(cardOnFile1.getPhoneNumber().equals(phoneNumber));
        //assertTrue(cardOnFile1.getLocationId().equals(locationId));
        assertTrue(cardOnFile1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(cardOnFile1.getDeletedDateTime().equals(deletedDateTime));
        assertTrue(cardOnFile1.getIsDeleted().equals(isDeleted));
    }
    @Test
    @Order(4)
    void updateCardOnFileByCardNumberTest() throws SQLException {
        updatedCardOnFile = cardOnFileService.updateCardOnFile(updatedCardOnFile);
    }
    @Test
    @Order(5)
    void selectUpdatedCardOnFileByCardNumberTest() throws SQLException {
        CardOnFile cardOnFile1 = cardOnFileService
                .getCardOnFileByCardNumber(updatedCardNumber).get();
        assertTrue(cardOnFile1.getCardNumber().equals(updatedCardNumber));
        assertTrue(cardOnFile1.getExpirationDate().equals(updatedExpirationDate));
        assertTrue(cardOnFile1.getCvv().equals(updatedCvv));
        //assertTrue(cardOnFile1.getUserId().equals(updatedUserId));
        assertTrue(cardOnFile1.getPhoneNumber().equals(updatedPhoneNumber));
        //assertTrue(cardOnFile1.getLocationId().equals(updatedLocationId));
        assertTrue(cardOnFile1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(cardOnFile1.getDeletedDateTime().equals(updatedDeletedDateTime));
        assertTrue(cardOnFile1.getIsDeleted().equals(updatedIsDeleted));
    }
    @Test
    @Order(6)
    void deleteCardOnFileByCardOnFileNameTest() throws SQLException {
        cardOnFileService.deleteCardOnFile(updatedCardOnFile.getId());
    }
}
