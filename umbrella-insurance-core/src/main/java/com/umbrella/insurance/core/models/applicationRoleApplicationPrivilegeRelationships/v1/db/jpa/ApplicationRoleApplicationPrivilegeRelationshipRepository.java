package com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationRoleApplicationPrivilegeRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRoleApplicationPrivilegeRelationshipRepository extends JpaRepository<ApplicationRoleApplicationPrivilegeRelationship, Long> {
    Optional<ApplicationRoleApplicationPrivilegeRelationship> findByApplicationRoleIdAndApplicationPrivilegePathAndApplicationPrivilegeMethod(
            Long applicationRoleId, String applicationPrivilegePath, String applicationPrivilegeMethod
    );

    Optional<ApplicationRoleApplicationPrivilegeRelationship> findByApplicationRoleIdAndApplicationPrivilegeId(
            Long applicationRoleId, Long applicationPrivilegeId
    );

    List<ApplicationRoleApplicationPrivilegeRelationship> findByApplicationRoleId(Long applicationRoleId);

    void deleteByApplicationRoleIdAndApplicationPrivilegeId(Long applicationRoleId,
                                                            Long applicationPrivilegeId);
}
