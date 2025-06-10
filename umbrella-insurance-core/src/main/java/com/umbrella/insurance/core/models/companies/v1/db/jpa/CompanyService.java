package com.umbrella.insurance.core.models.companies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company saveCompany(Company company);
    List<Company> getCompanies();
    Company updateCompany(Company company);
    void deleteCompany(Long companyId);
    Optional<Company> getCompany(Long companyId);
    Optional<Company> getCompanyByCompanyName(String companyName);
    void deleteCompanyByCompanyName(String companyName);
}
