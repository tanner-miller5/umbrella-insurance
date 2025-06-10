package com.umbrella.insurance.endpoints.rest.people.employees.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.employees.v1.db.EmployeePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.people.employees.v1.db.jpa.EmployeeService;
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
@RequestMapping(EmployeePrivilege.PATH)
public class EmployeeRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestEndpoints.class);

    @Autowired
    private EmployeeService employeeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Employee createEmployee(
            @RequestParam String env,
            @RequestBody Employee employee,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Employee employeeResponse;
        try {
            request.setAttribute("requestBody", employee);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            employeeResponse = employeeService.saveEmployee(employee);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create employee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", employeeResponse);
        return employeeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Employee> readEmployees(
            @RequestParam String env,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Employee> employeeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (employeeId != null) {
                Optional<Employee> employee = employeeService.getEmployee(
                        employeeId);
                if (employee.isEmpty()) {
                    throw new NotFoundException("Unable to read employee ");
                } else {
                    employeeList = new ArrayList<>();
                    employeeList.add(employee.get());
                }
            } else if (personId != null) {
                Optional<Employee> employee = employeeService
                        .getEmployeeByPersonId(personId);
                if (employee.isEmpty()) {
                    throw new NotFoundException("Unable to read employee ");
                } else {
                    employeeList = new ArrayList<>();
                    employeeList.add(employee.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented employee ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read employee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", employeeList);
        return employeeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Employee> updateEmployees(
            @RequestParam String env,
            @RequestBody Employee employee,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Employee> employeeList;
        try {
            request.setAttribute("requestBody", employee);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            employee = employeeService.updateEmployee(
                    employee);
            employeeList = new ArrayList<>();
            employeeList.add(employee);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update employee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", employeeList);
        return employeeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmployees(
            @RequestParam String env,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(employeeId != null) {
                employeeService.deleteEmployee(
                        employeeId);
            } else if (personId != null) {
                employeeService
                        .deleteEmployeeByPersonId(
                                personId);
            } else {
                throw new NotImplementedException("This delete query is not implemented employee ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete employee ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
