package com.umbrella.insurance.core.models.people.coaches.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {
    @Autowired
    CoachRepository coachRepository;

    @Override
    public Coach saveCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public List<Coach> getCoaches() {
        return coachRepository.findAll();
    }

    @Override
    public Coach updateCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public void deleteCoach(Long coachId) {
        coachRepository.deleteById(coachId);
    }

    @Override
    public Optional<Coach> getCoachById(Long coachId) {
        return coachRepository.getCoachById(coachId);
    }

    @Override
    public void deleteCoachByPersonId(Long personId) {
        coachRepository.deleteCoachByPersonId(personId);
    }

    @Override
    public Optional<Coach> getCoachByPersonId(Long personId) {
        return coachRepository.getCoachByPersonId(personId);
    }
}
