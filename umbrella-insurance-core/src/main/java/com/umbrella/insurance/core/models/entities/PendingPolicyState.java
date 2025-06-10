package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pending_policy_states", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "pending_policy_states_unique", columnNames = {"pending_policy_state_name"})
})
public class PendingPolicyState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_policy_state_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "pending_policy_state_name", nullable = false, length = Integer.MAX_VALUE)
    private String pendingPolicyStateName;

}