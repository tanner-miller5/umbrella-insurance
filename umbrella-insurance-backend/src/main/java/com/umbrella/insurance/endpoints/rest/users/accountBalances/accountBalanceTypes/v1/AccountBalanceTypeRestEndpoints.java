package com.umbrella.insurance.endpoints.rest.users.accountBalances.accountBalanceTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.AccountBalanceTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa.AccountBalanceTypeService;
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
@RequestMapping(AccountBalanceTypePrivilege.PATH)
public class AccountBalanceTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceTypeRestEndpoints.class);

    @Autowired
    private AccountBalanceTypeService accountBalanceTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AccountBalanceType createAccountBalanceType(
            @RequestParam String env,
            @RequestBody AccountBalanceType accountBalanceType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        AccountBalanceType accountBalanceTypeResponse;
        try {
            request.setAttribute("requestBody", accountBalanceType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceTypeResponse = accountBalanceTypeService.saveAccountBalanceType(accountBalanceType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create accountBalanceType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTypeResponse);
        return accountBalanceTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceType> readAccountBalanceTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTypeId,
            @RequestParam(required = false) String accountBalanceTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceType> accountBalanceTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (accountBalanceTypeId != null) {
                Optional<AccountBalanceType> accountBalanceType = accountBalanceTypeService.findAccountBalanceTypeById(
                        accountBalanceTypeId);
                if (accountBalanceType.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceType ");
                } else {
                    accountBalanceTypeList = new ArrayList<>();
                    accountBalanceTypeList.add(accountBalanceType.get());
                }
            } else if (accountBalanceTypeName != null) {
                Optional<AccountBalanceType> accountBalanceType = accountBalanceTypeService
                        .findAccountBalanceTypeByAccountBalanceTypeName(accountBalanceTypeName);
                if (accountBalanceType.isEmpty()) {
                    throw new NotFoundException("Unable to read accountBalanceType ");
                } else {
                    accountBalanceTypeList = new ArrayList<>();
                    accountBalanceTypeList.add(accountBalanceType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented accountBalanceType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read accountBalanceType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTypeList);
        return accountBalanceTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AccountBalanceType> updateAccountBalanceTypes(
            @RequestParam String env,
            @RequestBody AccountBalanceType accountBalanceType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<AccountBalanceType> accountBalanceTypeList;
        try {
            request.setAttribute("requestBody", accountBalanceType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            accountBalanceType = accountBalanceTypeService.updateAccountBalanceType(
                    accountBalanceType);
            accountBalanceTypeList = new ArrayList<>();
            accountBalanceTypeList.add(accountBalanceType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update accountBalanceType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", accountBalanceTypeList);
        return accountBalanceTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccountBalanceTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long accountBalanceTypeId,
            @RequestParam(required = false) String accountBalanceTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(accountBalanceTypeId != null) {
                accountBalanceTypeService.deleteAccountBalanceType(
                        accountBalanceTypeId);
            } else if (accountBalanceTypeName != null) {
                accountBalanceTypeService
                        .deleteAccountBalanceTypeByAccountBalanceTypeName(
                                accountBalanceTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented accountBalanceType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete accountBalanceType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
