package com.umbrella.insurance.core.models.people.coaches.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Coach;

import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> getCoachById(Long coachId);
    Optional<Coach> getCoachByPersonId(Long personId);
    void deleteCoachByPersonId(Long personId);
}
