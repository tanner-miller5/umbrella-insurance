package com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationPrivilegeServiceImpl implements ApplicationPrivilegeService {
    @Autowired
    ApplicationPrivilegeRepository applicationPrivilegeRepository;

    @Override
    public ApplicationPrivilege saveApplicationPrivilege(ApplicationPrivilege applicationPrivilege) {
        return applicationPrivilegeRepository.save(applicationPrivilege);
    }

    @Override
    public List<ApplicationPrivilege> getApplicationPrivileges() {
        return applicationPrivilegeRepository.findAll();
    }

    @Override
    public ApplicationPrivilege updateApplicationPrivilege(ApplicationPrivilege applicationPrivilege) {
        return applicationPrivilegeRepository.save(applicationPrivilege);
    }

    @Override
    public void deleteApplicationPrivilege(Long applicationPrivilegeId) {
        applicationPrivilegeRepository.deleteById(applicationPrivilegeId);
    }

    @Override
    public void deleteApplicationPrivilegeByApplicationPrivilegeName(String applicationPrivilegeName) {
        applicationPrivilegeRepository.deleteByApplicationPrivilegeName(applicationPrivilegeName);
    }

    @Override
    public Optional<ApplicationPrivilege> findApplicationPrivilegeByApplicationPrivilegeName(String applicationPrivilegeName) {
        return applicationPrivilegeRepository.findApplicationPrivilegeByApplicationPrivilegeName(applicationPrivilegeName);
    }

    @Override
    public Optional<ApplicationPrivilege>  findApplicationPrivilegeById(Long applicationPrivilegeId) {
        return applicationPrivilegeRepository.findById(applicationPrivilegeId);
    }

    @Override
    public List<ApplicationPrivilege> findApplicationPrivilegeByIds(List<Long> applicationPrivilegeIds) {
        return applicationPrivilegeRepository.findAllById(applicationPrivilegeIds);
    }
}
