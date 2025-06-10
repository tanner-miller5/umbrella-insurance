package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "verification_methods", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "verification_method_unique", columnNames = {"verification_method_name"})
})
public class VerificationMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_method_id", nullable = false)
    private Long id;

    @Column(name = "verification_method_name", length = Integer.MAX_VALUE)
    private String verificationMethodName;

}