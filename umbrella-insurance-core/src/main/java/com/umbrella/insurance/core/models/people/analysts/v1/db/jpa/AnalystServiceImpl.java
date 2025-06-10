package com.umbrella.insurance.core.models.people.analysts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Analyst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnalystServiceImpl implements AnalystService {
    @Autowired
    AnalystRepository analystRepository;

    @Override
    public Analyst saveAnalyst(Analyst analyst) {
        return analystRepository.save(analyst);
    }

    @Override
    public List<Analyst> getAnalysts() {
        return analystRepository.findAll();
    }

    @Override
    public Analyst updateAnalyst(Analyst analyst) {
        return analystRepository.save(analyst);
    }

    @Override
    public void deleteAnalyst(Long analystId) {
        analystRepository.deleteById(analystId);
    }

    @Override
    public Optional<Analyst> getAnalystById(Long analystId) {
        return analystRepository.findById(analystId);
    }

    @Override
    public Optional<Analyst> getAnalystByPersonId(Long personId) {
        return analystRepository.findAnalystByPersonId(personId);
    }

    @Override
    public void deleteAnalystByPersonId(Long personId) {
        analystRepository.deleteAnalystByPersonId(personId);
    }
}
