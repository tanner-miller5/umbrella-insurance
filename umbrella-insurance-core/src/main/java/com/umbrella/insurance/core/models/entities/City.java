package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cities", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "cities_un", columnNames = {"city_name"})
})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Long id;

    @Column(name = "city_name", length = Integer.MAX_VALUE)
    private String cityName;

}