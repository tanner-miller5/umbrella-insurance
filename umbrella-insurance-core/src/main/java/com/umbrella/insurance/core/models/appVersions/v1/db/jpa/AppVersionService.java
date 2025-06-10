package com.umbrella.insurance.core.models.appVersions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AppVersion;

import java.util.List;
import java.util.Optional;

public interface AppVersionService {
    AppVersion saveAppVersion(AppVersion appVersionRecord);
    List<AppVersion> getAppVersions();
    AppVersion updateAppVersion(AppVersion appVersionRecord);
    void deleteAppVersion(Long appVersionId);
    Optional<AppVersion> getAppVersionByAppName(String appVersionName);
    Optional<AppVersion> getAppVersionById(Long appVersionId);
    Optional<AppVersion> getAppVersionBySessionId(Long sessionId);
    void deleteAppVersionByAppName(String appVersionName);
}
