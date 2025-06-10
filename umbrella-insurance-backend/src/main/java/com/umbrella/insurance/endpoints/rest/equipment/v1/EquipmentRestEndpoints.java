package com.umbrella.insurance.endpoints.rest.equipment.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.equipment.v1.db.EquipmentPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.equipment.v1.db.jpa.EquipmentService;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
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
@RequestMapping(EquipmentPrivilege.PATH)
public class EquipmentRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(EquipmentRestEndpoints.class);

    @Autowired
    private EquipmentService equipmentService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Equipment createEquipment(
            @RequestParam String env,
            @RequestBody Equipment equipment,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Equipment equipmentResponse;
        try {
            request.setAttribute("requestBody", equipment);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            equipmentResponse = equipmentService.saveEquipment(equipment);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create equipment ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", equipmentResponse);
        return equipmentResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Equipment> readEquipments(
            @RequestParam String env,
            @RequestParam(required = false) Long equipmentId,
            @RequestParam(required = false) String equipmentName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Equipment> equipmentList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (equipmentId != null) {
                Optional<Equipment> equipment = equipmentService.getEquipmentById(equipmentId);
                if(equipment.isEmpty()) {
                    throw new NotFoundException("Unable to find equipment");
                } else {
                    equipmentList = new ArrayList<>();
                    equipmentList.add(equipment.get());
                }
            } else if (equipmentName != null) {
                Optional<Equipment> equipment = equipmentService.getEquipmentByEquipmentName(
                        equipmentName);
                if(equipment.isEmpty()) {
                    throw new NotFoundException("Unable to find equipment");
                } else {
                    equipmentList = new ArrayList<>();
                    equipmentList.add(equipment.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented equipment ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read equipment ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", equipmentList);
        return equipmentList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Equipment> updateEquipments(
            @RequestParam String env,
            @RequestBody Equipment equipment,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Equipment> equipmentList;
        try {
            request.setAttribute("requestBody", equipment);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            equipment = equipmentService.updateEquipment(
                    equipment);
            equipmentList = new ArrayList<>();
            equipmentList.add(equipment);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update equipment ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", equipmentList);
        return equipmentList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEquipments(@RequestParam String env,
                                @RequestParam(required = false) Long equipmentId,
                                @RequestParam(required = false) String equipmentName,
                                @RequestAttribute BigInteger currentRequestNumber,
                                ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(equipmentId != null) {
                equipmentService.deleteEquipment(equipmentId);
            } else if (equipmentName != null) {
                equipmentService.deleteByEquipmentName(equipmentName);
            } else {
                throw new NotImplementedException("This delete query is not implemented equipment ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete equipment ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
