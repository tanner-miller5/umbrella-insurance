package com.umbrella.insurance.endpoints.rest.users.accountBalances.accountBalanceTransactions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.AccountBalanceTransactionPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa.AccountBalanceTransactionService;
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
@RequestMapping(AccountBalanceTransactionPrivilege.PATH)
public class AccountBalanceTransactionRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceTransactionRestEndpoints.class);

    @Autowired
    private AccountBalanceTransactionService accountBalanceTransactionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AccountBalanceTransaction createAccountBalanceTransaction(
            @RequestParam String env,
            @RequestBody AccountBalanceTransaction accountBalanceTransaction,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        AccountBalanceTransaction accountBalanceTransactionResponse;
        try {
            request.setAttribute("requestBody", accountBalanceTransaction);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTransactionResponse = accountBalanceTransactionService.saveAccountBalanceTransaction(accountBalanceTransaction);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create accountBalanceTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionResponse);
        return accountBalanceTransactionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceTransaction> readAccountBalanceTransactions(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTransactionId,
            @RequestParam(required = false) String accountBalanceTransactionName,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceTransaction> accountBalanceTransactionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (accountBalanceTransactionId != null) {
                Optional<AccountBalanceTransaction> accountBalanceTransaction = accountBalanceTransactionService
                        .getAccountBalanceTransactionByAccountBalanceTransactionId(
                        accountBalanceTransactionId);
                if (accountBalanceTransaction.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceTransaction ");
                } else {
                    accountBalanceTransactionList = new ArrayList<>();
                    accountBalanceTransactionList.add(accountBalanceTransaction.get());
                }
            } else if (accountBalanceTransactionName != null) {
                Optional<AccountBalanceTransaction> accountBalanceTransaction = accountBalanceTransactionService
                        .getAccountBalanceTransactionByAccountBalanceTransactionName(accountBalanceTransactionName);
                if (accountBalanceTransaction.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceTransaction ");
                } else {
                    accountBalanceTransactionList = new ArrayList<>();
                    accountBalanceTransactionList.add(accountBalanceTransaction.get());
                }
            } else if (userId != null) {
                accountBalanceTransactionList = accountBalanceTransactionService.getAccountBalanceTransactionsByUserId(userId);
            } else {
                throw new NotImplementedException("This read query is not implemented accountBalanceTransaction ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read accountBalanceTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionList);
        return accountBalanceTransactionList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceTransaction> updateAccountBalanceTransactions(
            @RequestParam String env,
            @RequestBody AccountBalanceTransaction accountBalanceTransaction,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceTransaction> accountBalanceTransactionList;
        try {
            request.setAttribute("requestBody", accountBalanceTransaction);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTransaction = accountBalanceTransactionService.updateAccountBalanceTransaction(
                    accountBalanceTransaction);
            accountBalanceTransactionList = new ArrayList<>();
            accountBalanceTransactionList.add(accountBalanceTransaction);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update accountBalanceTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTransactionList);
        return accountBalanceTransactionList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccountBalanceTransactions(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTransactionId,
            @RequestParam(required = false) String accountBalanceTransactionName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(accountBalanceTransactionId != null) {
                accountBalanceTransactionService.deleteAccountBalanceTransaction(
                        accountBalanceTransactionId);
            } else if (accountBalanceTransactionName != null) {
                accountBalanceTransactionService
                        .deleteAccountBalanceTransactionByAccountBalanceTransactionName(
                                accountBalanceTransactionName);
            } else {
                throw new NotImplementedException("This delete query is not implemented accountBalanceTransaction ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete accountBalanceTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
