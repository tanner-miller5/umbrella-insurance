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
@Table(name = "tournaments", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tournaments_unique", columnNames = {"tournament_name"})
})
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "tournament_name", nullable = false, length = Integer.MAX_VALUE)
    private String tournamentName;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "tournament_type_id")
    private TournamentType tournamentType;

}