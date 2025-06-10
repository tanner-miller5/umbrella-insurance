package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pending_policy_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "pending_policy_type_name_unique", columnNames = {"pending_policy_type_name"})
})
public class PendingPolicyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_policy_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "pending_policy_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String pendingPolicyTypeName;

}