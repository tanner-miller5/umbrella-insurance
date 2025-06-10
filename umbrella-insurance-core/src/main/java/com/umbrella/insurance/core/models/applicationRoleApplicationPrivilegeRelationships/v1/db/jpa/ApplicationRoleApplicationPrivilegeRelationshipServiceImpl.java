package com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationRoleApplicationPrivilegeRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationRoleApplicationPrivilegeRelationshipServiceImpl implements ApplicationRoleApplicationPrivilegeRelationshipService {
    @Autowired
    ApplicationRoleApplicationPrivilegeRelationshipRepository applicationRoleApplicationPrivilegeRelationshipRepository;

    @Override
    public ApplicationRoleApplicationPrivilegeRelationship saveApplicationRoleApplicationPrivilegeRelationship(
            ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship) {
        return applicationRoleApplicationPrivilegeRelationshipRepository.save(applicationRoleApplicationPrivilegeRelationship);
    }

    @Override
    public List<ApplicationRoleApplicationPrivilegeRelationship> getApplicationRoleApplicationPrivilegeRelationships() {
        return applicationRoleApplicationPrivilegeRelationshipRepository.findAll();
    }

    @Override
    public ApplicationRoleApplicationPrivilegeRelationship updateApplicationRoleApplicationPrivilegeRelationship(
            ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship) {
        return applicationRoleApplicationPrivilegeRelationshipRepository.save(applicationRoleApplicationPrivilegeRelationship);
    }

    @Override
    public void deleteApplicationRoleApplicationPrivilegeRelationship(Long applicationRoleApplicationPrivilegeRelationshipId) {
        applicationRoleApplicationPrivilegeRelationshipRepository.deleteById(applicationRoleApplicationPrivilegeRelationshipId);
    }

    @Override
    public Optional<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegePathAndApplicationPrivilegeMethod(
            Long applicationRoleId, String applicationPrivilegePath, String applicationPrivilegeMethod) {
        return applicationRoleApplicationPrivilegeRelationshipRepository.findByApplicationRoleIdAndApplicationPrivilegePathAndApplicationPrivilegeMethod(
                applicationRoleId, applicationPrivilegePath, applicationPrivilegeMethod
        );
    }

    @Override
    public Optional<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeId(Long applicationRoleId, Long applicationPrivilegeId) {
        return applicationRoleApplicationPrivilegeRelationshipRepository.findByApplicationRoleIdAndApplicationPrivilegeId(
                applicationRoleId, applicationPrivilegeId
        );
    }

    @Override
    public List<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipsByApplicationRoleId(
            Long applicationRoleId) {
        return applicationRoleApplicationPrivilegeRelationshipRepository.findByApplicationRoleId(applicationRoleId);
    }

    @Override
    public Optional<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId(Long applicationRoleApplicationPrivilegeRelationshipId) {
        return applicationRoleApplicationPrivilegeRelationshipRepository.findById(applicationRoleApplicationPrivilegeRelationshipId);
    }

    @Override
    public void deleteByApplicationRoleIdAndApplicationPrivilegeId(Long applicationRoleId, Long applicationPrivilegeId) {
        applicationRoleApplicationPrivilegeRelationshipRepository.deleteByApplicationRoleIdAndApplicationPrivilegeId(
                applicationRoleId, applicationPrivilegeId
        );
    }
}
