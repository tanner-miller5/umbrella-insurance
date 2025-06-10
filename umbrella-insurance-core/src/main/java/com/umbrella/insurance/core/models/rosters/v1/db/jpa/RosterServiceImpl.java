package com.umbrella.insurance.core.models.rosters.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.Roster;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RosterServiceImpl implements RosterService {
    @Autowired
    RosterRepository rosterRepository;

    @Override
    public Roster saveRoster(Roster roster) {
        return rosterRepository.save(roster);
    }

    @Override
    public List<Roster> getRosters() {
        return rosterRepository.findAll();
    }

    @Override
    public Roster updateRoster(Roster roster) {
        return rosterRepository.save(roster);
    }

    @Override
    public void deleteRoster(Long rosterId) {
        rosterRepository.deleteById(rosterId);
    }

    @Override
    public Optional<Roster> getRosterById(Long rosterId) {
        return rosterRepository.findById(rosterId);
    }

    @Override
    public Optional<Roster> getRosterByRosterName(String rosterName) {
        return rosterRepository.findByRosterName(rosterName);
    }

    @Override
    public void deleteRosterByRosterName(String rosterName) {
        rosterRepository.deleteByRosterName(rosterName);
    }
}
