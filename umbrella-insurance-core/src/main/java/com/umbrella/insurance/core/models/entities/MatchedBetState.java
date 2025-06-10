package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matched_bet_states", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "matched_bet_states_unique", columnNames = {"matched_bet_state_name"})
})
public class MatchedBetState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matched_bet_state_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "matched_bet_state_name", nullable = false, length = Integer.MAX_VALUE)
    private String matchedBetStateName;

}