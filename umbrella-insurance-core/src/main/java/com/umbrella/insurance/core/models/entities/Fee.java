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
@Table(name = "fees", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "fees_unique", columnNames = {"fee_name"})
})
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id", nullable = false)
    private Long id;

    @Column(name = "fee_percent")
    private Double feePercent;

    @Column(name = "fixed_fee")
    private Double fixedFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @NotNull
    @Column(name = "fee_name", nullable = false, length = Integer.MAX_VALUE)
    private String feeName;

}