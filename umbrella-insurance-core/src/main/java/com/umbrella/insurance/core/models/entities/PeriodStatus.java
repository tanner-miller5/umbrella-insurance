package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "period_statuses", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "period_statuses_un", columnNames = {"period_status_name"})
})
public class PeriodStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_status_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "period_status_name", nullable = false, length = Integer.MAX_VALUE)
    private String periodStatusName;

}