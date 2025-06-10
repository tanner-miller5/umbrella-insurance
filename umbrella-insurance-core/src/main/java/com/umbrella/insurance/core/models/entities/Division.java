package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "divisions", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "divisions_unique", columnNames = {"division_name"})
})
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @NotNull
    @Column(name = "division_name", nullable = false, length = Integer.MAX_VALUE)
    private String divisionName;

}