package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "specialties", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "specialties_unique", columnNames = {"specialty_name"})
})
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "specialty_name", nullable = false, length = Integer.MAX_VALUE)
    private String specialtyName;

}