package com.umbrella.insurance.core.models.rosters.v1.db.jpa;


import com.umbrella.insurance.core.models.entities.Roster;

import java.util.List;
import java.util.Optional;

public interface RosterService {
    Roster saveRoster(Roster roster);
    List<Roster> getRosters();
    Roster updateRoster(Roster roster);
    void deleteRoster(Long rosterId);
    Optional<Roster> getRosterById(Long rosterId);
    Optional<Roster> getRosterByRosterName(String rosterName);
    void deleteRosterByRosterName(String rosterName);
}
