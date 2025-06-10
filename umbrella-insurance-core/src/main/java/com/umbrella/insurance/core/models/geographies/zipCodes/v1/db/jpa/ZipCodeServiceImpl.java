package com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umbrella.insurance.core.models.entities.ZipCode;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZipCodeServiceImpl implements ZipCodeService {

    @Autowired
    ZipCodeRepository zipCodeRepository;

    @Override
    public ZipCode saveZipCode(ZipCode zipCode) {
        return zipCodeRepository.save(zipCode);
    }

    @Override
    public List<ZipCode> getZipCodes() {
        return zipCodeRepository.findAll();
    }

    @Override
    public ZipCode updateZipCode(ZipCode zipCode) {
        return zipCodeRepository.save(zipCode);
    }

    @Override
    public void deleteZipCode(Long zipCodeId) {
        zipCodeRepository.deleteById(zipCodeId);
    }

    @Override
    public Optional<ZipCode> getZipCodeByZipCodeValue(String zipCodeValue) {
        return zipCodeRepository.findByZipCodeValue(zipCodeValue);
    }

    @Override
    public Optional<ZipCode> getZipCodeById(Long zipCodeId) {
        return zipCodeRepository.findById(zipCodeId);
    }

    @Override
    public void deleteZipCodeByZipCodeValue(String zipCodeValue) {
        zipCodeRepository.deleteByZipCodeValue(zipCodeValue);
    }
}
