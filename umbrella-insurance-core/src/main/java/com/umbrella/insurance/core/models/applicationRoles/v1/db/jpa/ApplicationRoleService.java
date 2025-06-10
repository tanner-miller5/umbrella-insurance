package com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationRole;

import java.util.List;
import java.util.Optional;

public interface ApplicationRoleService {
    ApplicationRole saveApplicationRole(ApplicationRole applicationRole);
    List<ApplicationRole> getApplicationRoles();
    ApplicationRole updateApplicationRole(ApplicationRole applicationRole);
    void deleteApplicationRole(Long applicationRoleId);
    Optional<ApplicationRole> getApplicationRoleByApplicationRoleName(String applicationRoleName);
    void deleteApplicationRoleByApplicationRoleName(String applicationRoleName);
    Optional<ApplicationRole> getApplicationRoleByApplicationRoleId(Long applicationRoleId);
}
