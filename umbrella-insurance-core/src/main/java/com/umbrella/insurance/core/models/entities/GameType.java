package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "game_types_game_name_unique", columnNames = {"game_type_name"})
})
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "game_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String gameTypeName;

}