package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "units", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "units_un", columnNames = {"unit_name"})
})
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "unit_name", nullable = false, length = Integer.MAX_VALUE)
    private String unitName;

}