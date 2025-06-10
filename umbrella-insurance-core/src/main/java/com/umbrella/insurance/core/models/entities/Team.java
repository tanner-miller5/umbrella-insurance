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
@Table(name = "teams", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "teams_un", columnNames = {"team_name", "level_of_competition_id", "game_type_id"})
})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "team_name", nullable = false, length = Integer.MAX_VALUE)
    private String teamName;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "team_type_id")
    private TeamType teamType;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "first_season_id")
    private Season firstSeason;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "last_season_id")
    private Season lastSeason;

    @Column(name = "logo_name", length = Integer.MAX_VALUE)
    private String logoName;

    @Column(name = "logo_image")
    private byte[] logoImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "level_of_competition_id")
    private LevelOfCompetition levelOfCompetition;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

}