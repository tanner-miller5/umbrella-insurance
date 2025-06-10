package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "application_privileges", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "application_privileges_unique", columnNames = {"application_privilege_name"})
})
public class ApplicationPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_privilege_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "application_privilege_name", nullable = false, length = Integer.MAX_VALUE)
    private String applicationPrivilegeName;

    @NotNull
    @Column(name = "path", nullable = false, length = Integer.MAX_VALUE)
    private String path;

    @NotNull
    @Column(name = "method", nullable = false, length = Integer.MAX_VALUE)
    private String method;

    @NotNull
    @Column(name = "access", nullable = false, length = Integer.MAX_VALUE)
    private String access;

}