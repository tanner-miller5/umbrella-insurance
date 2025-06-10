package com.umbrella.insurance.core.models.devices.v1.db.jpa;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.Device;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device updateDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(Long deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    @Override
    public Optional<Device> findDeviceByIpAddressAndUserAgent(String ipAddress, String userAgent) {
        return deviceRepository.findDeviceByIpAddressAndUserAgent(ipAddress, userAgent);
    }

    @Override
    public Optional<Device> findDeviceByDeviceId(Long deviceId) {
        return deviceRepository.findById(deviceId);
    }

    @Override
    public void deleteDeviceByDeviceName(String deviceName) {
        deviceRepository.deleteByDeviceName(deviceName);
    }
}
