package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "event_types_unique", columnNames = {"event_type_name"})
})
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_type_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "event_type_name", nullable = false, length = Integer.MAX_VALUE)
    private String eventTypeName;

}