package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pending_bets", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "pending_bets_unique", columnNames = {"pending_bet_name"})
})
public class PendingBet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_bet_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "game_id")
    private Game game;

    @NotNull
    @Column(name = "minimum_odds", nullable = false)
    private Double minimumOdds;

    @NotNull
    @Column(name = "maximum_wager_amount", nullable = false)
    private Double maximumWagerAmount;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "bot_id")
    private Bot bot;

    @Column(name = "is_bot_bet")
    private Boolean isBotBet;

    @Column(name = "is_charity_bet")
    private Boolean isCharityBet;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "pending_bet_state_id")
    private PendingBetState pendingBetState;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @NotNull
    @Column(name = "pending_bet_name", nullable = false, length = Integer.MAX_VALUE)
    private String pendingBetName;

    @OneToMany(mappedBy = "originalPendingBet", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    //@JoinColumn(name = "split_pending_bet_id_1")
    private List<PendingBet> splitPendingBetId1;

    @OneToMany(mappedBy = "originalPendingBet", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    //@JoinColumn(name = "split_pending_bet_id_2")
    private List<PendingBet> splitPendingBetId2;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "original_pending_bet_id")
    @JsonIgnore
    private PendingBet originalPendingBet;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "account_balance_escrow_transaction_id")
    private AccountBalanceTransaction accountBalanceEscrowTransaction;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "account_balance_canceled_escrow_transaction_id")
    private AccountBalanceTransaction accountBalanceCanceledEscrowTransaction;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "canceled_date_time")
    private Timestamp canceledDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "bet_type_id")
    private BetType betType;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "order_type_id")
    private OrderType orderType;

}