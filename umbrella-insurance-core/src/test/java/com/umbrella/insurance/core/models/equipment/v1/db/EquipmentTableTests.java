package com.umbrella.insurance.core.models.equipment.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Equipment;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.equipment.v1.db.jpa.EquipmentService;
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
public class EquipmentTableTests {
    private static String equipmentName = "1234";
    private static String updatedEquipmentName = "12345";
    private static Equipment equipment = new Equipment();
    private static Equipment updatedEquipment = new Equipment();
    static {
        equipment.setEquipmentName(equipmentName);

        updatedEquipment.setEquipmentName(updatedEquipmentName);

    }

    @Autowired
    private EquipmentService equipmentService;

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

    @Test
    @Order(2)
    void insertEquipmentTest() throws SQLException {
        equipment = equipmentService.saveEquipment(equipment);
        updatedEquipment.setId(equipment.getId());
    }
    @Test
    @Order(3)
    void selectEquipmentByEquipmentNameTest() throws SQLException {
        Equipment equipment1 = equipmentService.getEquipmentByEquipmentName(equipmentName).get();
        assertTrue(equipment1.getEquipmentName().equals(equipmentName));
    }
    @Test
    @Order(4)
    void updateEquipmentByEquipmentNameTest() throws SQLException {
        updatedEquipment = equipmentService.updateEquipment(updatedEquipment);
    }
    @Test
    @Order(5)
    void selectUpdatedEquipmentByEquipmentNameTest() throws SQLException {
        Equipment equipment1 = equipmentService.getEquipmentByEquipmentName(updatedEquipmentName).get();
        assertTrue(equipment1.getEquipmentName().equals(updatedEquipmentName));
    }
    @Test
    @Order(6)
    void deleteEquipmentByEquipmentNameTest() throws SQLException {
        equipmentService.deleteEquipment(updatedEquipment.getId());
    }
}
