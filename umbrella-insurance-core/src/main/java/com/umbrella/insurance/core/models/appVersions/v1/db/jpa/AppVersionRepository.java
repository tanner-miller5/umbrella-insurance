package com.umbrella.insurance.core.models.appVersions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
    Optional<AppVersion> findByAppName(String appName);
    Optional<AppVersion> findBySessionId(Long sessionId);
    void deleteAppVersionByAppName(String appName);
}
