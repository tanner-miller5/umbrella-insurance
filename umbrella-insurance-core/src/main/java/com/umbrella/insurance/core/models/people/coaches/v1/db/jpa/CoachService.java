package com.umbrella.insurance.core.models.people.coaches.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Coach;

import java.util.List;
import java.util.Optional;

public interface CoachService {
    Coach saveCoach(Coach coach);
    List<Coach> getCoaches();
    Coach updateCoach(Coach coach);
    void deleteCoach(Long coachId);
    Optional<Coach> getCoachById(Long coachId);
    void deleteCoachByPersonId(Long personId);
    Optional<Coach> getCoachByPersonId(Long personId);
}
