package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charities", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "charities_unique", columnNames = {"charity_name"})
})
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charity_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "charity_name", nullable = false, length = Integer.MAX_VALUE)
    private String charityName;

}