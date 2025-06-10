package com.umbrella.insurance.core.models.people.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findBySsn(String ssn);
    void deleteBySsn(String ssn);
}
