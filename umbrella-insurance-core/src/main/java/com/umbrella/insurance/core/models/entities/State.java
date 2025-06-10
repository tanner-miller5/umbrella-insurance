package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "states", schema = "public")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "state_name", nullable = false, length = Integer.MAX_VALUE)
    private String stateName;

}