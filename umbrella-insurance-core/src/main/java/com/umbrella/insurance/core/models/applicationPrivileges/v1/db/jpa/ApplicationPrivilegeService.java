package com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;

import java.util.List;
import java.util.Optional;

public interface ApplicationPrivilegeService {
    ApplicationPrivilege saveApplicationPrivilege(ApplicationPrivilege applicationPrivilege);
    List<ApplicationPrivilege> getApplicationPrivileges();
    ApplicationPrivilege updateApplicationPrivilege(ApplicationPrivilege applicationPrivilege);
    void deleteApplicationPrivilege(Long applicationPrivilegeId);
    void deleteApplicationPrivilegeByApplicationPrivilegeName(String applicationPrivilegeName);
    Optional<ApplicationPrivilege> findApplicationPrivilegeByApplicationPrivilegeName(String applicationPrivilege);
    Optional<ApplicationPrivilege> findApplicationPrivilegeById(Long applicationPrivilegeId);
    List<ApplicationPrivilege> findApplicationPrivilegeByIds(List<Long> applicationPrivilegeIds);
}
