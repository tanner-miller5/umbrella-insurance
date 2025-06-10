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
@Table(name = "matched_bets", schema = "public")
public class MatchedBet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matched_bet_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_bet_id_1")
    private PendingBet pendingBet1;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_bet_id_2")
    private PendingBet pendingBet2;

    @NotNull
    @Column(name = "odds_1", nullable = false)
    private Double odds1;

    @NotNull
    @Column(name = "odds_2", nullable = false)
    private Double odds2;

    @NotNull
    @Column(name = "wager_amount_1", nullable = false)
    private Double wagerAmount1;

    @NotNull
    @Column(name = "wager_amount_2", nullable = false)
    private Double wagerAmount2;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "fee_id_1")
    private Fee feeId1;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "fee_id_2")
    private Fee feeId2;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "matched_bet_state_id")
    private MatchedBetState matchedBetState;

}