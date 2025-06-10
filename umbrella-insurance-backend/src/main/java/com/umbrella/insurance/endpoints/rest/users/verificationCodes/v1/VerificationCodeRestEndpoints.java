package com.umbrella.insurance.endpoints.rest.users.verificationCodes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.VerificationCodePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
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
@RequestMapping(VerificationCodePrivilege.PATH)
public class VerificationCodeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeRestEndpoints.class);

    @Autowired
    private VerificationCodeService verificationCodeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    VerificationCode createVerificationCode(
            @RequestParam String env,
            @RequestBody VerificationCode verificationCode,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        VerificationCode verificationCodeResponse;
        try {
            request.setAttribute("requestBody", verificationCode);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            verificationCodeResponse = verificationCodeService.saveVerificationCode(verificationCode);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create verificationCode ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", verificationCodeResponse);
        return verificationCodeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<VerificationCode> readVerificationCodes(
            @RequestParam String env,
            @RequestParam(required = false) Long verificationCodeId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<VerificationCode> verificationCodeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (verificationCodeId != null) {
                Optional<VerificationCode> verificationCode = verificationCodeService
                        .getVerificationCodeByVerificationCodeId(verificationCodeId);
                if (verificationCode.isEmpty()) {
                    throw new NotFoundException("Unable to read verificationCode");
                } else {
                    verificationCodeList = new ArrayList<>();
                    verificationCodeList.add(verificationCode.get());
                }
            } else if (userId != null) {
                Optional<VerificationCode> verificationCode = verificationCodeService
                        .getVerificationCodeByUserId(userId);
                if (verificationCode.isEmpty()) {
                    throw new NotFoundException("Unable to read verificationCode");
                } else {
                    verificationCodeList = new ArrayList<>();
                    verificationCodeList.add(verificationCode.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented verificationCode");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read verificationCode");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", verificationCodeList);
        return verificationCodeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<VerificationCode> updateVerificationCodes(
            @RequestParam String env,
            @RequestParam(required = false) Long verificationCodeId,
            @RequestParam(required = false) Long userId,
            @RequestBody VerificationCode verificationCode,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<VerificationCode> verificationCodeList;
        try {
            request.setAttribute("requestBody", verificationCode);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            verificationCode = verificationCodeService.updateVerificationCode(
                    verificationCode);
            verificationCodeList = new ArrayList<>();
            verificationCodeList.add(verificationCode);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update verificationCode");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", verificationCodeList);
        return verificationCodeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteVerificationCodes(
            @RequestParam String env,
            @RequestParam(required = false) Long verificationCodeId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(verificationCodeId != null) {
                verificationCodeService.deleteVerificationCode(
                        verificationCodeId);
            } else if (userId != null) {
                verificationCodeService
                        .getVerificationCodeByUserId(
                                userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented verificationCode ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete verificationCode");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
