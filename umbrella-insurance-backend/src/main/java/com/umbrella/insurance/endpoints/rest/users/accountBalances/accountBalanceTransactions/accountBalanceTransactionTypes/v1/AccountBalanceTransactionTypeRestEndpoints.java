package com.umbrella.insurance.endpoints.rest.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.AccountBalanceTransactionTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa.AccountBalanceTransactionTypeService;
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
@RequestMapping(AccountBalanceTransactionTypePrivilege.PATH)
public class AccountBalanceTransactionTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceTransactionTypeRestEndpoints.class);

    @Autowired
    private AccountBalanceTransactionTypeService accountBalanceTransactionTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AccountBalanceTransactionType createAccountBalanceTransactionType(
            @RequestParam String env,
            @RequestBody AccountBalanceTransactionType accountBalanceTransactionType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        AccountBalanceTransactionType accountBalanceTransactionTypeResponse;
        try {
            request.setAttribute("requestBody", accountBalanceTransactionType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTransactionTypeResponse = accountBalanceTransactionTypeService.saveAccountBalanceTransactionType(
                    accountBalanceTransactionType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create accountBalanceTransactionType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionTypeResponse);
        return accountBalanceTransactionTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceTransactionType> readAccountBalanceTransactionTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTransactionTypeId,
            @RequestParam(required = false) String accountBalanceTransactionTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceTransactionType> accountBalanceTransactionTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (accountBalanceTransactionTypeId != null) {
                Optional<AccountBalanceTransactionType> accountBalanceTransactionType = accountBalanceTransactionTypeService
                        .getAccountBalanceTransactionTypeById(
                        accountBalanceTransactionTypeId);
                if (accountBalanceTransactionType.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceTransactionType");
                } else {
                    accountBalanceTransactionTypeList = new ArrayList<>();
                    accountBalanceTransactionTypeList.add(accountBalanceTransactionType.get());
                }
            } else if (accountBalanceTransactionTypeName != null) {
                Optional<AccountBalanceTransactionType> accountBalanceTransactionType = accountBalanceTransactionTypeService
                        .getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(accountBalanceTransactionTypeName);
                if (accountBalanceTransactionType.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceTransactionType");
                } else {
                    accountBalanceTransactionTypeList = new ArrayList<>();
                    accountBalanceTransactionTypeList.add(accountBalanceTransactionType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented accountBalanceTransactionType");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read accountBalanceTransactionType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionTypeList);
        return accountBalanceTransactionTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceTransactionType> updateAccountBalanceTransactionTypes(
            @RequestParam String env,
            @RequestBody AccountBalanceTransactionType accountBalanceTransactionType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceTransactionType> accountBalanceTransactionTypeList;
        try {
            request.setAttribute("requestBody", accountBalanceTransactionType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTransactionType = accountBalanceTransactionTypeService.updateAccountBalanceTransactionType(
                    accountBalanceTransactionType);
            accountBalanceTransactionTypeList = new ArrayList<>();
            accountBalanceTransactionTypeList.add(accountBalanceTransactionType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update accountBalanceTransactionType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionTypeList);
        return accountBalanceTransactionTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccountBalanceTransactionTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTransactionTypeId,
            @RequestParam(required = false) String accountBalanceTransactionTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(accountBalanceTransactionTypeId != null) {
                accountBalanceTransactionTypeService.deleteAccountBalanceTransactionType(
                        accountBalanceTransactionTypeId);
            } else if (accountBalanceTransactionTypeName != null) {
                accountBalanceTransactionTypeService
                        .deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                                accountBalanceTransactionTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented accountBalanceTransactionType");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete accountBalanceTransactionType");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
