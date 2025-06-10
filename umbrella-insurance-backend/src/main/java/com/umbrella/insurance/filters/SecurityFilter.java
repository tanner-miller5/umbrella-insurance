package com.umbrella.insurance.filters;

import com.umbrella.insurance.core.models.devices.v1.db.jpa.DeviceService;

import com.umbrella.insurance.core.utils.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import com.umbrella.insurance.core.models.entities.Device;
import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;

@Component
@Order(2)
public class SecurityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private Security security;


    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            String session = req.getHeader("session");
            String userAgent = req.getHeader("User-Agent");
            String ip = req.getRemoteAddr();
            Device device = new Device();
            device.setUserAgent(userAgent);
            device.setCreatedDateTime(
                    Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            device.setIpAddress(ip);
            String deviceName = ip + "_" + userAgent;
            device.setDeviceName(deviceName);
            ApplicationPrivilege applicationPrivilege = new ApplicationPrivilege();
            applicationPrivilege.setPath(req.getRequestURI());
            applicationPrivilege.setMethod(req.getMethod());
            String queryString = req.getQueryString();
            String[] queryParams = null;
            Map<String, String> queryMap = new HashMap<String, String>();
            if(queryString != null) {
                queryParams = queryString.split("&");
                if(queryParams != null) {
                    for(int i = 0; i < queryParams.length; i++) {
                        String[] query = queryParams[i].split("=");
                        queryMap.put(query[0], query[1]);
                    }
                }
            }
            Optional<Device> device1 = deviceService.findDeviceByIpAddressAndUserAgent(ip, userAgent);
            if(device1.isEmpty() || device1.get().getId() == null) {
                device = deviceService.saveDevice(device);
                if(device == null) {
                    throw new Exception("Unable to create device record in db");
                } else {
                    Optional<Device> device2  = deviceService.findDeviceByIpAddressAndUserAgent(ip, userAgent);
                    if(device2.isEmpty()) {
                        throw new Exception("Unable to find device record in db");
                    } else {
                        device = device2.get();
                    }
                }
            } else {
                device = device1.get();
            }
            req.setAttribute("device", device);
            if (security.checkPrivilege(session, applicationPrivilege)) {
                req.setAttribute("access", MDC.get("access"));
                req.setAttribute("username", MDC.get("username"));
                chain.doFilter(request, response);
            } else {
                logger.info("SECURITY FILTER UNAUTHORIZED!");
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("SECURITY FILTER EXCEPTION");
            logger.error(e.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}