package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "zip_codes", schema = "public")
public class ZipCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zip_code_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "zip_code_value", nullable = false, length = Integer.MAX_VALUE)
    private String zipCodeValue;

}