package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.DateDeserializer;
import com.umbrella.insurance.core.serializers.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "holidays", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "holidays_unique", columnNames = {"holiday_name"})
})
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "holiday_name", nullable = false, length = Integer.MAX_VALUE)
    private String holidayName;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull
    @Column(name = "holiday_date", nullable = false)
    private Date holidayDate;

}