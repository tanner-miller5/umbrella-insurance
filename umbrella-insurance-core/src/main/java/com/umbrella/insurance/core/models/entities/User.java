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
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "user_v4_unique", columnNames = {"person_id"}),
        @UniqueConstraint(name = "profiles_email_address_unique", columnNames = {"email_address"}),
        @UniqueConstraint(name = "profiles_phone_number_unique", columnNames = {"phone_number"}),
        @UniqueConstraint(name = "profiles_username_unique", columnNames = {"username"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @Column(name = "email_address", nullable = false, length = Integer.MAX_VALUE)
    private String emailAddress;

    @NotNull
    @Column(name = "phone_number", nullable = false, length = Integer.MAX_VALUE)
    private String phoneNumber;

    @NotNull
    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    private String username;

    @Column(name = "is_email_address_verified")
    private Boolean isEmailAddressVerified;

    @Column(name = "is_phone_number_verified")
    private Boolean isPhoneNumberVerified;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "verification_method_id")
    private VerificationMethod verificationMethod;

    @Column(name = "is_auth_app_verified")
    private Boolean isAuthAppVerified;

}