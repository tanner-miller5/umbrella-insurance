package com.umbrella.insurance.core.models.charities.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {
    Optional<Charity> getCharityByCharityName(String charityName);
    void deleteCharityByCharityName(String charityName);
}
