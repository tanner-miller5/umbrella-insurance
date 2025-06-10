package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "conferences", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "conferences_unique", columnNames = {"conference_name"})
})
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "league_id")
    private League league;

    @Column(name = "conference_name", length = Integer.MAX_VALUE)
    private String conferenceName;

}