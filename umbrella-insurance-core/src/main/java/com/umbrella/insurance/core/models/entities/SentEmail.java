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
@Table(name = "sent_emails", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "sent_emails_unique", columnNames = {"email_subject", "sent_date_time"})
})
public class SentEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sent_email_id", nullable = false)
    private Long id;

    @Column(name = "content_type", length = Integer.MAX_VALUE)
    private String contentType;

    @NotNull
    @Column(name = "email_subject", nullable = false, length = Integer.MAX_VALUE)
    private String emailSubject;

    @Column(name = "email_body", length = Integer.MAX_VALUE)
    private String emailBody;

    @NotNull
    @Column(name = "recipient_email_address", nullable = false, length = Integer.MAX_VALUE)
    private String recipientEmailAddress;

    @NotNull
    @Column(name = "sender_email_address", nullable = false, length = Integer.MAX_VALUE)
    private String senderEmailAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "sent_date_time", nullable = false)
    private Timestamp sentDateTime;

}