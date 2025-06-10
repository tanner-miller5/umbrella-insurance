package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "devices", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "devices_unique", columnNames = {"device_name"})
})
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ip_address", nullable = false, length = Integer.MAX_VALUE)
    private String ipAddress;

    @NotNull
    @Column(name = "user_agent", nullable = false, length = Integer.MAX_VALUE)
    private String userAgent;

    @NotNull
    @Column(name = "device_name", nullable = false, length = Integer.MAX_VALUE)
    private String deviceName;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private Timestamp createdDateTime;

}