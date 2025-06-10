package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "groups", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "groups_unique", columnNames = {"group_name"})
})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "group_name", nullable = false, length = Integer.MAX_VALUE)
    private String groupName;

}