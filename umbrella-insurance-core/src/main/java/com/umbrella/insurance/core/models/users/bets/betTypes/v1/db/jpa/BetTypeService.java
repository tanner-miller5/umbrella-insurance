package com.umbrella.insurance.core.models.users.bets.betTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.BetType;

import java.util.List;
import java.util.Optional;

public interface BetTypeService {
    BetType saveBetType(BetType betType);
    List<BetType> getBetTypes();
    BetType updateBetType(BetType betType);
    void deleteBetType(Long betTypeId);
    Optional<BetType> getBetTypeByBetTypeName(String betTypeName);
    Optional<BetType> getBetTypeByBetTypeId(Long betTypeId);
    void deleteBetTypeByBetTypeName(String betTypeName);
}
