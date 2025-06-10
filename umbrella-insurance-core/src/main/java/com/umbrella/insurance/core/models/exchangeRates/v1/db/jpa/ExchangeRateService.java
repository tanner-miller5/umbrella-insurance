package com.umbrella.insurance.core.models.exchangeRates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ExchangeRate;
import com.umbrella.insurance.core.models.entities.Unit;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateService {
    ExchangeRate saveExchangeRate(ExchangeRate exchangeRateRecord);
    List<ExchangeRate> getExchangeRates();
    ExchangeRate updateExchangeRate(ExchangeRate exchangeRate);
    void deleteExchangeRate(Long exchangeRateId);
    Optional<ExchangeRate> getExchangeRateByUnit1AndUnit2(Unit unit1, Unit unit2);
    Optional<ExchangeRate> getExchangeRateByExchangeRateId(Long exchangeRateId);
    void deleteExchangeRateByUnit1AndUnit2(Unit unit1, Unit unit2);

}
