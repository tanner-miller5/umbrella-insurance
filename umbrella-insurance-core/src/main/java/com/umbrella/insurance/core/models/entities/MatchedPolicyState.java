package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matched_policy_states", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "matched_policy_states_unique", columnNames = {"matched_policy_state_name"})
})
public class MatchedPolicyState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matched_policy_state_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "matched_policy_state_name", nullable = false, length = Integer.MAX_VALUE)
    private String matchedPolicyStateName;

}