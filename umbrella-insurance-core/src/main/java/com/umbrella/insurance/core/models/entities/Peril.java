package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perils", schema = "public")
public class Peril {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "peril_id", nullable = false)
    private Long id;

    @Column(name = "peril_name", length = Integer.MAX_VALUE)
    private String perilName;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "scale_name", length = Integer.MAX_VALUE)
    private String scaleName;

    @NotNull
    @Column(name = "min_magnitude", nullable = false)
    private Double minMagnitude;

    @NotNull
    @Column(name = "max_magnitude", nullable = false)
    private Double maxMagnitude;

}