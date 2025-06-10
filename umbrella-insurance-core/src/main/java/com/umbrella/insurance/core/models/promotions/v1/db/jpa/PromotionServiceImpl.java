package com.umbrella.insurance.core.models.promotions.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.Promotion;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Override
    public Promotion savePromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(Long promotionId) {
        promotionRepository.deleteById(promotionId);
    }

    @Override
    public Optional<Promotion> getPromotion(Long promotionId) {
        return promotionRepository.findById(promotionId);
    }

    @Override
    public Optional<Promotion> getPromotionByPromotionName(String promotionName) {
        return promotionRepository.getPromotionByPromotionName(promotionName);
    }

    @Override
    public void deletePromotionByPromotionName(String promotionName) {
        promotionRepository.deletePromotionByPromotionName(promotionName);
    }
}
