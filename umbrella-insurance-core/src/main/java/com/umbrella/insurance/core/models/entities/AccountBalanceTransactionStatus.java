package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account_balance_transaction_statuses", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "account_balance_transaction_statuses_unique", columnNames = {"account_balance_transaction_status_name"})
})
public class AccountBalanceTransactionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_balance_transaction_status_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "account_balance_transaction_status_name", nullable = false, length = Integer.MAX_VALUE)
    private String accountBalanceTransactionStatusName;

}