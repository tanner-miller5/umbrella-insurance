package com.umbrella.insurance.endpoints.rest.geographies.streetAddresses.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.StreetAddressPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
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
@RequestMapping(StreetAddressPrivilege.PATH)
public class StreetAddressRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(StreetAddressRestEndpoints.class);

    @Autowired
    private StreetAddressService streetAddressService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    StreetAddress createStreetAddress(
            @RequestParam String env,
            @RequestBody StreetAddress streetAddress,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        StreetAddress streetAddressResponse;
        try {
            request.setAttribute("requestBody", streetAddress);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            streetAddressResponse = streetAddressService.saveStreetAddress(streetAddress);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create streetAddress ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", streetAddressResponse);
        return streetAddressResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<StreetAddress> readStreetAddress(
            @RequestParam String env,
            @RequestParam(required = false) Long streetAddressId,
            @RequestParam(required = false) String streetAddressLine1,
            @RequestParam(required = false) String streetAddressLine2,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<StreetAddress> streetAddressList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (streetAddressId != null) {
                Optional<StreetAddress> streetAddress = streetAddressService.getStreetAddressByStreetAddressId(
                        streetAddressId);
                if (streetAddress.isEmpty()) {
                    throw new NotFoundException("Unable to read streetAddress ");
                } else {
                    streetAddressList = new ArrayList<>();
                    streetAddressList.add(streetAddress.get());
                }
            } else if (streetAddressLine1 != null && streetAddressLine2 != null) {
                Optional<StreetAddress> streetAddress = streetAddressService.getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                        streetAddressLine1, streetAddressLine2);
                if (streetAddress.isEmpty()) {
                    throw new NotFoundException("Unable to read streetAddress ");
                } else {
                    streetAddressList = new ArrayList<>();
                    streetAddressList.add(streetAddress.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented streetAddress ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read streetAddress ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", streetAddressList);
        return streetAddressList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<StreetAddress> updateStreetAddress(
            @RequestParam String env,
            @RequestBody StreetAddress streetAddress,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<StreetAddress> streetAddressList;
        try {
            request.setAttribute("requestBody", streetAddress);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            streetAddress = streetAddressService.updateStreetAddress(
                    streetAddress);
            streetAddressList = new ArrayList<>();
            streetAddressList.add(streetAddress);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update streetAddress ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", streetAddressList);
        return streetAddressList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteStreetAddress(
            @RequestParam String env, @RequestParam(required = false) Long streetAddressId,
            @RequestParam(required = false) String streetAddressLine1,
            @RequestParam(required = false) String streetAddressLine2,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(streetAddressId != null) {
                streetAddressService.deleteStreetAddress(streetAddressId);
            } else if (streetAddressLine1 != null && streetAddressLine2 != null) {
                streetAddressService.deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                        streetAddressLine1, streetAddressLine2);
            } else {
                throw new NotImplementedException("This delete query is not implemented streetAddress ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete streetAddress ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
