package com.umbrella.insurance.core.models.people.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person savePerson(Person person);
    List<Person> getPeople();
    Person updatePerson(Person person);
    void deletePerson(Long personId);
    Optional<Person> getPersonBySsn(String ssn);
    Optional<Person> getPersonById(Long personId);
    void deletePersonBySsn(String ssn);
}
