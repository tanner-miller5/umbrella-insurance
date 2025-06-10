package com.umbrella.insurance.endpoints.rest.companies.v1;

import com.umbrella.insurance.core.models.companies.v1.db.CompanyPrivilege;
import com.umbrella.insurance.core.models.companies.v1.db.jpa.CompanyService;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
@RequestMapping(CompanyPrivilege.PATH)
public class CompanyRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CompanyRestEndpoints.class);

    @Autowired
    private CompanyService companyService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Company createCompany(
            @RequestParam String env,
            @RequestBody Company company,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Company companyResponse;
        try {
            request.setAttribute("requestBody", company);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            companyResponse = companyService.saveCompany(company);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create company ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", companyResponse);
        return companyResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Company> readCompanies(
            @RequestParam String env,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String companyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Company> companyList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (companyId != null) {
                Optional<Company> company = companyService.getCompany(companyId);
                if(company.isEmpty()) {
                    throw new NotFoundException("Unable to find company");
                } else {
                    companyList = new ArrayList<>();
                    companyList.add(company.get());
                }
            } else if (companyName != null) {
                Optional<Company> company = companyService.getCompanyByCompanyName(
                        companyName);
                if(company.isEmpty()) {
                    throw new NotFoundException("Unable to find company");
                } else {
                    companyList = new ArrayList<>();
                    companyList.add(company.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented company ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read company ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", companyList);
        return companyList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Company> updateCompanies(
            @RequestParam String env,
            @RequestBody Company company,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Company> companyList;
        try {
            request.setAttribute("requestBody", company);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            company = companyService.updateCompany(company);
            companyList = new ArrayList<>();
            companyList.add(company);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update company ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", companyList);
        return companyList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompanies(@RequestParam String env,
                              @RequestParam(required = false) Long companyId,
                              @RequestParam(required = false) String companyName,
                              @RequestAttribute BigInteger currentRequestNumber,
                              ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(companyId != null) {
                companyService.deleteCompany(companyId);
            } else if (companyName != null) {
                companyService.deleteCompanyByCompanyName(companyName);
            } else {
                throw new NotImplementedException("This delete query is not implemented company ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete company ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
