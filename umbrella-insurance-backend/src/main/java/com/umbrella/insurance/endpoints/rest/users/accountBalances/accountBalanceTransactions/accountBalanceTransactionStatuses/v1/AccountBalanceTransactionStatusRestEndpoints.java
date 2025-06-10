package com.umbrella.insurance.endpoints.rest.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.AccountBalanceTransactionStatusPrivilege;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa.AccountBalanceTransactionStatusService;
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
@RequestMapping(AccountBalanceTransactionStatusPrivilege.PATH)
public class AccountBalanceTransactionStatusRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceTransactionStatusRestEndpoints.class);

    @Autowired
    private AccountBalanceTransactionStatusService accountBalanceTransactionStatusService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AccountBalanceTransactionStatus createAccountBalanceTransactionStatus(
            @RequestParam String env,
            @RequestBody AccountBalanceTransactionStatus accountBalanceTransactionStatus,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        AccountBalanceTransactionStatus accountBalanceTransactionStatusResponse;
        try {
            request.setAttribute("requestBody", accountBalanceTransactionStatus);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTransactionStatusResponse = accountBalanceTransactionStatusService
                    .saveAccountBalanceTransactionStatus(accountBalanceTransactionStatus);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create accountBalanceTransactionStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionStatusResponse);
        return accountBalanceTransactionStatusResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceTransactionStatus> readAccountBalanceTransactionStatuses(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTransactionStatusId,
            @RequestParam(required = false) String accountBalanceTransactionStatusName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceTransactionStatus> accountBalanceTransactionStatusList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (accountBalanceTransactionStatusId != null) {
                Optional<AccountBalanceTransactionStatus> accountBalanceTransactionStatus = accountBalanceTransactionStatusService
                        .getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusId(
                        accountBalanceTransactionStatusId);
                if (accountBalanceTransactionStatus.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceTransactionStatus");
                } else {
                    accountBalanceTransactionStatusList = new ArrayList<>();
                    accountBalanceTransactionStatusList.add(accountBalanceTransactionStatus.get());
                }
            } else if (accountBalanceTransactionStatusName != null) {
                Optional<AccountBalanceTransactionStatus> accountBalanceTransactionStatus = accountBalanceTransactionStatusService
                        .getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                                accountBalanceTransactionStatusName);
                if (accountBalanceTransactionStatus.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceTransactionStatus ");
                } else {
                    accountBalanceTransactionStatusList = new ArrayList<>();
                    accountBalanceTransactionStatusList.add(accountBalanceTransactionStatus.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented accountBalanceTransactionStatus ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read accountBalanceTransactionStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionStatusList);
        return accountBalanceTransactionStatusList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceTransactionStatus> updateAccountBalanceTransactionStatuses(
            @RequestParam String env,
            @RequestBody AccountBalanceTransactionStatus accountBalanceTransactionStatus,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceTransactionStatus> accountBalanceTransactionStatusList;
        try {
            request.setAttribute("requestBody", accountBalanceTransactionStatus);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTransactionStatus= accountBalanceTransactionStatusService.updateAccountBalanceTransactionStatus(
                    accountBalanceTransactionStatus);
            accountBalanceTransactionStatusList = new ArrayList<>();
            accountBalanceTransactionStatusList.add(accountBalanceTransactionStatus);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update accountBalanceTransactionStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionStatusList);
        return accountBalanceTransactionStatusList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccountBalanceTransactionStatuses(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTransactionStatusId,
            @RequestParam(required = false) String accountBalanceTransactionStatusName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(accountBalanceTransactionStatusId != null) {
                accountBalanceTransactionStatusService.deleteAccountBalanceTransactionStatus(
                        accountBalanceTransactionStatusId);
            } else if (accountBalanceTransactionStatusName != null) {
                accountBalanceTransactionStatusService
                        .deleteAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                                accountBalanceTransactionStatusName);
            } else {
                throw new NotImplementedException("This delete query is not implemented accountBalanceTransactionStatus ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete accountBalanceTransactionStatus ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
