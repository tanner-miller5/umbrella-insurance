package com.umbrella.insurance.endpoints.rest.users.policies.pendingPolicies.pendingPolicyTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.PendingPolicyTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa.PendingPolicyTypeService;
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
@RequestMapping(PendingPolicyTypePrivilege.PATH)
public class PendingPolicyTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PendingPolicyTypeRestEndpoints.class);

    @Autowired
    private PendingPolicyTypeService pendingPolicyTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PendingPolicyType createPendingPolicyType(
            @RequestParam String env,
            @RequestBody PendingPolicyType pendingPolicyType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PendingPolicyType pendingPolicyTypeResponse;
        try {
            request.setAttribute("requestBody", pendingPolicyType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicyTypeResponse = pendingPolicyTypeService.savePendingPolicyType(pendingPolicyType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create pendingPolicyType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyTypeResponse);
        return pendingPolicyTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingPolicyType> readPendingPolicyTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingPolicyTypeId,
            @RequestParam(required = false) String pendingPolicyTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingPolicyType> pendingPolicyTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (pendingPolicyTypeId != null) {
                Optional<PendingPolicyType> pendingPolicyType = pendingPolicyTypeService.getPendingPolicyTypeById(
                        pendingPolicyTypeId);
                if (pendingPolicyType.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingPolicyType ");
                } else {
                    pendingPolicyTypeList = new ArrayList<>();
                    pendingPolicyTypeList.add(pendingPolicyType.get());
                }
            } else if (pendingPolicyTypeName != null) {
                Optional<PendingPolicyType> pendingPolicyType = pendingPolicyTypeService
                        .getPendingPolicyTypeByPendingPolicyTypeName(pendingPolicyTypeName);
                if (pendingPolicyType.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingPolicyType ");
                } else {
                    pendingPolicyTypeList = new ArrayList<>();
                    pendingPolicyTypeList.add(pendingPolicyType.get());
                }
            } else {
                pendingPolicyTypeList = pendingPolicyTypeService.getPendingPolicyTypes();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read pendingPolicyType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyTypeList);
        return pendingPolicyTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingPolicyType> updatePendingPolicyTypes(
            @RequestParam String env,
            @RequestBody PendingPolicyType pendingPolicyType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingPolicyType> pendingPolicyTypeList;
        try {
            request.setAttribute("requestBody", pendingPolicyType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicyTypeService.updatePendingPolicyType(
                    pendingPolicyType);
            pendingPolicyTypeList = new ArrayList<>();
            pendingPolicyTypeList.add(pendingPolicyType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update pendingPolicyType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyTypeList);
        return pendingPolicyTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePendingPolicyTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingPolicyTypeId,
            @RequestParam(required = false) String pendingPolicyTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(pendingPolicyTypeId != null) {
                pendingPolicyTypeService.deletePendingPolicyType(
                        pendingPolicyTypeId);
            } else if (pendingPolicyTypeName != null) {
                pendingPolicyTypeService
                        .deletePendingPolicyTypeByPendingPolicyTypeName(
                                pendingPolicyTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented pendingPolicyType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete pendingPolicyType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
