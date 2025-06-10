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
@Table(name = "reviews", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "review_unique", columnNames = {"user_id", "frontend_app_version", "backend_app_version"})
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @NotNull
    @Column(name = "subject", nullable = false, length = Integer.MAX_VALUE)
    private String subject;

    @NotNull
    @Column(name = "comment", nullable = false, length = Integer.MAX_VALUE)
    private String comment;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "session_id")
    private Session session;

    @NotNull
    @Column(name = "frontend_app_version", nullable = false, length = Integer.MAX_VALUE)
    private String frontendAppVersion;

    @NotNull
    @Column(name = "backend_app_version", nullable = false, length = Integer.MAX_VALUE)
    private String backendAppVersion;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

}