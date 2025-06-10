package com.umbrella.insurance.core.models.people.referees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {
    Optional<Referee> findByPersonId(Long personId);
    void deleteByPersonId(Long personId);
}
