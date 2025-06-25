package com.umbrella.insurance.endpoints.soa.pendingPolicySoa.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.units.v1.UnitEnum;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.AccountBalanceTransactionStatusEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa.AccountBalanceTransactionStatusService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.AccountBalanceTransactionTypeEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa.AccountBalanceTransactionTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa.AccountBalanceTransactionService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.AccountBalanceTypeEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa.AccountBalanceTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa.AccountBalanceService;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.PendingPolicyTypeEnum;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.PendingPolicyPrivilege;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa.PendingPolicyService;
import com.umbrella.insurance.endpoints.rest.users.policies.pendingPolicies.v1.PendingPolicyRestEndpoints;
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
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping(PendingPolicyPrivilege.SOA_PATH)
public class PendingPolicySoaEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(PendingPolicyRestEndpoints.class);

    @Autowired
    private PendingPolicyService pendingPolicyService;

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Autowired
    private AccountBalanceTypeService accountBalanceTypeService;

    @Autowired
    private AccountBalanceTransactionService accountBalanceTransactionService;

    @Autowired
    private AccountBalanceTransactionTypeService accountBalanceTransactionTypeService;

    @Autowired
    private AccountBalanceTransactionStatusService accountBalanceTransactionStatusService;

    @Autowired
    private UnitService unitService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PendingPolicy createPendingPolicy(
            @RequestParam String env,
            @RequestBody PendingPolicy pendingPolicy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PendingPolicy pendingPolicyResponse;
        try {
            Optional<AccountBalanceType> accountBalanceType = accountBalanceTypeService.findAccountBalanceTypeByAccountBalanceTypeName(
                    AccountBalanceTypeEnum.balance.name());
            if(accountBalanceType.isEmpty()) {
                throw new Exception("Unable to find account balance type");
            }
            Optional<Unit> unit = unitService.getUnitByUnitName(UnitEnum.butter_bucks.name());
            if(unit.isEmpty()) {
                throw new Exception("Unable to find unit");
            }
            Optional<AccountBalance> accountBalance = accountBalanceService.getAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                    pendingPolicy.getSession().getUser().getId(), accountBalanceType.get().getId(),
                    unit.get().getId());
            if(accountBalance.isEmpty()) {
                throw new Exception("Unable to find account balance");
            }
            Optional<AccountBalanceTransactionStatus> accountBalanceTransactionStatus = accountBalanceTransactionStatusService.getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
                    AccountBalanceTransactionStatusEnum.PENDING.name());
            if(accountBalanceTransactionStatus.isEmpty()) {
                throw new Exception("Unable to find account balance transaction status");
            }
            Optional<AccountBalanceTransactionType> accountBalanceTransactionType = accountBalanceTransactionTypeService.getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                    AccountBalanceTransactionTypeEnum.ESCROW_IN.name());
            if(accountBalanceTransactionType.isEmpty()) {
                throw new Exception("Unable to find account balance transaction type");
            }
            if(pendingPolicy.getPendingPolicyType().equals(PendingPolicyTypeEnum.INSURER) &&
                    accountBalance.get().getAccountBalanceValue() < pendingPolicy.getCoverageAmount()) {
                throw new Exception("Insufficient account balance");
            } else if(pendingPolicy.getPendingPolicyType().equals(PendingPolicyTypeEnum.INSURER)){
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                accountBalance.get().setAccountBalanceValue(accountBalance.get().getAccountBalanceValue() -
                        pendingPolicy.getCoverageAmount());
                accountBalance.get().setUpdatedDateTime(timestamp);
                AccountBalanceTransaction accountBalanceTransaction = new AccountBalanceTransaction();
                accountBalanceTransaction.setCreatedDateTime(timestamp);
                accountBalanceTransaction.setAmount(pendingPolicy.getCoverageAmount());
                accountBalanceTransaction.setUnit(unit.get());
                accountBalanceTransaction.setAccountBalanceTransactionName(timestamp.toString());
                accountBalanceTransaction.setAccountBalanceTransactionType(accountBalanceTransactionType.get());
                accountBalanceTransaction.setAccountBalanceTransactionStatus(accountBalanceTransactionStatus.get());
                accountBalanceTransaction = accountBalanceTransactionService.saveAccountBalanceTransaction(accountBalanceTransaction);
            } else if(pendingPolicy.getPendingPolicyType().equals(PendingPolicyTypeEnum.INSURED) &&
                    accountBalance.get().getAccountBalanceValue() < pendingPolicy.getPremiumAmount()) {
                throw new Exception("Insufficient account balance");
            } else if(pendingPolicy.getPendingPolicyType().equals(PendingPolicyTypeEnum.INSURED)) {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                accountBalance.get().setAccountBalanceValue(accountBalance.get().getAccountBalanceValue() -
                        pendingPolicy.getPremiumAmount());
                accountBalance.get().setUpdatedDateTime(timestamp);
                AccountBalanceTransaction accountBalanceTransaction = new AccountBalanceTransaction();
                accountBalanceTransaction.setCreatedDateTime(timestamp);
                accountBalanceTransaction.setAmount(pendingPolicy.getCoverageAmount());
                accountBalanceTransaction.setUnit(unit.get());
                accountBalanceTransaction.setAccountBalanceTransactionName(timestamp.toString());
                accountBalanceTransaction.setAccountBalanceTransactionType(accountBalanceTransactionType.get());
                accountBalanceTransaction.setAccountBalanceTransactionStatus(accountBalanceTransactionStatus.get());
                accountBalanceTransaction = accountBalanceTransactionService.saveAccountBalanceTransaction(accountBalanceTransaction);
            }
            
            request.setAttribute("requestBody", pendingPolicy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicyResponse = pendingPolicyService.savePendingPolicy(pendingPolicy);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create pendingPolicy");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyResponse);
        return pendingPolicyResponse;
    }
}
