package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "continents", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "continents_un", columnNames = {"continent_name"})
})
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "continent_id", nullable = false)
    private Long id;

    @Column(name = "continent_name", length = Integer.MAX_VALUE)
    private String continentName;

}