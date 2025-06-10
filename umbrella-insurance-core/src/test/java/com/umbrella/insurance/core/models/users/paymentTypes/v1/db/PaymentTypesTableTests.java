package com.umbrella.insurance.core.models.users.paymentTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.PaymentType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.paymentTypes.v1.db.jpa.PaymentTypeService;
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
public class PaymentTypesTableTests {
    private static String paymentTypeName = "1234";
    private static String updatedPaymentTypeName = "12345";
    private static PaymentType paymentType = new PaymentType();
    private static PaymentType updatedPaymentType = new PaymentType();
    static {
        paymentType.setPaymentTypeName(paymentTypeName);

        updatedPaymentType.setPaymentTypeName(updatedPaymentTypeName);

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
    private PaymentTypeService paymentTypeService;

    @Test
    @Order(2)
    void insertPaymentTypeTest() throws SQLException {
        paymentType = paymentTypeService.savePaymentType(paymentType);
        updatedPaymentType.setId(paymentType.getId());
    }
    @Test
    @Order(3)
    void selectPaymentTypeByPaymentTypeNameTest() throws SQLException {
        PaymentType paymentType1 = paymentTypeService.findPaymentTypeByPaymentTypeName(
                paymentTypeName).get();
        assertTrue(paymentType1.getPaymentTypeName().equals(paymentTypeName));
    }
    @Test
    @Order(4)
    void updatePaymentTypeByPaymentTypeNameTest() throws SQLException {
        updatedPaymentType = paymentTypeService
                .updatePaymentType(updatedPaymentType);
    }
    @Test
    @Order(5)
    void selectUpdatedPaymentTypeByPaymentTypeNameTest() throws SQLException {
            PaymentType paymentType1 = paymentTypeService
                    .findPaymentTypeByPaymentTypeName(updatedPaymentTypeName).get();
            assertTrue(paymentType1.getPaymentTypeName().equals(updatedPaymentTypeName));
    }
    @Test
    @Order(6)
    void deletePaymentTypeByPaymentTypeNameTest() throws SQLException {
        paymentTypeService.deletePaymentType(updatedPaymentType.getId());
    }

}
