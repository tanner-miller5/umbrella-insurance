package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "level_of_competitions", schema = "public")
public class LevelOfCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_of_competition_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "level_of_competition_name", nullable = false, length = Integer.MAX_VALUE)
    private String levelOfCompetitionName;

}