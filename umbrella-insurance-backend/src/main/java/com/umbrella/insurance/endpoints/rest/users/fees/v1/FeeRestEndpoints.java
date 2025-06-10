package com.umbrella.insurance.endpoints.rest.users.fees.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.fees.v1.db.FeePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.fees.v1.db.jpa.FeeService;
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
@RequestMapping(FeePrivilege.PATH)
public class FeeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(FeeRestEndpoints.class);

    @Autowired
    private FeeService feeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Fee createFee(
            @RequestParam String env,
            @RequestBody Fee fee,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Fee feeResponse;
        try {
            request.setAttribute("requestBody", fee);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            feeResponse = feeService.saveFee(fee);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create fee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", feeResponse);
        return feeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Fee> readFees(
            @RequestParam String env,
            @RequestParam(required = false) Long feeId,
            @RequestParam(required = false) String feeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Fee> feeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (feeId != null) {
                Optional<Fee> fee = feeService.getFeeById(feeId);
                if (fee.isEmpty()) {
                    throw new NotFoundException("Unable to read fee ");
                } else {
                    feeList = new ArrayList<>();
                    feeList.add(fee.get());
                }
            } else if (!feeName.isEmpty()) {
                Optional<Fee> fee = feeService
                        .getFeeByFeeName(feeName);
                if (fee.isEmpty()) {
                    throw new NotFoundException("Unable to read fee ");
                } else {
                    feeList = new ArrayList<>();
                    feeList.add(fee.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented fee ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read fee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", feeList);
        return feeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Fee> updateFees(
            @RequestParam String env,
            @RequestBody Fee fee,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Fee> feeList;
        try {
            request.setAttribute("requestBody", fee);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            fee = feeService.updateFee(fee);
            feeList = new ArrayList<>();
            feeList.add(fee);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update fee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", feeList);
        return feeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFees(
            @RequestParam String env,
            @RequestParam(required = false) Long feeId,
            @RequestParam(required = false) String feeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(feeId != null) {
                feeService.deleteFee(
                        feeId);
            } else if (feeName != null) {
                feeService.deleteFeeByFeeName(feeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented fee ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete fee ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
