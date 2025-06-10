package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.DateDeserializer;
import com.umbrella.insurance.core.serializers.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "weeks", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "weeks_unique", columnNames = {"week_title"})
})
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_id", nullable = false)
    private Long id;

    @Column(name = "week_number")
    private Long weekNumber;

    @Column(name = "week_title", length = Integer.MAX_VALUE)
    private String weekTitle;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "week_start_date")
    private Date weekStartDate;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "week_end_date")
    private Date weekEndDate;

}