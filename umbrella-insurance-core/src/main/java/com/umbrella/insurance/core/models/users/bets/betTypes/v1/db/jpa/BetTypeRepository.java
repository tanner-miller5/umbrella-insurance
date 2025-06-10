package com.umbrella.insurance.core.models.users.bets.betTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.BetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BetTypeRepository extends JpaRepository<BetType, Long> {
    Optional<BetType> findByBetTypeName(String betTypeName);
    void deleteBetTypeByBetTypeName(String betTypeName);
}
