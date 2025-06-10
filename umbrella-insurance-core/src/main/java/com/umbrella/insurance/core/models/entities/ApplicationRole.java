package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "application_roles", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "application_roles_unique", columnNames = {"application_role_name"})
})
public class ApplicationRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_role_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "application_role_name", nullable = false, length = Integer.MAX_VALUE)
    private String applicationRoleName;

}