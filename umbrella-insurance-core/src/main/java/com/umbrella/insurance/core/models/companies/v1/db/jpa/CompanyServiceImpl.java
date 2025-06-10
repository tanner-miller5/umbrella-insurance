package com.umbrella.insurance.core.models.companies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    @Override
    public Optional<Company> getCompany(Long companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public Optional<Company> getCompanyByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    @Override
    public void deleteCompanyByCompanyName(String companyName) {
        companyRepository.deleteByCompanyName(companyName);
    }
}
