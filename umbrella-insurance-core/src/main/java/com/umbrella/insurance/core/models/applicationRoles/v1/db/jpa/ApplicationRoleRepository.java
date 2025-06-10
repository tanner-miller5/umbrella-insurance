package com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole, Long> {
    Optional<ApplicationRole> findApplicationRolesByApplicationRoleName(String applicationRoleName);
    void deleteApplicationRoleByApplicationRoleName(String applicationRoleName);
}
