package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_statuses", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "game_statuses_unique", columnNames = {"game_status_name"})
})
public class GameStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_status_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "game_status_name", nullable = false, length = Integer.MAX_VALUE)
    private String gameStatusName;

}