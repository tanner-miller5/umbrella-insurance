package com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationRoleServiceImpl implements ApplicationRoleService {
    @Autowired
    ApplicationRoleRepository applicationRoleRepository;

    @Override
    public ApplicationRole saveApplicationRole(ApplicationRole applicationRole) {
        return applicationRoleRepository.save(applicationRole);
    }

    @Override
    public List<ApplicationRole> getApplicationRoles() {
        return applicationRoleRepository.findAll();
    }

    @Override
    public ApplicationRole updateApplicationRole(ApplicationRole applicationRole) {
        return applicationRoleRepository.save(applicationRole);
    }

    @Override
    public void deleteApplicationRole(Long applicationRoleId) {
        applicationRoleRepository.deleteById(applicationRoleId);
    }

    @Override
    public Optional<ApplicationRole> getApplicationRoleByApplicationRoleName(String applicationRoleName) {
        return applicationRoleRepository.findApplicationRolesByApplicationRoleName(applicationRoleName);
    }

    @Override
    public void deleteApplicationRoleByApplicationRoleName(String applicationRoleName) {
        applicationRoleRepository.deleteApplicationRoleByApplicationRoleName(applicationRoleName);
    }

    @Override
    public Optional<ApplicationRole> getApplicationRoleByApplicationRoleId(Long applicationRoleId) {
        return applicationRoleRepository.findById(applicationRoleId);
    }
}
