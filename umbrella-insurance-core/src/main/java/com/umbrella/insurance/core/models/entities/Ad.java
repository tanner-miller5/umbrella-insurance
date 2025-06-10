package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@Entity
@Table(name = "ads", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "ads_unique", columnNames = {"ad_name"})
})
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ad_name", nullable = false, length = Integer.MAX_VALUE)
    private String adName;

    @NotNull
    @Column(name = "ad_data", nullable = false, length = Integer.MAX_VALUE)
    private String adData;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

}