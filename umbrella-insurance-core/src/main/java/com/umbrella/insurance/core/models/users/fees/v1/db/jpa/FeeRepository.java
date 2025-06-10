package com.umbrella.insurance.core.models.users.fees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {
    Optional<Fee> getFeeByFeeName(String feeName);
    void deleteFeeByFeeName(String feeName);
}
