package com.umbrella.insurance.core.models.exchangeRates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ExchangeRate;
import com.umbrella.insurance.core.models.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findExchangeRateByUnit1AndUnit2(Unit unit1, Unit unit2);
    void deleteByUnit1AndUnit2(Unit unit1, Unit unit2);
}
