package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "application_role_application_privilege_relationships", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "application_role_application_privilege_relationships_unique", columnNames = {"application_role_id", "application_privilege_id"})
})
public class ApplicationRoleApplicationPrivilegeRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_role_application_privilege_relationship_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "application_role_id")
    private ApplicationRole applicationRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "application_privilege_id")
    private ApplicationPrivilege applicationPrivilege;

}