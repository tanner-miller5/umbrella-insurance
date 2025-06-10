package com.umbrella.insurance.core.models.perils.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Peril;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerilRepository extends JpaRepository<Peril, Long> {
    Optional<Peril> findByPerilName(String perilName);
    void deletePerilByPerilName(String perilName);
}
