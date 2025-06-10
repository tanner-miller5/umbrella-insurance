package com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Division;

import java.util.List;
import java.util.Optional;

public interface DivisionService {
    Division saveDivision(Division division);
    List<Division> getDivisions();
    Division updateDivision(Division division);
    void deleteDivision(Long divisionId);
    Optional<Division> getDivisionById(Long divisionId);
    Optional<Division> getDivisionByConferenceIdAndDivisionName(Long conferenceId, String divisionName);
    void deleteDivisionByConferenceIdAndDivisionName(Long conferenceId, String divisionName);

}
