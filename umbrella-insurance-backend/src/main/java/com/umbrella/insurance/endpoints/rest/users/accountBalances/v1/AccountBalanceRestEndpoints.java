package com.umbrella.insurance.endpoints.rest.users.accountBalances.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.accountBalances.v1.db.AccountBalancePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa.AccountBalanceService;
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
@RequestMapping(AccountBalancePrivilege.PATH)
public class AccountBalanceRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceRestEndpoints.class);

    @Autowired
    private AccountBalanceService accountBalanceService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AccountBalance createAccountBalance(
            @RequestParam String env,
            @RequestBody AccountBalance accountBalance,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        AccountBalance accountBalanceResponse;
        try {
            request.setAttribute("requestBody", accountBalance);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceResponse = accountBalanceService.saveAccountBalance(accountBalance);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create accountBalance ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceResponse);
        return accountBalanceResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalance> readAccountBalances(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long accountBalanceTypeId,
            @RequestParam(required = false) Long unitId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalance> accountBalanceList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (accountBalanceId != null) {
                Optional<AccountBalance> accountBalance = accountBalanceService.getAccountBalanceByAccountBalanceId(
                        accountBalanceId);
                if (accountBalance.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalance ");
                } else {
                    accountBalanceList = new ArrayList<>();
                    accountBalanceList.add(accountBalance.get());
                }
            } else if (userId != null && accountBalanceTypeId != null && unitId != null) {
                Optional<AccountBalance> accountBalance = accountBalanceService
                        .getAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                                userId, accountBalanceTypeId, unitId);
                if (accountBalance.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalance ");
                } else {
                    accountBalanceList = new ArrayList<>();
                    accountBalanceList.add(accountBalance.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented accountBalance ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read accountBalance ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceList);
        return accountBalanceList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalance> updateAccountBalances(
            @RequestParam String env,
            @RequestBody AccountBalance accountBalance,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalance> accountBalanceList;
        try {
            request.setAttribute("requestBody", accountBalance);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalance = accountBalanceService.updateAccountBalance(
                    accountBalance);
            accountBalanceList = new ArrayList<>();
            accountBalanceList.add(accountBalance);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update accountBalance ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceList);
        return accountBalanceList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccountBalances(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long accountBalanceTypeId,
            @RequestParam(required = false) Long unitId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(accountBalanceId != null) {
                accountBalanceService.deleteAccountBalance(
                        accountBalanceId);
            } else if (userId != null && accountBalanceTypeId != null) {
                accountBalanceService
                        .deleteAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                                userId, accountBalanceTypeId, unitId);
            } else {
                throw new NotImplementedException("This delete query is not implemented accountBalance ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete accountBalance ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
