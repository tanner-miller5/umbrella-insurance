package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "payment_types_unique", columnNames = {"payment_type_name"})
})
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_type_id", nullable = false)
    private Long id;

    @Column(name = "payment_type_name", length = Integer.MAX_VALUE)
    private String paymentTypeName;

}