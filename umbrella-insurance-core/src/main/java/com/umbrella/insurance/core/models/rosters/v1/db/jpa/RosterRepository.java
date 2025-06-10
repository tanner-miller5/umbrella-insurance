package com.umbrella.insurance.core.models.rosters.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Roster;

import java.util.Optional;

@Repository
public interface RosterRepository extends JpaRepository<Roster, Long> {
    Optional<Roster> findByRosterName(String rosterName);
    void deleteByRosterName(String rosterName);
}
