package com.umbrella.insurance.core.models.devices.v1.db;

import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.Device;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
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
public class DevicesTableTests {
    private static String deviceName = "1234";
    private static String ipAddress = "2";
    private static String userAgent = "3";
    private static Timestamp createdDateTime = Timestamp.valueOf("2023-12-12 12:12:00");
    private static String updatedDeviceName = "12345";
    private static String updatedIpAddress = "22";
    private static String updatedUserAgent = "33";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2024-11-11 11:11:00");
    private static Device device = new Device();
    private static Device updatedDevice = new Device();
    static {
        device.setDeviceName(deviceName);
        device.setIpAddress(ipAddress);
        device.setUserAgent(userAgent);
        device.setCreatedDateTime(createdDateTime);
        //
        updatedDevice.setDeviceName(updatedDeviceName);
        updatedDevice.setIpAddress(updatedIpAddress);
        updatedDevice.setUserAgent(updatedUserAgent);
        updatedDevice.setCreatedDateTime(updatedCreatedDateTime);

    }

    @Autowired
    DeviceService deviceService;

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
    @Order(3)
    void insertDeviceTest() throws SQLException {
        device = deviceService.saveDevice(device);
        updatedDevice.setId(device.getId());
    }
    @Test
    @Order(4)
    void selectDeviceByIpAddressAndUserAgentTest() throws SQLException {
        Device device1 = deviceService.findDeviceByIpAddressAndUserAgent(
                ipAddress, userAgent).get();
        assertTrue(device1.getDeviceName().equals(deviceName));
        assertTrue(device1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(device1.getIpAddress().equals(ipAddress));
        assertTrue(device1.getUserAgent().equals(userAgent));
    }
    @Test
    @Order(5)
    void updateDeviceByIpAddressAndUserAgentTest() throws SQLException {
        updatedDevice = deviceService.updateDevice(
                updatedDevice);

    }
    @Test
    @Order(6)
    void selectUpdatedDeviceByIpAddressAndUserAgentTest() throws SQLException {
        Device device1 = deviceService.findDeviceByIpAddressAndUserAgent(
                updatedIpAddress, updatedUserAgent).get();
        assertTrue(device1.getDeviceName().equals(updatedDeviceName));
        assertTrue(device1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(device1.getIpAddress().equals(updatedIpAddress));
        assertTrue(device1.getUserAgent().equals(updatedUserAgent));
    }
    @Test
    @Order(7)
    void deleteDeviceByDeviceNameTest() throws SQLException {
        deviceService.deleteDevice(
                updatedDevice.getId());
    }
}
