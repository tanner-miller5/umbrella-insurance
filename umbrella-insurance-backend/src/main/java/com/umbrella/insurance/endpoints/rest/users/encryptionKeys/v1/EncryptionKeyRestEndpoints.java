package com.umbrella.insurance.endpoints.rest.users.encryptionKeys.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.EncryptionKeyPrivilege;
import com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.jpa.EncryptionKeyService;
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

import com.umbrella.insurance.core.models.entities.*;

@Controller
@RequestMapping(EncryptionKeyPrivilege.PATH)
public class EncryptionKeyRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionKeyRestEndpoints.class);

    @Autowired
    private EncryptionKeyService encryptionKeyService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    EncryptionKey createEncryptionKey(
            @RequestParam String env,
            @RequestBody EncryptionKey encryptionKey,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        EncryptionKey encryptionKeyResponse;
        try {
            request.setAttribute("requestBody", encryptionKey);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            encryptionKeyResponse = encryptionKeyService.saveEncryptionKey(encryptionKey);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create encryptionKey ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", encryptionKeyResponse);
        return encryptionKeyResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EncryptionKey> readEncryptionKeys(
            @RequestParam String env,
            @RequestParam(required = false) Long encryptionKeyId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<EncryptionKey> encryptionKeyList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (encryptionKeyId != null) {
                Optional<EncryptionKey> encryptionKey = encryptionKeyService.getEncryptionKeyByEncryptionKeyId(
                        encryptionKeyId);
                if (encryptionKey.isEmpty()) {
                    throw new NotFoundException("Unable to read encryptionKey ");
                } else {
                    encryptionKeyList = new ArrayList<>();
                    encryptionKeyList.add(encryptionKey.get());
                }
            } else if (userId != null) {
                Optional<EncryptionKey> encryptionKey = encryptionKeyService
                        .getEncryptionKeyByUserId(userId);
                if (encryptionKey.isEmpty()) {
                    throw new NotFoundException("Unable to read encryptionKey ");
                } else {
                    encryptionKeyList = new ArrayList<>();
                    encryptionKeyList.add(encryptionKey.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented encryptionKey");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read encryptionKey ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", encryptionKeyList);
        return encryptionKeyList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EncryptionKey> updateEncryptionKeys(
            @RequestParam String env,
            @RequestBody EncryptionKey encryptionKey,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<EncryptionKey> encryptionKeyList;
        try {
            request.setAttribute("requestBody", encryptionKey);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            encryptionKey = encryptionKeyService.updateEncryptionKey(
                    encryptionKey);
            encryptionKeyList = new ArrayList<>();
            encryptionKeyList.add(encryptionKey);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update encryptionKey ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", encryptionKeyList);
        return encryptionKeyList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEncryptionKeys(
            @RequestParam String env,
            @RequestParam(required = false) Long encryptionKeyId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(encryptionKeyId != null) {
                encryptionKeyService.deleteEncryptionKey(encryptionKeyId);
            } else if (userId != null) {
                encryptionKeyService.deleteEncryptionKeyByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented encryptionKey ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete encryptionKey ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
