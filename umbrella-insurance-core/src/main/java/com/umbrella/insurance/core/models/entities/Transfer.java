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
@Table(name = "transfers", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "transfers_unique", columnNames = {"transfer_name"})
})
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_voided")
    private Boolean isVoided;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "voided_date_time")
    private Timestamp voidedDateTime;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "posted_date_time")
    private Timestamp postedDateTime;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "voided_event_id")
    private Event voidedEvent;

    @NotNull
    @Column(name = "transfer_name", nullable = false, length = Integer.MAX_VALUE)
    private String transferName;

}