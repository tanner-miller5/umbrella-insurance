package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.DateDeserializer;
import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.serializers.DateSerializer;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "matched_policies", schema = "public")
public class MatchedPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matched_policy_id", nullable = false)
    private Long id;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "created_date_time")
    private Timestamp createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_insured_policy_id")
    private PendingPolicy pendingInsuredPolicy;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_insurer_policy_id")
    private PendingPolicy pendingInsurerPolicy;

    @Column(name = "premium")
    private Double premium;

    @Column(name = "coverage_amount")
    private Double coverageAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "matched_policy_state_id")
    private MatchedPolicyState matchedPolicyState;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "policy_start_date")
    private Date policyStartDate;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "policy_end_date")
    private Date policyEndDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "insured_fee_id")
    private Fee insuredFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "insurer_fee_id")
    private Fee insurerFee;

    @Column(name = "implied_probability")
    private Double impliedProbability;

}