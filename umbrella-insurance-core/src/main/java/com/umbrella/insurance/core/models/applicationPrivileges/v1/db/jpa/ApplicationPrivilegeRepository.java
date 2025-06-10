package com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationPrivilegeRepository extends JpaRepository<ApplicationPrivilege, Long> {
    @Query(value = "SELECT * FROM application_privileges a WHERE a.application_privilege_name = :applicationPrivilege", nativeQuery = true)
    Optional<ApplicationPrivilege> findApplicationPrivilegeByApplicationPrivilegeName(String applicationPrivilege);
    void deleteByApplicationPrivilegeName(String applicationPrivilege);
}
