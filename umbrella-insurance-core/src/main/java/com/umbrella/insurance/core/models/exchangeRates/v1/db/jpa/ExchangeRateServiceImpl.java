package com.umbrella.insurance.core.models.exchangeRates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ExchangeRate;
import com.umbrella.insurance.core.models.entities.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Override
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public void deleteExchangeRate(Long exchangeRateId) {
        exchangeRateRepository.deleteById(exchangeRateId);
    }

    @Override
    public Optional<ExchangeRate> getExchangeRateByUnit1AndUnit2(Unit unit1, Unit unit2) {
        return exchangeRateRepository.findExchangeRateByUnit1AndUnit2(unit1, unit2);
    }

    @Override
    public Optional<ExchangeRate> getExchangeRateByExchangeRateId(Long exchangeRateId) {
        return exchangeRateRepository.findById(exchangeRateId);
    }

    @Override
    public void deleteExchangeRateByUnit1AndUnit2(Unit unit1, Unit unit2) {
        exchangeRateRepository.deleteByUnit1AndUnit2(unit1, unit2);
    }
}
