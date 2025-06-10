package com.umbrella.insurance.core.models.users.v4.db.jpa;

import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPersonId(Long personId);
    Optional<User> findByEmailAddress(String emailAddress);
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> person(Person person);
    void deleteByPersonId(Long personId);
}
