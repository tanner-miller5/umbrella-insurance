package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rewards", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "reward_name", columnNames = {"reward_name"})
})
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "reward_name", nullable = false, length = Integer.MAX_VALUE)
    private String rewardName;

}