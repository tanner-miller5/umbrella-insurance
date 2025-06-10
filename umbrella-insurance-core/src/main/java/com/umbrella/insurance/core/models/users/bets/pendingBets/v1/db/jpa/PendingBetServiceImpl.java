package com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingBet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PendingBetServiceImpl implements PendingBetService {

    @Autowired
    PendingBetRepository pendingBetRepository;

    @Override
    public PendingBet savePendingBet(PendingBet pendingBet) {
        return pendingBetRepository.save(pendingBet);
    }

    @Override
    public List<PendingBet> getPendingBets() {
        return pendingBetRepository.findAll();
    }

    @Override
    public PendingBet updatePendingBet(PendingBet pendingBet) {
        return pendingBetRepository.save(pendingBet);
    }

    @Override
    public void deletePendingBet(Long pendingBetId) {
        pendingBetRepository.deleteById(pendingBetId);
    }

    @Override
    public Optional<PendingBet> getPendingBetByPendingBetName(String pendingBetName) {
        return pendingBetRepository.findPendingBetByPendingBetName(pendingBetName);
    }

    @Override
    public Optional<PendingBet> getPendingBetByPendingBetId(Long pendingBetId) {
        return pendingBetRepository.findById(pendingBetId);
    }

    @Override
    public void deletePendingBetByPendingBetName(String pendingBetName) {
        pendingBetRepository.deletePendingBetByPendingBetName(pendingBetName);
    }
}
