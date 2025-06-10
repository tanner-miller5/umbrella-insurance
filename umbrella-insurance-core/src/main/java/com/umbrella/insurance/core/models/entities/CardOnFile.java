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

@Getter
@Setter
@Entity
@Table(name = "cards_on_file", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "cards_on_file_unique", columnNames = {"card_number"})
})
public class CardOnFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_on_file_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "card_number", nullable = false, length = Integer.MAX_VALUE)
    private String cardNumber;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @NotNull
    @Column(name = "cvv", nullable = false, length = Integer.MAX_VALUE)
    private String cvv;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "phone_number", nullable = false, length = Integer.MAX_VALUE)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "location_id")
    private Location location;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "deleted_date_time")
    private Timestamp deletedDateTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}