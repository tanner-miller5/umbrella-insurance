package com.umbrella.insurance.endpoints.rest.geographies.zipCodes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.ZipCodePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
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
@RequestMapping(ZipCodePrivilege.PATH)
public class ZipCodeRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(ZipCodeRestEndpoints.class);

    @Autowired
    private ZipCodeService zipCodeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ZipCode createZipCode(
            @RequestParam String env,
            @RequestBody ZipCode zipCode,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ZipCode zipCodeResponse;
        try {
            request.setAttribute("requestBody", zipCode);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            zipCodeResponse = zipCodeService.saveZipCode(zipCode);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create zipCode ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", zipCodeResponse);
        return zipCodeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ZipCode> readZipCodes(
            @RequestParam String env,
            @RequestParam(required = false) Long zipCodeId,
            @RequestParam(required = false) String zipCodeValue,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ZipCode> zipCodeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (zipCodeId != null) {
                Optional<ZipCode> zipCode = zipCodeService.getZipCodeById(
                        zipCodeId);
                if (zipCode.isEmpty()) {
                    throw new NotFoundException("Unable to read zipCode ");
                } else {
                    zipCodeList = new ArrayList<>();
                    zipCodeList.add(zipCode.get());
                }
            } else if (zipCodeValue != null) {
                Optional<ZipCode> zipCode = zipCodeService.getZipCodeByZipCodeValue(
                        zipCodeValue);
                if (zipCode.isEmpty()) {
                    throw new NotFoundException("Unable to read zipCode ");
                } else {
                    zipCodeList = new ArrayList<>();
                    zipCodeList.add(zipCode.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented zipCode ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read zipCode ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", zipCodeList);
        return zipCodeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ZipCode> updateZipCodes(
            @RequestParam String env,
            @RequestBody ZipCode zipCode,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ZipCode> zipCodeList;
        try {
            request.setAttribute("requestBody", zipCode);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            zipCode = zipCodeService.updateZipCode(zipCode);
            zipCodeList = new ArrayList<>();
            zipCodeList.add(zipCode);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update zipCode ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", zipCodeList);
        return zipCodeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteZipCodes(@RequestParam String env,
                           @RequestParam(required = false) Long zipCodeId,
                           @RequestParam(required = false) String zipCodeValue,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(zipCodeId != null) {
                zipCodeService.deleteZipCode(zipCodeId);
            } else if (zipCodeValue != null) {
                zipCodeService.deleteZipCodeByZipCodeValue(
                        zipCodeValue);
            } else {
                throw new NotImplementedException("This delete query is not implemented zipCode ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete zipCode ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
