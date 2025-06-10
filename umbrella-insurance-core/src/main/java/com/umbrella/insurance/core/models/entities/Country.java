package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "countries", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "countries_un", columnNames = {"country_name"})
})
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Long id;

    @Column(name = "country_name", length = Integer.MAX_VALUE)
    private String countryName;

}