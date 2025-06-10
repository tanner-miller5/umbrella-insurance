package com.umbrella.insurance.core.models.users.fees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Fee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeeServiceImpl implements FeeService {
    @Autowired
    FeeRepository feeRepository;

    @Override
    public Fee saveFee(Fee fee) {
        return feeRepository.save(fee);
    }

    @Override
    public List<Fee> getFees() {
        return feeRepository.findAll();
    }

    @Override
    public Fee updateFee(Fee fee) {
        return feeRepository.save(fee);
    }

    @Override
    public void deleteFee(Long feeId) {
        feeRepository.deleteById(feeId);
    }

    @Override
    public Optional<Fee> getFeeByFeeName(String feeName) {
        return feeRepository.getFeeByFeeName(feeName);
    }

    @Override
    public Optional<Fee> getFeeById(Long feeId) {
        return feeRepository.findById(feeId);
    }

    @Override
    public void deleteFeeByFeeName(String feeName) {
        feeRepository.deleteFeeByFeeName(feeName);
    }
}
