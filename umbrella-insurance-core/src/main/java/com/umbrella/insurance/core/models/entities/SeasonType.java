package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "season_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "season_types_unique", columnNames = {"season_type_name"})
})
public class SeasonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "season_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String seasonTypeName;

}