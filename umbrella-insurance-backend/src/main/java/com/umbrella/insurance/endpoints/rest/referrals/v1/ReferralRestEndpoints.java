package com.umbrella.insurance.endpoints.rest.referrals.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.referrals.v1.db.ReferralPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.referrals.v1.db.jpa.ReferralService;
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
@RequestMapping(ReferralPrivilege.PATH)
public class ReferralRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ReferralRestEndpoints.class);

    @Autowired
    private ReferralService referralService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Referral createReferral(
            @RequestParam String env,
            @RequestBody Referral referral,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Referral referralResponse;
        try {
            request.setAttribute("requestBody", referral);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            referralResponse = referralService.saveReferral(referral);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create referral ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", referralResponse);
        return referralResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Referral> readReferrals(
            @RequestParam String env,
            @RequestParam(required = false) Long referralId,
            @RequestParam(required = false) String referralName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Referral> referralList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (referralId != null) {
                Optional<Referral> referral = referralService.getReferral(
                        referralId);
                if (referral.isEmpty()) {
                    throw new NotFoundException("Unable to read referral ");
                } else {
                    referralList = new ArrayList<>();
                    referralList.add(referral.get());
                }
            } else if (referralName != null) {
                Optional<Referral> referral = referralService
                        .getReferralByReferralName(referralName);
                if (referral.isEmpty()) {
                    throw new NotFoundException("Unable to read referral ");
                } else {
                    referralList = new ArrayList<>();
                    referralList.add(referral.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented referral ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read referral ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", referralList);
        return referralList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Referral> updateReferrals(
            @RequestParam String env,
            @RequestBody Referral referral,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Referral> referralList;
        try {
            request.setAttribute("requestBody", referral);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            referral = referralService.updateReferral(
                    referral);
            referralList = new ArrayList<>();
            referralList.add(referral);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update referral ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", referralList);
        return referralList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReferrals(
            @RequestParam String env,
            @RequestParam(required = false) Long referralId,
            @RequestParam(required = false) String referralName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(referralId != null) {
                referralService.deleteReferral(
                        referralId);
            } else if (referralName != null) {
                referralService
                        .deleteReferralByReferralName(
                                referralName);
            } else {
                throw new NotImplementedException("This delete query is not implemented referral ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete referral ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
