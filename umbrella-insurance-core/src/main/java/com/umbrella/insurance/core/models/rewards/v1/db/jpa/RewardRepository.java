package com.umbrella.insurance.core.models.rewards.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Optional<Reward> getRewardByRewardName(String name);
    void deleteRewardByRewardName(String name);
}
