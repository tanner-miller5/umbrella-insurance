package com.umbrella.insurance.core.models.perils.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Peril;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerilServiceImpl implements PerilService {

    @Autowired
    PerilRepository perilRepository;

    @Override
    public Peril savePeril(Peril peril) {
        return perilRepository.save(peril);
    }

    @Override
    public List<Peril> getPerils() {
        return perilRepository.findAll();
    }

    @Override
    public Peril updatePeril(Peril peril) {
        return perilRepository.save(peril);
    }

    @Override
    public void deletePeril(Long perilId) {
        perilRepository.deleteById(perilId);
    }

    @Override
    public Optional<Peril> getPeril(Long perilId) {
        return perilRepository.findById(perilId);
    }

    @Override
    public Optional<Peril> getPerilByPerilName(String perilName) {
        return perilRepository.findByPerilName(perilName);
    }

    @Override
    public void deletePerilByPerilName(String perilName) {
        perilRepository.deletePerilByPerilName(perilName);
    }
}
