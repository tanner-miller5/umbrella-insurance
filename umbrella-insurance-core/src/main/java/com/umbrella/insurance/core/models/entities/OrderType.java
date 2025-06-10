package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "order_types_unique", columnNames = {"order_type_name"})
})
public class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "order_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String orderTypeName;

}