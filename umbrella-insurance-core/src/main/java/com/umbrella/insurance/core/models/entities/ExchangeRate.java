package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "exchange_rates", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "exchange_rates_unique", columnNames = {"unit_id_1", "unit_id_2"})
})
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_rate_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id_1")
    private Unit unit1;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id_2")
    private Unit unit2;

    @NotNull
    @Column(name = "unit_1_to_unit_2_ratio", nullable = false)
    private Double unit1ToUnit2Ratio;

}