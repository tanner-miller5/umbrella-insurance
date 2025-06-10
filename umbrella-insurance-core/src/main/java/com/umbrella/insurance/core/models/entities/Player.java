package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "players", schema = "public")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

    @Column(name = "height", length = Integer.MAX_VALUE)
    private String height;

    @Column(name = "weight", length = Integer.MAX_VALUE)
    private String weight;

    @Column(name = "college", length = Integer.MAX_VALUE)
    private String college;

    @Column(name = "draft_info", length = Integer.MAX_VALUE)
    private String draftInfo;

    @Column(name = "player_status", length = Integer.MAX_VALUE)
    private String playerStatus;

    @Column(name = "jersey_number", length = Integer.MAX_VALUE)
    private String jerseyNumber;

    @Column(name = "player_position", length = Integer.MAX_VALUE)
    private String playerPosition;

    @Column(name = "experience", length = Integer.MAX_VALUE)
    private String experience;

    @Column(name = "birthplace", length = Integer.MAX_VALUE)
    private String birthplace;

}