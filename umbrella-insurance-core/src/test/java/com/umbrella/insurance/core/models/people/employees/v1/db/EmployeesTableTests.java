package com.umbrella.insurance.core.models.people.employees.v1.db;

import com.umbrella.insurance.core.models.entities.Employee;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.employees.v1.db.jpa.EmployeeService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeesTableTests {
    private static Person person = new Person();
    private static Employee employee = new Employee();
    private static Employee updatedEmployee = new Employee();
    static {

        person.setFirstName("test1");
        person.setMiddleName("test2");
        person.setSurname("test3");
        person.setDateOfBirth(Date.valueOf("2000-01-01"));
        person.setSsn("1234567890");
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
    private PersonService personService;

    @Autowired
    private EmployeeService employeeService;


    @Test
    @Order(4)
    void insertEmployeeTest() throws SQLException {
        person = personService.savePerson(person);
        employee.setPerson(person);
        updatedEmployee.setPerson(person);

        employee = employeeService.saveEmployee(employee);
        updatedEmployee.setId(employee.getId());
    }
    @Test
    @Order(5)
    void selectEmployeeByPersonIdTest() throws SQLException {
        Employee employee1 = employeeService.getEmployeeByPersonId(
                person.getId()).get();
        assertTrue(employee1.getPerson().getId().equals(person.getId()));
    }
    @Test
    @Order(6)
    void updateEmployeeByPersonIdTest() throws SQLException {
        updatedEmployee = employeeService.updateEmployee(
                updatedEmployee);
    }
    @Test
    @Order(7)
    void selectUpdatedEmployeeByPersonIdTest() throws SQLException {
        Employee employee1 = employeeService.getEmployeeByPersonId(
                person.getId()).get();
        assertTrue(employee1.getPerson().getId().equals(person.getId()));
    }
    @Test
    @Order(8)
    void deleteEmployeeByEmployeeNameTest() throws SQLException {
        employeeService.deleteEmployee(
                updatedEmployee.getId());

        personService.deletePerson(
                person.getId());
    }
}
