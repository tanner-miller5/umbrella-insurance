package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "leagues", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "leagues_unique", columnNames = {"league_name", "game_type_id"})
})
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "league_name", nullable = false, length = Integer.MAX_VALUE)
    private String leagueName;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

}