package com.umbrella.insurance.endpoints.rest.devices.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.devices.v1.db.DevicePrivilege;
import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(DevicePrivilege.PATH)
public class DeviceRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(DeviceRestEndpoints.class);

    @Autowired
    private DeviceService deviceService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Device createDevice(
            @RequestParam String env,
            @RequestBody Device device,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Device deviceResponse;
        try {
            request.setAttribute("requestBody", device);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            deviceResponse = deviceService.saveDevice(device);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create device ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", deviceResponse);
        return deviceResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Device> readDevices(
            @RequestParam String env,
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) String userAgent,
            @RequestParam(required = false) String ipAddress,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Device> deviceList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (deviceId != null) {
                Optional<Device> device = deviceService.findDeviceByDeviceId(deviceId);
                if(device.isEmpty()) {
                    throw new NotFoundException("Device not found");
                } else {
                    deviceList = new ArrayList<>();
                    deviceList.add(device.get());
                }

            } else if (userAgent != null && ipAddress != null) {
                Optional<Device> device = deviceService.findDeviceByIpAddressAndUserAgent(
                        ipAddress, userAgent);
                if(device.isEmpty()) {
                    throw new NotFoundException("Device not found");
                } else {
                    deviceList = new ArrayList<>();
                    deviceList.add(device.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented device ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read device ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", deviceList);
        return deviceList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Device> updateDevices(
            @RequestParam String env,
            @RequestParam(required = false) Long deviceId,
            @RequestBody Device device,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Device> deviceList;
        try {
            request.setAttribute("requestBody", device);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (deviceId != null) {
                device = deviceService.updateDevice(device);
                deviceList = new ArrayList<>();
                deviceList.add(device);
            } else {
                throw new NotImplementedException("This update query is not implemented device ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update device ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", deviceList);
        return deviceList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDevices(@RequestParam String env,
                              @RequestParam(required = false) Long deviceId,
                              @RequestParam(required = false) String deviceName,
                              @RequestAttribute BigInteger currentRequestNumber,
                              ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(deviceId != null) {
                deviceService.deleteDevice(deviceId);
            } else if (deviceName != null) {
                deviceService.deleteDeviceByDeviceName(deviceName);
            } else {
                throw new NotImplementedException("This delete query is not implemented device ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete device ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
