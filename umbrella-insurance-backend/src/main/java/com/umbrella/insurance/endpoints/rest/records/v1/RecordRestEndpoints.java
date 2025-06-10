package com.umbrella.insurance.endpoints.rest.records.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.records.v1.db.RecordPrivilege;
import com.umbrella.insurance.core.models.records.v1.db.jpa.RecordService;
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

import com.umbrella.insurance.core.models.entities.Record;

@Controller
@RequestMapping(RecordPrivilege.PATH)
public class RecordRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(RecordRestEndpoints.class);

    @Autowired
    private RecordService recordService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Record createRecord(
            @RequestParam String env,
            @RequestBody Record record,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Record recordResponse;
        try {
            request.setAttribute("requestBody", record);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            recordResponse = recordService.saveRecord(record);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", recordResponse);
        return recordResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Record> readRecords(
            @RequestParam String env,
            @RequestParam(required = false) Long recordId,
            @RequestParam(required = false) String recordName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Record> recordList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (recordId != null) {
                Optional<Record> record = recordService.getRecordById(
                        recordId);
                if (record.isEmpty()) {
                    throw new NotFoundException("Unable to read record record");
                } else {
                    recordList = new ArrayList<>();
                    recordList.add(record.get());
                }
            } else if (recordName != null) {
                Optional<Record> record = recordService
                        .getRecordByRecordName(
                                recordName);
                if (record.isEmpty()) {
                    throw new NotFoundException("Unable to read record");
                } else {
                    recordList = new ArrayList<>();
                    recordList.add(record.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented record record");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", recordList);
        return recordList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Record> updateRecords(
            @RequestParam String env,
            @RequestBody Record record,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Record> recordRecordList;
        try {
            request.setAttribute("requestBody", record);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            record = recordService.updateRecord(
                    record);
            recordRecordList = new ArrayList<>();
            recordRecordList.add(record);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update record record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", recordRecordList);
        return recordRecordList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRecordRecords(
            @RequestParam String env,
            @RequestParam(required = false) Long recordId,
            @RequestParam(required = false) String recordName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(recordId != null) {
                recordService.deleteRecord(
                        recordId);
            } else if (recordName != null) {
                recordService
                        .deleteRecordByRecordName(
                                recordName);
            } else {
                throw new NotImplementedException("This delete query is not implemented record record");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete record record");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
