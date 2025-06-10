package com.umbrella.insurance.core.models.devices.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    Device saveDevice(Device device);
    List<Device> getDevices();
    Device updateDevice(Device device);
    void deleteDevice(Long deviceId);
    Optional<Device> findDeviceByIpAddressAndUserAgent(String ipAddress, String userAgent);
    Optional<Device> findDeviceByDeviceId(Long deviceId);
    void deleteDeviceByDeviceName(String deviceName);

}
