package com.umbrella.insurance.core.models.users.v4.db.jpa;

import com.umbrella.insurance.core.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    List<User> getUsers();
    User updateUser(User user);
    void deleteUser(Long userId);
    Optional<User> getUserByPersonId(Long personId);
    Optional<User> getUserByUserId(Long userId);
    Optional<User> getUserByEmailAddress(String emailAddress);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByPhoneNumber(String phoneNumber);
    void deleteUserByPersonId(Long personId);
}
