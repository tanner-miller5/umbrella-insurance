package com.umbrella.insurance.core.models.orderTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.OrderType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.orderTypes.v1.db.jpa.OrderTypeService;
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
public class OrderTypesTableTests {
    private static String orderTypeName = "1234";
    private static String updatedOrderTypeName = "12345";
    private static OrderType orderType = new OrderType();
    private static OrderType updatedOrderType = new OrderType();
    static {
        orderType.setOrderTypeName(orderTypeName);

        updatedOrderType.setOrderTypeName(updatedOrderTypeName);

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
    private OrderTypeService orderTypeService;

    @Test
    @Order(2)
    void insertOrderTypeTest() throws SQLException {
        orderType = orderTypeService.saveOrderType(orderType);
        updatedOrderType.setId(orderType.getId());
    }
    @Test
    @Order(3)
    void selectOrderTypeByOrderTypeNameTest() throws SQLException {
        OrderType orderType1 = orderTypeService.getOrderTypeByOrderTypeName(orderTypeName).get();
        assertTrue(orderType1.getOrderTypeName().equals(orderTypeName));
    }
    @Test
    @Order(4)
    void updateOrderTypeByOrderTypeNameTest() throws SQLException {
        updatedOrderType = orderTypeService
                .updateOrderType(updatedOrderType);
    }
    @Test
    @Order(5)
    void selectUpdatedOrderTypeByOrderTypeNameTest() throws SQLException {
        OrderType orderType1 = orderTypeService
                .getOrderTypeByOrderTypeName(
                        updatedOrderTypeName).get();
        assertTrue(orderType1.getOrderTypeName().equals(updatedOrderTypeName));
    }
    @Test
    @Order(6)
    void deleteOrderTypeByOrderTypeNameTest() throws SQLException {
        orderTypeService
                .deleteOrderType(updatedOrderType.getId());
    }

}
