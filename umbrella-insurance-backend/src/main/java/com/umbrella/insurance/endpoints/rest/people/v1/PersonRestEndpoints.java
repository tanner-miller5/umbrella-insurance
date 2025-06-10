package com.umbrella.insurance.endpoints.rest.people.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.v1.db.PersonPrivilege;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
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
@RequestMapping(PersonPrivilege.PATH)
public class PersonRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PersonRestEndpoints.class);

    @Autowired
    private PersonService personService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Person createPerson(
            @RequestParam String env,
            @RequestBody Person person,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Person personResponse;
        try {
            request.setAttribute("requestBody", person);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            personResponse = personService.savePerson(person);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create person ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", personResponse);
        return personResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Person> readPersons(
            @RequestParam String env,
            @RequestParam(required = false) Long personId,
            @RequestParam(required = false) String ssn,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Person> personList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (personId != null) {
                Optional<Person> person = personService.getPersonById(
                        personId);
                if (person.isEmpty()) {
                    throw new NotFoundException("Unable to read person ");
                } else {
                    personList = new ArrayList<>();
                    personList.add(person.get());
                }
            } else if (ssn != null) {
                Optional<Person> person = personService
                        .getPersonBySsn(ssn);
                if (person.isEmpty()) {
                    throw new NotFoundException("Unable to read person ");
                } else {
                    personList = new ArrayList<>();
                    personList.add(person.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented person ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read person ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", personList);
        return personList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Person> updatePersons(
            @RequestParam String env,
            @RequestBody Person person,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Person> personList;
        try {
            request.setAttribute("requestBody", person);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            person = personService.updatePerson(
                    person);
            personList = new ArrayList<>();
            personList.add(person);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update person ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", personList);
        return personList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePersons(
            @RequestParam String env,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(personId != null) {
                personService.deletePerson(
                        personId);
            } else {
                throw new NotImplementedException("This delete query is not implemented person ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete person ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
