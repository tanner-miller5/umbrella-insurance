package com.umbrella.insurance.core.models.promotions.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.umbrella.insurance.core.models.entities.Promotion;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> getPromotionByPromotionName(String promotionName);
    void deletePromotionByPromotionName(String promotionName);
}
