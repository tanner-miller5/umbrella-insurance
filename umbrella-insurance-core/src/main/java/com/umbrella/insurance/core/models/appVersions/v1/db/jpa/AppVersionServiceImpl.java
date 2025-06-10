package com.umbrella.insurance.core.models.appVersions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppVersionServiceImpl implements AppVersionService {
    @Autowired
    AppVersionRepository appVersionRepository;

    @Override
    public AppVersion saveAppVersion(AppVersion appVersion) {
        return appVersionRepository.save(appVersion);
    }

    @Override
    public List<AppVersion> getAppVersions() {
        return appVersionRepository.findAll();
    }

    @Override
    public AppVersion updateAppVersion(AppVersion appVersion) {
        return appVersionRepository.save(appVersion);
    }

    @Override
    public void deleteAppVersion(Long appVersionId) {
        appVersionRepository.deleteById(
                appVersionId);
    }

    @Override
    public Optional<AppVersion> getAppVersionByAppName(String appName) {
        return appVersionRepository.findByAppName(appName);
    }

    @Override
    public Optional<AppVersion> getAppVersionById(Long appVersionId) {
        return appVersionRepository.findById(appVersionId);
    }

    @Override
    public Optional<AppVersion> getAppVersionBySessionId(Long sessionId) {
        return appVersionRepository.findBySessionId(sessionId);
    }

    @Override
    public void deleteAppVersionByAppName(String appVersionName) {
        appVersionRepository.deleteAppVersionByAppName(appVersionName);
    }
}
