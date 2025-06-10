package com.umbrella.insurance.core.models.users.bets.betTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.BetType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.bets.betTypes.v1.db.jpa.BetTypeService;
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
public class BetTypesTableTests {
    private static String betTypeName = "1234";
    private static String updatedBetTypeName = "12345";
    private static BetType betType = new BetType();
    private static BetType updatedBetType = new BetType();
    static {
        betType.setBetTypeName(betTypeName);

        updatedBetType.setBetTypeName(updatedBetTypeName);

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
    private BetTypeService betTypeService;

    @Test
    @Order(2)
    void insertBetTypeTest() throws SQLException {
        betType = betTypeService.saveBetType(betType);
        updatedBetType.setId(betType.getId());
    }
    @Test
    @Order(3)
    void selectBetTypeByBetTypeNameTest() throws SQLException {
        BetType betType1 = betTypeService
                .getBetTypeByBetTypeName(betTypeName).get();
        assertTrue(betType1.getBetTypeName().equals(betTypeName));
    }
    @Test
    @Order(4)
    void updateBetTypeByBetTypeNameTest() throws SQLException {
        betTypeService.updateBetType(updatedBetType);
    }
    @Test
    @Order(5)
    void selectUpdatedBetTypeByBetTypeNameTest() throws SQLException {
        BetType betType1 = betTypeService.getBetTypeByBetTypeName(updatedBetTypeName).get();
        assertTrue(betType1.getBetTypeName().equals(updatedBetTypeName));
    }
    @Test
    @Order(6)
    void deleteBetTypeByBetTypeNameTest() throws SQLException {
        betTypeService.deleteBetType(updatedBetType.getId());
    }

}
