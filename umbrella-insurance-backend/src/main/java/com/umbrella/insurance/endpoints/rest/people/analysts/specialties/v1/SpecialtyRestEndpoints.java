package com.umbrella.insurance.endpoints.rest.people.analysts.specialties.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.SpecialtyPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
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
@RequestMapping(SpecialtyPrivilege.PATH)
public class SpecialtyRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(SpecialtyRestEndpoints.class);

    @Autowired
    private SpecialtyService specialtyService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Specialty createSpecialty(
            @RequestParam String env,
            @RequestBody Specialty specialty,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Specialty specialtyResponse;
        try {
            request.setAttribute("requestBody", specialty);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            specialtyResponse = specialtyService.saveSpecialty(specialty);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create specialty ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", specialtyResponse);
        return specialtyResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Specialty> readSpecialties(
            @RequestParam String env,
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String specialtyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Specialty> specialtyList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (specialtyId != null) {
                Optional<Specialty> specialty = specialtyService.getSpecialtyById(
                        specialtyId);
                if (specialty.isEmpty()) {
                    throw new NotFoundException("Unable to read specialty ");
                } else {
                    specialtyList = new ArrayList<>();
                    specialtyList.add(specialty.get());
                }
            } else if (specialtyName != null) {
                Optional<Specialty> specialty = specialtyService
                        .getSpecialtyBySpecialtyName(specialtyName);
                if (specialty.isEmpty()) {
                    throw new NotFoundException("Unable to read specialty ");
                } else {
                    specialtyList = new ArrayList<>();
                    specialtyList.add(specialty.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented specialty ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read specialty ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", specialtyList);
        return specialtyList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Specialty> updateSpecialties(
            @RequestParam String env,
            @RequestBody Specialty specialty,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Specialty> specialtyList;
        try {
            request.setAttribute("requestBody", specialty);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            specialty = specialtyService.updateSpecialty(
                    specialty);
            specialtyList = new ArrayList<>();
            specialtyList.add(specialty);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update specialty ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", specialtyList);
        return specialtyList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSpecialties(
            @RequestParam String env,
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String specialtyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(specialtyId != null) {
                specialtyService.deleteSpecialty(
                        specialtyId);
            } else if (specialtyName != null) {
                specialtyService
                        .deleteBySpecialtyName(
                                specialtyName);
            } else {
                throw new NotImplementedException("This delete query is not implemented specialty ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete specialty ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
