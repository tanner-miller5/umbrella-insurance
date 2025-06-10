package com.umbrella.insurance.endpoints.rest.users.totps.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.totps.v1.db.TotpPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.totps.v1.db.jpa.TotpService;
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
@RequestMapping(TotpPrivilege.PATH)
public class TotpRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TotpRestEndpoints.class);

    @Autowired
    private TotpService totpService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Totp createTotp(
            @RequestParam String env,
            @RequestBody Totp totp,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Totp totpResponse;
        try {
            request.setAttribute("requestBody", totp);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            totpResponse = totpService.saveTotp(totp);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create totp ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", totpResponse);
        return totpResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Totp> readTotps(
            @RequestParam String env,
            @RequestParam(required = false) Long totpId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Totp> totpList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (totpId != null) {
                Optional<Totp> totp = totpService.getTotpByTotpId(
                        totpId);
                if (totp.isEmpty()) {
                    throw new NotFoundException("Unable to read totp ");
                } else {
                    totpList = new ArrayList<>();
                    totpList.add(totp.get());
                }
            } else if (userId != null) {
                Optional<Totp> totp = totpService
                        .getTotpByUserId(userId);
                if (totp.isEmpty()) {
                    throw new NotFoundException("Unable to read totp ");
                } else {
                    totpList = new ArrayList<>();
                    totpList.add(totp.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented totp ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read totp ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", totpList);
        return totpList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Totp> updateTotps(
            @RequestParam String env,
            @RequestBody Totp totp,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Totp> totpList;
        try {
            request.setAttribute("requestBody", totp);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            totp= totpService.updateTotp(
                    totp);
            totpList = new ArrayList<>();
            totpList.add(totp);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update totp ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", totpList);
        return totpList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTotps(
            @RequestParam String env,
            @RequestParam(required = false) Long totpId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(totpId != null) {
                totpService.deleteTotp(totpId);
            } else if (userId != null) {
                totpService.deleteTotpByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented totp ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete totp ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
