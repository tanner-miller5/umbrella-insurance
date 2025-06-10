package com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    DivisionRepository divisionRepository;

    @Override
    public Division saveDivision(Division division) {
        return divisionRepository.save(division);
    }

    @Override
    public List<Division> getDivisions() {
        return divisionRepository.findAll();
    }

    @Override
    public Division updateDivision(Division division) {
        return divisionRepository.save(division);
    }

    @Override
    public void deleteDivision(Long divisionId) {
        divisionRepository.deleteById(divisionId);
    }

    @Override
    public Optional<Division> getDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId);
    }

    @Override
    public Optional<Division> getDivisionByConferenceIdAndDivisionName(Long conferenceId, String divisionName) {
        return divisionRepository.findByConferenceIdAndDivisionName(conferenceId, divisionName);
    }

    @Override
    public void deleteDivisionByConferenceIdAndDivisionName(Long conferenceId, String divisionName) {
        divisionRepository.deleteByConferenceIdAndDivisionName(conferenceId, divisionName);
    }
}
