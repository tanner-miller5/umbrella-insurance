package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tournament_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tournament_types_unique", columnNames = {"tournament_type_name"})
})
public class TournamentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "tournament_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String tournamentTypeName;

}