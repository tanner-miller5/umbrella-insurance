package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "islands", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "islands_un", columnNames = {"island_name"})
})
public class Island {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "island_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "island_name", nullable = false, length = Integer.MAX_VALUE)
    private String islandName;

}