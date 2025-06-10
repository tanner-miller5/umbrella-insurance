package com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ZipCode;

import java.util.List;
import java.util.Optional;

public interface ZipCodeService {
    ZipCode saveZipCode(ZipCode zipCode);
    List<ZipCode> getZipCodes();
    ZipCode updateZipCode(ZipCode zipCode);
    void deleteZipCode(Long zipCodeId);
    Optional<ZipCode> getZipCodeByZipCodeValue(String zipCodeValue);
    Optional<ZipCode> getZipCodeById(Long zipCodeId);
    void deleteZipCodeByZipCodeValue(String zipCodeValue);
}
