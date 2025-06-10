package com.umbrella.insurance.endpoints.rest.users.transfers.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.transfers.v1.db.TransferPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.transfers.v1.db.jpa.TransferService;
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
@RequestMapping(TransferPrivilege.PATH)
public class TransferRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TransferRestEndpoints.class);

    @Autowired
    private TransferService transferService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Transfer createTransfer(
            @RequestParam String env,
            @RequestBody Transfer transfer,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Transfer transferResponse;
        try {
            request.setAttribute("requestBody", transfer);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            transferResponse = transferService.saveTransfer(transfer);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create transfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", transferResponse);
        return transferResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Transfer> readTransfers(
            @RequestParam String env,
            @RequestParam(required = false) Long transferId,
            @RequestParam(required = false) String transferName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Transfer> transferList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (transferId != null) {
                Optional<Transfer> transfer = transferService.getTransferByTransferId(
                        transferId);
                if (transfer.isEmpty()) {
                    throw new NotFoundException("Unable to read transfer");
                } else {
                    transferList = new ArrayList<>();
                    transferList.add(transfer.get());
                }
            } else if (transferName != null) {
                Optional<Transfer> transfer = transferService
                        .getTransferByTransferName(transferName);
                if (transfer.isEmpty()) {
                    throw new NotFoundException("Unable to read transfer");
                } else {
                    transferList = new ArrayList<>();
                    transferList.add(transfer.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented transfer ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read transfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", transferList);
        return transferList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Transfer> updateTransfers(
            @RequestParam String env,
            @RequestBody Transfer transfer,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Transfer> transferList;
        try {
            request.setAttribute("requestBody", transfer);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            transfer = transferService.updateTransfer(transfer);
            transferList = new ArrayList<>();
            transferList.add(transfer);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update transfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", transferList);
        return transferList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTransfers(
            @RequestParam String env,
            @RequestParam(required = false) Long transferId,
            @RequestParam(required = false) String transferName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(transferId != null) {
                transferService.deleteTransfer(transferId);
            } else if (transferName != null) {
                transferService.deleteCardTransferByTransferName(transferName);
            } else {
                throw new NotImplementedException("This delete query is not implemented transfer ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete transfer ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
