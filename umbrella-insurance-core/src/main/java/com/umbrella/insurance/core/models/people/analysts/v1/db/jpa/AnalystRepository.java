package com.umbrella.insurance.core.models.people.analysts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Analyst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnalystRepository extends JpaRepository<Analyst, Long> {
    Optional<Analyst> findAnalystByPersonId(Long personId);
    void deleteAnalystByPersonId(Long personId);
}
