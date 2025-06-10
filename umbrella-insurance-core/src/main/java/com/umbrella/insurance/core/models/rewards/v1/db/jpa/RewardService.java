package com.umbrella.insurance.core.models.rewards.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Reward;

import java.util.List;
import java.util.Optional;

public interface RewardService {
    Reward saveReward(Reward reward);
    List<Reward> getRewards();
    Reward updateReward(Reward reward);
    void deleteReward(Long rewardId);
    Optional<Reward> getRewardById(Long rewardId);
    Optional<Reward> getRewardByName(String name);
    void deleteRewardByRewardName(String rewardName);
}
