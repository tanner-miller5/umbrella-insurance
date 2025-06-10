package com.umbrella.insurance.core.models.ads.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Ad;

import java.util.List;
import java.util.Optional;

public interface AdService {
    Ad saveAd(Ad ad);
    List<Ad> getAds();
    Ad updateAd(Ad ad);
    void deleteByAdId(Long adId);
    void deleteByAdName(String adName);
    Optional<Ad> findByAdId(Long adId);
    Optional<Ad> findByAdName(String adName);
}
