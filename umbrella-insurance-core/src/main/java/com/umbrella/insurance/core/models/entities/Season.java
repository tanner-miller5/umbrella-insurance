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
@Table(name = "seasons", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "seasons_unique", columnNames = {"season_name"})
})
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "season_name", nullable = false, length = Integer.MAX_VALUE)
    private String seasonName;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Date endDate;

}