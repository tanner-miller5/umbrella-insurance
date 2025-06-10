package com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingBetState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PendingBetStateServiceImpl implements PendingBetStateService {

    @Autowired
    PendingBetStateRepository pendingBetStateRepository;

    @Override
    public PendingBetState savePendingBetState(PendingBetState pendingBetState) {
        return pendingBetStateRepository.save(pendingBetState);
    }

    @Override
    public List<PendingBetState> getPendingBetStates() {
        return pendingBetStateRepository.findAll();
    }

    @Override
    public PendingBetState updatePendingBetState(PendingBetState pendingBetState) {
        return pendingBetStateRepository.save(pendingBetState);
    }

    @Override
    public void deletePendingBetState(Long pendingBetStateId) {
        pendingBetStateRepository.deleteById(pendingBetStateId);
    }

    @Override
    public Optional<PendingBetState> getPendingBetStateByPendingBetStateName(String pendingBetStateName) {
        return pendingBetStateRepository.getPendingBetStateByPendingBetStateName(pendingBetStateName);
    }

    @Override
    public void deletePendingBetStateByPendingBetStateName(String pendingBetStateName) {
        pendingBetStateRepository.deletePendingBetStateByPendingBetStateName(pendingBetStateName);
    }

    @Override
    public Optional<PendingBetState> getPendingBetStateByPendingBetStateId(Long pendingBetStateId) {
        return pendingBetStateRepository.findById(pendingBetStateId);
    }
}
