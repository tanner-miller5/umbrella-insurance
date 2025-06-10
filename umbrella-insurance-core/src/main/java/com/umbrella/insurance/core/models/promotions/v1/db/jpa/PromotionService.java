package com.umbrella.insurance.core.models.promotions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Promotion savePromotion(Promotion promotion);
    List<Promotion> getPromotions();
    Promotion updatePromotion(Promotion promotion);
    void deletePromotion(Long promotionId);
    Optional<Promotion> getPromotion(Long promotionId);
    Optional<Promotion> getPromotionByPromotionName(String promotionName);
    void deletePromotionByPromotionName(String promotionName);
}
