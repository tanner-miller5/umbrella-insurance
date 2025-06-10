package com.umbrella.insurance.core.models.charities.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Charity;

import java.util.List;
import java.util.Optional;

public interface CharityService {
    Charity saveCharity(Charity charity);
    List<Charity> getCharities();
    Charity updateCharity(Charity charity);
    void deleteCharity(Long charityId);
    Optional<Charity> getCharityByCharityName(String charityName);
    Optional<Charity> getCharityByCharityId(Long charityId);
    void deleteCharityByCharityName(String charityName);

}
