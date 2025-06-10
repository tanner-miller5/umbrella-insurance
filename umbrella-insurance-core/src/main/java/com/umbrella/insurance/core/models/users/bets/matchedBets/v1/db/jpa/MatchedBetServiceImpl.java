package com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedBet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatchedBetServiceImpl implements MatchedBetService {
    @Autowired
    MatchedBetRepository matchedBetRepository;

    @Override
    public MatchedBet saveMatchedBet(MatchedBet matchedBet) {
        return matchedBetRepository.save(matchedBet);
    }

    @Override
    public List<MatchedBet> getMatchedBets() {
        return matchedBetRepository.findAll();
    }

    @Override
    public MatchedBet updateMatchedBet(MatchedBet matchedBet) {
        return matchedBetRepository.save(matchedBet);
    }

    @Override
    public void deleteMatchedBet(Long matchedBetId) {
        matchedBetRepository.deleteById(matchedBetId);
    }

    @Override
    public Optional<MatchedBet> getMatchedBetByPendingBetId1(Long pendingBet1Id) {
        return matchedBetRepository.findByPendingBet1Id(pendingBet1Id);
    }

    @Override
    public Optional<MatchedBet> getMatchedBetByMatchedBetId(Long matchedBetId) {
        return matchedBetRepository.findById(matchedBetId);
    }

    @Override
    public void deleteMatchedBetByPendingBetId(Long pendingBet1Id) {
        matchedBetRepository.deleteByPendingBet1Id(pendingBet1Id);
    }
}
