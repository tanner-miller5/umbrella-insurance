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
@Table(name = "app_versions", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "app_versions_unique", columnNames = {"app_name", "app_version"})
})
public class AppVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_version_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "app_name", nullable = false, length = Integer.MAX_VALUE)
    private String appName;

    @NotNull
    @Column(name = "app_version", nullable = false, length = Integer.MAX_VALUE)
    private String appVersion;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "session_id")
    private Session session;

}