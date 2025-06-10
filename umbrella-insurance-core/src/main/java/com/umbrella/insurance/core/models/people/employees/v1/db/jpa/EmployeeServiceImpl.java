package com.umbrella.insurance.core.models.people.employees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Optional<Employee> getEmployeeByPersonId(Long personId) {
        return employeeRepository.getEmployeeByPersonId(personId);
    }

    @Override
    public void deleteEmployeeByPersonId(Long personId) {
        employeeRepository.deleteEmployeeByPersonId(personId);
    }
}
