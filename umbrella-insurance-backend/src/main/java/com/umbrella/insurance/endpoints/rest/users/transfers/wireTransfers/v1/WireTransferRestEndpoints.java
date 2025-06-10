package com.umbrella.insurance.endpoints.rest.users.transfers.wireTransfers.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db.WireTransferPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db.jpa.WireTransferService;
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
@RequestMapping(WireTransferPrivilege.PATH)
public class WireTransferRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(WireTransferRestEndpoints.class);

    @Autowired
    private WireTransferService wireTransferService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    WireTransfer createWireTransfer(
            @RequestParam String env,
            @RequestBody WireTransfer wireTransfer,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        WireTransfer wireTransferResponse;
        try {
            request.setAttribute("requestBody", wireTransfer);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            wireTransferResponse = wireTransferService.saveWireTransfer(wireTransfer);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create wireTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", wireTransferResponse);
        return wireTransferResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<WireTransfer> readWireTransfers(
            @RequestParam String env,
            @RequestParam(required = false) Long wireTransferId,
            @RequestParam(required = false) Long transferId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<WireTransfer> wireTransferList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (wireTransferId != null) {
                Optional<WireTransfer> wireTransfer = wireTransferService.getWireTransferByWireTransferId(
                        wireTransferId);
                if (wireTransfer.isEmpty()) {
                    throw new NotFoundException("Unable to read wireTransfer");
                } else {
                    wireTransferList = new ArrayList<>();
                    wireTransferList.add(wireTransfer.get());
                }
            } else if (transferId != null) {
                Optional<WireTransfer> wireTransfer = wireTransferService
                        .getWireTransferByTransferId(transferId);
                if (wireTransfer.isEmpty()) {
                    throw new NotFoundException("Unable to read wireTransfer");
                } else {
                    wireTransferList = new ArrayList<>();
                    wireTransferList.add(wireTransfer.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented wireTransfer");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read wireTransfer");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", wireTransferList);
        return wireTransferList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<WireTransfer> updateWireTransfers(
            @RequestParam String env,
            @RequestBody WireTransfer wireTransfer,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<WireTransfer> wireTransferList;
        try {
            request.setAttribute("requestBody", wireTransfer);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            wireTransfer = wireTransferService.updateWireTransfer(wireTransfer);
            wireTransferList = new ArrayList<>();
            wireTransferList.add(wireTransfer);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update wireTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", wireTransferList);
        return wireTransferList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteWireTransfers(
            @RequestParam String env,
            @RequestParam(required = false) Long wireTransferId,
            @RequestParam(required = false) Long transferId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(wireTransferId != null) {
                wireTransferService.deleteWireTransfer(wireTransferId);
            } else if (transferId != null) {
                wireTransferService
                        .deleteWireTransferByTransferId(transferId);
            } else {
                throw new NotImplementedException("This delete query is not implemented wireTransfer ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete wireTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
