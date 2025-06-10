package com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.SentEmail;

import java.util.Optional;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
    Optional<SentEmail> getSentEmailById(Long sentEmailId);
    Optional<SentEmail> getSentEmailByEmailSubject(String emailSubject);
    void deleteSentEmailByEmailSubject(String emailSubject);
}
