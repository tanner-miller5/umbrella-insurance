package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "analysts", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "analysts_unique", columnNames = {"person_id", "specialty_id"})
})
public class Analyst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analyst_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "person_id")
    private Person person;

}