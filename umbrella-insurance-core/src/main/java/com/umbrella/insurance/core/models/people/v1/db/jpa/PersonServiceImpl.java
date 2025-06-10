package com.umbrella.insurance.core.models.people.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonRepository personRepository;

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);
    }

    @Override
    public Optional<Person> getPersonBySsn(String ssn) {
        return personRepository.findBySsn(ssn);
    }

    @Override
    public Optional<Person> getPersonById(Long personId) {
        return personRepository.findById(personId);
    }

    @Override
    public void deletePersonBySsn(String ssn) {
        personRepository.deleteBySsn(ssn);
    }
}
