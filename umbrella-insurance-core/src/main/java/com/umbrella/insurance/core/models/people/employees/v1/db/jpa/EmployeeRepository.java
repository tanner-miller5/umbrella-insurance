package com.umbrella.insurance.core.models.people.employees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> getEmployeeByPersonId(Long personId);
    void deleteEmployeeByPersonId(Long personId);
}
