package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.DateDeserializer;
import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.serializers.DateSerializer;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pending_policies", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "pending_policies_unique", columnNames = {"pending_policy_name"})
})
public class PendingPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_policy_id", nullable = false)
    private Long id;

    @Column(name = "premium_amount")
    private Double premiumAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "coverage_amount")
    private Double coverageAmount;

    @Column(name = "implied_probability")
    private Double impliedProbability;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "peril_id")
    private Peril peril;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_policy_state_id")
    private PendingPolicyState pendingPolicyState;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @NotNull
    @Column(name = "pending_policy_name", nullable = false, length = Integer.MAX_VALUE)
    private String pendingPolicyName;

    @OneToMany(mappedBy = "originalPendingPolicy", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    //@JoinColumn(name = "split_pending_policy_id_1")
    private List<PendingPolicy> splitPendingPolicy1;

    @OneToMany(mappedBy = "originalPendingPolicy", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    //@JoinColumn(name = "split_pending_policy_id_2")
    private List<PendingPolicy> splitPendingPolicy2;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "original_pending_policy_id")
    private PendingPolicy originalPendingPolicy;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "account_balance_escrow_transaction_id")
    private AccountBalanceTransaction accountBalanceEscrowTransaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "account_balance_canceled_escrow_transaction_id")
    private AccountBalanceTransaction accountBalanceCanceledEscrowTransaction;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "canceled_date_time")
    private Timestamp canceledDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_policy_type_id")
    private PendingPolicyType pendingPolicyType;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "order_type_id")
    private OrderType orderType;

}