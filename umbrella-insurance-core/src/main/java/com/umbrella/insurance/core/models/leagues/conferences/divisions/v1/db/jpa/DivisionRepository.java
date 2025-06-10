package com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findByConferenceIdAndDivisionName(Long conferenceId, String divisionName);
    void deleteByConferenceIdAndDivisionName(Long conferenceId, String divisionName);
}
