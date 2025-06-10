package com.umbrella.insurance.core.models.companies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);
    void deleteByCompanyName(String companyName);
}
