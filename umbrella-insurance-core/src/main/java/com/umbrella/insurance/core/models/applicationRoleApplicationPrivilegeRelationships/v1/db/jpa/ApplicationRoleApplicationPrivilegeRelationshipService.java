package com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.ApplicationRoleApplicationPrivilegeRelationship;

public interface ApplicationRoleApplicationPrivilegeRelationshipService {
    ApplicationRoleApplicationPrivilegeRelationship saveApplicationRoleApplicationPrivilegeRelationship(
            ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship);
    List<ApplicationRoleApplicationPrivilegeRelationship> getApplicationRoleApplicationPrivilegeRelationships();
    ApplicationRoleApplicationPrivilegeRelationship updateApplicationRoleApplicationPrivilegeRelationship(
            ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship);
    void deleteApplicationRoleApplicationPrivilegeRelationship(Long applicationRoleApplicationPrivilegeRelationshipId);
    Optional<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegePathAndApplicationPrivilegeMethod(
            Long applicationRoleId, String applicationPrivilegePath, String applicationPrivilegeMethod
    );
    Optional<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeId(
            Long applicationRoleId, Long applicationPrivilegeId
    );
    List<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipsByApplicationRoleId(
            Long applicationRoleId);
    Optional<ApplicationRoleApplicationPrivilegeRelationship> findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId(
            Long applicationRoleApplicationPrivilegeRelationshipId
    );
    void deleteByApplicationRoleIdAndApplicationPrivilegeId(Long applicationRoleId, Long applicationPrivilegeId);
}
