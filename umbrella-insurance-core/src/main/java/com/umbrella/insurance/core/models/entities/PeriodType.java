package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "period_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "period_types_unique", columnNames = {"period_type_name"})
})
public class PeriodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "period_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String periodTypeName;

}