package com.umbrella.insurance.core.models.people.employees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getEmployees();
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long employeeId);
    Optional<Employee> getEmployee(Long employeeId);
    Optional<Employee> getEmployeeByPersonId(Long personId);
    void deleteEmployeeByPersonId(Long personId);
}
