package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "team_types_unique", columnNames = {"team_type_name"})
})
public class TeamType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "team_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String teamTypeName;

}