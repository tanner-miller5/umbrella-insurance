package com.umbrella.insurance.core.models.perils.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Peril;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.perils.v1.db.jpa.PerilService;
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
public class PerilsTableTests {
    private static String perilName = "1234";
    private static String updatedPerilName = "12345";
    private static Peril peril = new Peril();
    private static Peril updatedPeril = new Peril();
    static {
        peril.setPerilName(perilName);

        updatedPeril.setPerilName(updatedPerilName);

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

    @Autowired
    private PerilService perilService;

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Test
    @Order(2)
    void insertPerilTest() throws SQLException {
        peril = perilService.savePeril(peril);
        updatedPeril.setId(peril.getId());
    }
    @Test
    @Order(3)
    void selectPerilByPerilNameTest() throws SQLException {
        Peril peril1 = perilService.getPerilByPerilName(perilName).get();
        assertTrue(peril1.getPerilName().equals(perilName));
    }
    @Test
    @Order(4)
    void updatePerilByPerilNameTest() throws SQLException {
        perilService.updatePeril(updatedPeril);
    }
    @Test
    @Order(5)
    void selectUpdatedPerilByPerilNameTest() throws SQLException {
        Peril peril1 = perilService.getPerilByPerilName(updatedPerilName).get();
        assertTrue(peril1.getPerilName().equals(updatedPerilName));
    }
    @Test
    @Order(6)
    void deletePerilByPerilNameTest() throws SQLException {
        perilService.deletePeril(updatedPeril.getId());
    }
}
