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
@Table(name = "verification_codes", schema = "public")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_code_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "verification_method_id")
    private VerificationMethod verificationMethod;

    @NotNull
    @Column(name = "verification_code", nullable = false, length = Integer.MAX_VALUE)
    private String verificationCode;

    @NotNull
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "expiration_date_time", nullable = false)
    private Timestamp expirationDateTime;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "verified_date_time")
    private Timestamp verifiedDateTime;

    @NotNull
    @Column(name = "minutes_to_verify", nullable = false)
    private Long minutesToVerify;

    @NotNull
    @Column(name = "max_attempts", nullable = false)
    private Long maxAttempts;

    @NotNull
    @Column(name = "current_attempt", nullable = false)
    private Long currentAttempt;

}