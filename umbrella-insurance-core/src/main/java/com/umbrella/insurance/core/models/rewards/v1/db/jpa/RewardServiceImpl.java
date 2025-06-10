package com.umbrella.insurance.core.models.rewards.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardRepository rewardRepository;

    @Override
    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    @Override
    public List<Reward> getRewards() {
        return rewardRepository.findAll();
    }

    @Override
    public Reward updateReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    @Override
    public void deleteReward(Long rewardId) {
        rewardRepository.deleteById(rewardId);
    }

    @Override
    public Optional<Reward> getRewardById(Long rewardId) {
        return rewardRepository.findById(rewardId);
    }

    @Override
    public Optional<Reward> getRewardByName(String name) {
        return rewardRepository.getRewardByRewardName(name);
    }

    @Override
    public void deleteRewardByRewardName(String rewardName) {
        rewardRepository.deleteRewardByRewardName(rewardName);
    }
}
