package com.umbrella.insurance.core.models.users.bets.betTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.BetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BetTypeServiceImpl implements BetTypeService {
    @Autowired
    BetTypeRepository betTypeRepository;

    @Override
    public BetType saveBetType(BetType betType) {
        return betTypeRepository.save(betType);
    }

    @Override
    public List<BetType> getBetTypes() {
        return betTypeRepository.findAll();
    }

    @Override
    public BetType updateBetType(BetType betType) {
        return betTypeRepository.save(betType);
    }

    @Override
    public void deleteBetType(Long betTypeId) {
        betTypeRepository.deleteById(betTypeId);
    }

    @Override
    public Optional<BetType> getBetTypeByBetTypeName(String betTypeName) {
        return betTypeRepository.findByBetTypeName(betTypeName);
    }

    @Override
    public Optional<BetType> getBetTypeByBetTypeId(Long betTypeId) {
        return betTypeRepository.findById(betTypeId);
    }

    @Override
    public void deleteBetTypeByBetTypeName(String betTypeName) {
        betTypeRepository.deleteBetTypeByBetTypeName(betTypeName);
    }
}
