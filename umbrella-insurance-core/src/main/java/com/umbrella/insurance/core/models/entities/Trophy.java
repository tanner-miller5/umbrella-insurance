package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trophies", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "trophies_unique", columnNames = {"trophy_name"})
})
public class Trophy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trophy_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "trophy_name", nullable = false, length = Integer.MAX_VALUE)
    private String trophyName;

}