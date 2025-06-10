package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "companies", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "companies_unique", columnNames = {"company_name"})
})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "company_name", nullable = false, length = Integer.MAX_VALUE)
    private String companyName;

}