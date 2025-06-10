package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "referrals", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "referrals_unique", columnNames = {"referral_name"})
})
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referral_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "referral_name", nullable = false, length = Integer.MAX_VALUE)
    private String referralName;

}