package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "promotions", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "promotions_unique", columnNames = {"promotion_name"})
})
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "promotion_name", nullable = false, length = Integer.MAX_VALUE)
    private String promotionName;

}