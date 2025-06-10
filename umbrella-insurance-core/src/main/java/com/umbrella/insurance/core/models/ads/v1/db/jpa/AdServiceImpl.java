package com.umbrella.insurance.core.models.ads.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Ad;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdServiceImpl implements AdService {

    @Autowired
    AdRepository adRepository;

    @Override
    public Ad saveAd(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public List<Ad> getAds() {
        return adRepository.findAll();
    }

    @Override
    public Ad updateAd(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public void deleteByAdId(Long adId) {
        adRepository.deleteById(adId);
    }

    @Override
    public void deleteByAdName(String adName) {
        adRepository.deleteByAdName(adName);
    }

    @Override
    public Optional<Ad> findByAdId(Long adId) {
        return adRepository.findById(adId);
    }

    @Override
    public Optional<Ad> findByAdName(String adName) {
        return adRepository.findByAdName(adName);
    }

}
