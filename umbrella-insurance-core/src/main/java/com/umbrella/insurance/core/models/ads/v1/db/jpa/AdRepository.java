package com.umbrella.insurance.core.models.ads.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.umbrella.insurance.core.models.entities.Ad;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    Optional<Ad> findByAdName(String adName);
    void deleteByAdName(String adName);
}
