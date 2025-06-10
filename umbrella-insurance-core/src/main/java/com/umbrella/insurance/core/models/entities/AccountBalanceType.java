package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account_balance_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "account_balance_types_unique", columnNames = {"account_balance_type_name"})
})
public class AccountBalanceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_balance_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "account_balance_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String accountBalanceTypeName;

}