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
@Table(name = "sent_texts", schema = "public")
public class SentText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sent_text_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "recipient_phone_number", nullable = false, length = Integer.MAX_VALUE)
    private String recipientPhoneNumber;

    @NotNull
    @Column(name = "sender_phone_number", nullable = false, length = Integer.MAX_VALUE)
    private String senderPhoneNumber;

    @NotNull
    @Column(name = "text_message", nullable = false, length = Integer.MAX_VALUE)
    private String textMessage;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "sent_date_time", nullable = false)
    private Timestamp sentDateTime;

}