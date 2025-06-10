package com.umbrella.insurance.core.models.users.loanPayments.v1.db;

import com.umbrella.insurance.core.models.companies.v1.db.jpa.CompanyService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Company;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoanPaymentsTableTests {
    private static String companyName = "1234";
    private static String updatedCompanyName = "12345";
    private static Company company = new Company();
    private static Company updatedCompany = new Company();
    static {
        company.setCompanyName(companyName);

        updatedCompany.setCompanyName(updatedCompanyName);

    }

    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();

    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Autowired
    private CompanyService companyService;

    //@Test
    //@Order(2)
    void insertCompanyTest() throws SQLException {
        company = companyService.saveCompany(company);
    }
    //@Test
    //@Order(3)
    void selectCompanyByCompanyNameTest() throws SQLException {
        Company company1 = companyService.getCompanyByCompanyName(companyName).get();
        assertTrue(company1.getCompanyName().equals(companyName));
    }
    //@Test
    //@Order(4)
    void updateCompanyByCompanyNameTest() throws SQLException {
        updatedCompany = companyService.updateCompany(updatedCompany);
    }
    //@Test
    //@Order(5)
    void selectUpdatedCompanyByCompanyNameTest() throws SQLException {
        Company company1 = companyService.getCompanyByCompanyName(updatedCompanyName).get();
        assertTrue(company1.getCompanyName().equals(updatedCompanyName));
    }
    //@Test
    //@Order(6)
    void deleteCompanyByCompanyNameTest() throws SQLException {
        companyService.deleteCompany(updatedCompany.getId());
    }

}
