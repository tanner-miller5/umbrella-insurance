package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "flyway_schema_history", schema = "public", indexes = {
        @Index(name = "flyway_schema_history_s_idx", columnList = "success")
})
public class FlywaySchemaHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "installed_rank", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "version", length = 50)
    private String version;

    @Size(max = 200)
    @NotNull
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Size(max = 20)
    @NotNull
    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Size(max = 1000)
    @NotNull
    @Column(name = "script", nullable = false, length = 1000)
    private String script;

    @Column(name = "checksum")
    private Integer checksum;

    @Size(max = 100)
    @NotNull
    @Column(name = "installed_by", nullable = false, length = 100)
    private String installedBy;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "installed_on", nullable = false)
    private Timestamp installedOn;

    @NotNull
    @Column(name = "execution_time", nullable = false)
    private Integer executionTime;

    @NotNull
    @Column(name = "success", nullable = false)
    private Boolean success = false;

}