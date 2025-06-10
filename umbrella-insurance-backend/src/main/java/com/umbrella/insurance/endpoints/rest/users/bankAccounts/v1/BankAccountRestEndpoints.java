package com.umbrella.insurance.endpoints.rest.users.bankAccounts.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bankAccounts.v1.db.BankAccountPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bankAccounts.v1.db.jpa.BankAccountService;
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
@RequestMapping(BankAccountPrivilege.PATH)
public class BankAccountRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountRestEndpoints.class);

    @Autowired
    private BankAccountService bankAccountService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    BankAccount createBankAccount(
            @RequestParam String env,
            @RequestBody BankAccount bankAccount,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        BankAccount bankAccountResponse;
        try {
            request.setAttribute("requestBody", bankAccount);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            bankAccountResponse = bankAccountService.saveBankAccount(bankAccount);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create bankAccount ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", bankAccountResponse);
        return bankAccountResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<BankAccount> readBankAccounts(
            @RequestParam String env,
            @RequestParam(required = false) Long bankAccountId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<BankAccount> bankAccountList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (bankAccountId != null) {
                Optional<BankAccount> bankAccount = bankAccountService.getBankAccountById(
                        bankAccountId);
                if (bankAccount.isEmpty()) {
                    throw new NotFoundException("Unable to read bankAccount ");
                } else {
                    bankAccountList = new ArrayList<>();
                    bankAccountList.add(bankAccount.get());
                }
            } else if (userId != null) {
                Optional<BankAccount> bankAccount = bankAccountService
                        .getBankAccountByUserId(userId);
                if (bankAccount.isEmpty()) {
                    throw new NotFoundException("Unable to read bankAccount ");
                } else {
                    bankAccountList = new ArrayList<>();
                    bankAccountList.add(bankAccount.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented bankAccount ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read bankAccount ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", bankAccountList);
        return bankAccountList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<BankAccount> updateBankAccounts(
            @RequestParam String env,
            @RequestBody BankAccount bankAccount,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<BankAccount> bankAccountList;
        try {
            request.setAttribute("requestBody", bankAccount);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            bankAccount = bankAccountService.updateBankAccount(
                    bankAccount);
            bankAccountList = new ArrayList<>();
            bankAccountList.add(bankAccount);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update bankAccount ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", bankAccountList);
        return bankAccountList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBankAccounts(
            @RequestParam String env,
            @RequestParam(required = false) Long bankAccountId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(bankAccountId != null) {
                bankAccountService.deleteBankAccount(
                        bankAccountId);
            } else if (userId != null) {
                bankAccountService
                        .deleteBankAccountByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented bankAccount ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete bankAccount ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
