package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stats", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "stats_name_unique", columnNames = {"stat_name"})
})
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "stat_name", nullable = false, length = Integer.MAX_VALUE)
    private String statName;

}