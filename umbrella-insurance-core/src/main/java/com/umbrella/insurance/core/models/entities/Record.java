package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "records", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "records_unique", columnNames = {"record_name"})
})
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "wins", nullable = false)
    private Long wins;

    @NotNull
    @Column(name = "ties", nullable = false)
    private Long ties;

    @NotNull
    @Column(name = "losses", nullable = false)
    private Long losses;

    @NotNull
    @Column(name = "record_name", nullable = false, length = Integer.MAX_VALUE)
    private String recordName;

}