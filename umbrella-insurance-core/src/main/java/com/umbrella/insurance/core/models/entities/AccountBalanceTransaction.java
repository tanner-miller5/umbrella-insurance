package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "account_balance_transactions", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "account_balance_transactions_unique", columnNames = {"account_balance_transaction_name"})
})
public class AccountBalanceTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_balance_transaction_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "account_balance_transaction_name", nullable = false, length = Integer.MAX_VALUE)
    private String accountBalanceTransactionName;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "account_balance_transaction_type_id")
    private AccountBalanceTransactionType accountBalanceTransactionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "account_balance_transaction_status_id")
    private AccountBalanceTransactionStatus accountBalanceTransactionStatus;

}