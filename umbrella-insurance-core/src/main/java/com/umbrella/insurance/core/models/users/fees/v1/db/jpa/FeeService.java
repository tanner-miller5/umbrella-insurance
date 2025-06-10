package com.umbrella.insurance.core.models.users.fees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Fee;

import java.util.List;
import java.util.Optional;

public interface FeeService {
    Fee saveFee(Fee fee);
    List<Fee> getFees();
    Fee updateFee(Fee fee);
    void deleteFee(Long feeId);
    Optional<Fee> getFeeByFeeName(String feeName);
    Optional<Fee> getFeeById(Long feeId);
    void deleteFeeByFeeName(String feeName);
}
