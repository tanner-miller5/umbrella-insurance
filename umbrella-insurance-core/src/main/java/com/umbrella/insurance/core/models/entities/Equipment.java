package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipment", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "equipment_unique", columnNames = {"equipment_name"})
})
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id", nullable = false)
    private Long id;

    @Column(name = "equipment_name", length = Integer.MAX_VALUE)
    private String equipmentName;

}