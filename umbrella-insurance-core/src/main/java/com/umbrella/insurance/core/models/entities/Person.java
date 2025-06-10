package com.umbrella.insurance.core.models.entities;

import com.umbrella.insurance.core.deserializers.DateDeserializer;
import com.umbrella.insurance.core.serializers.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "people", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "people_ssn_unique", columnNames = {"ssn"})
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "middle_name", length = Integer.MAX_VALUE)
    private String middleName;

    @NotNull
    @Column(name = "surname", nullable = false, length = Integer.MAX_VALUE)
    private String surname;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "ssn", length = Integer.MAX_VALUE)
    private String ssn;

}