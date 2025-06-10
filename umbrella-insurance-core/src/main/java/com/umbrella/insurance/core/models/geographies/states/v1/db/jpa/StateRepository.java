package com.umbrella.insurance.core.models.geographies.states.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByStateName(String stateName);
    void deleteByStateName(String stateName);
}
