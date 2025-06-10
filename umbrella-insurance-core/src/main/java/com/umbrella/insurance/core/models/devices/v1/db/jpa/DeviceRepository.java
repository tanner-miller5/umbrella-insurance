package com.umbrella.insurance.core.models.devices.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findDeviceByIpAddressAndUserAgent(String ipAddress, String userAgent);
    void deleteByDeviceName(String deviceName);
}
