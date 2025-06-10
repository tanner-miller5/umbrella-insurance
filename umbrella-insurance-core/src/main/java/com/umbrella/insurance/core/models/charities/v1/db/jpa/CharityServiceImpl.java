package com.umbrella.insurance.core.models.charities.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Charity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharityServiceImpl implements CharityService {
    @Autowired
    CharityRepository charityRepository;

    @Override
    public Charity saveCharity(Charity charity) {
        return charityRepository.save(charity);
    }

    @Override
    public List<Charity> getCharities() {
        return charityRepository.findAll();
    }

    @Override
    public Charity updateCharity(Charity charity) {
        return charityRepository.save(charity);
    }

    @Override
    public void deleteCharity(Long charityId) {
        charityRepository.deleteById(charityId);
    }

    @Override
    public Optional<Charity> getCharityByCharityName(String charityName) {
        return charityRepository.getCharityByCharityName(charityName);
    }

    @Override
    public Optional<Charity> getCharityByCharityId(Long charityId) {
        return charityRepository.findById(charityId);
    }

    @Override
    public void deleteCharityByCharityName(String charityName) {
        charityRepository.deleteCharityByCharityName(charityName);
    }
}
