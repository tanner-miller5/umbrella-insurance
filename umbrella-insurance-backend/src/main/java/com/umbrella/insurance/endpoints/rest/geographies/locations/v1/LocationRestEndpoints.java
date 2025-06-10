package com.umbrella.insurance.endpoints.rest.geographies.locations.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.LocationPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
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
@RequestMapping(LocationPrivilege.PATH)
public class LocationRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(LocationRestEndpoints.class);

    @Autowired
    private LocationService locationService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Location createLocation(
            @RequestParam String env,
            @RequestBody Location location,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Location locationResponse;
        try {
            request.setAttribute("requestBody", location);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            locationResponse = locationService.saveLocation(location);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create location ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", locationResponse);
        return locationResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Location> readLocations(
            @RequestParam String env,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) String locationName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Location> locationList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (locationId != null) {
                Optional<Location> location = locationService.getLocationByLocationId(
                        locationId);
                if (location.isEmpty()) {
                    throw new NotFoundException("Unable to read location ");
                } else {
                    locationList = new ArrayList<>();
                    locationList.add(location.get());
                }
            } else if (locationName != null) {
                Optional<Location> location = locationService.getLocationByLocationName(
                        locationName);
                if (location.isEmpty()) {
                    throw new NotFoundException("Unable to read location ");
                } else {
                    locationList = new ArrayList<>();
                    locationList.add(location.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented location ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read location ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", locationList);
        return locationList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Location> updateLocations(
            @RequestParam String env,
            @RequestBody Location location,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Location> locationList;
        try {
            request.setAttribute("requestBody", location);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            locationService.updateLocation(location);
            locationList = new ArrayList<>();
            locationList.add(location);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update location ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", locationList);
        return locationList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLocations(@RequestParam String env,
                           @RequestParam(required = false) Long locationId,
                           @RequestParam(required = false) String locationName,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(locationId != null) {
                locationService.deleteLocation(locationId);
            } else if (locationName != null) {
                locationService.deleteByLocationName(locationName);
            } else {
                throw new NotImplementedException("This delete query is not implemented location ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete location ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
