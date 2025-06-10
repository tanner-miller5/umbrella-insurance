package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bet_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "bet_types_unique", columnNames = {"bet_type_name"})
})
public class BetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "bet_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String betTypeName;

}