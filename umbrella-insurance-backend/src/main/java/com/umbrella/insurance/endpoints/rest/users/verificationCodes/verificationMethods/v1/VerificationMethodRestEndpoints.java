package com.umbrella.insurance.endpoints.rest.users.verificationCodes.verificationMethods.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.VerificationMethodPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
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
@RequestMapping(VerificationMethodPrivilege.PATH)
public class VerificationMethodRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(VerificationMethodRestEndpoints.class);

    @Autowired
    private VerificationMethodService verificationMethodService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    VerificationMethod createVerificationMethod(
            @RequestParam String env,
            @RequestBody VerificationMethod verificationMethod,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        VerificationMethod verificationMethodResponse;
        try {
            request.setAttribute("requestBody", verificationMethod);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            verificationMethodResponse = verificationMethodService.saveVerificationMethod(verificationMethod);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create verificationMethod ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", verificationMethodResponse);
        return verificationMethodResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<VerificationMethod> readVerificationMethods(
            @RequestParam String env,
            @RequestParam(required = false) Long verificationMethodId,
            @RequestParam(required = false) String verificationMethodName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<VerificationMethod> verificationMethodList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (verificationMethodId != null) {
                Optional<VerificationMethod> verificationMethod = verificationMethodService
                        .getVerificationMethodByVerificationMethodId(verificationMethodId);
                if (verificationMethod.isEmpty()) {
                    throw new NotFoundException("Unable to read verificationMethod ");
                } else {
                    verificationMethodList = new ArrayList<>();
                    verificationMethodList.add(verificationMethod.get());
                }
            } else if (verificationMethodName != null) {
                Optional<VerificationMethod> verificationMethod = verificationMethodService
                        .getVerificationMethodByVerificationMethodName(verificationMethodName);
                if (verificationMethod.isEmpty()) {
                    throw new NotFoundException("Unable to read verificationMethod ");
                } else {
                    verificationMethodList = new ArrayList<>();
                    verificationMethodList.add(verificationMethod.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented verificationMethod ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read verificationMethod ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", verificationMethodList);
        return verificationMethodList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<VerificationMethod> updateVerificationMethods(
            @RequestParam String env,
            @RequestParam(required = false) Long verificationMethodId,
            @RequestParam(required = false) String verificationMethodName,
            @RequestBody VerificationMethod verificationMethod,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<VerificationMethod> verificationMethodList;
        try {
            request.setAttribute("requestBody", verificationMethod);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            verificationMethod = verificationMethodService.updateVerificationMethod(
                    verificationMethod);
            verificationMethodList = new ArrayList<>();
            verificationMethodList.add(verificationMethod);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update verificationMethod ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", verificationMethodList);
        return verificationMethodList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteVerificationMethods(
            @RequestParam String env,
            @RequestParam(required = false) Long verificationMethodId,
            @RequestParam(required = false) String verificationMethodName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(verificationMethodId != null) {
                verificationMethodService.deleteVerificationMethod(
                        verificationMethodId);
            } else if (verificationMethodName != null) {
                verificationMethodService
                        .deleteVerificationMethodByVerificationMethodName(
                                verificationMethodName);
            } else {
                throw new NotImplementedException("This delete query is not implemented verificationMethod ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete verificationMethod ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
