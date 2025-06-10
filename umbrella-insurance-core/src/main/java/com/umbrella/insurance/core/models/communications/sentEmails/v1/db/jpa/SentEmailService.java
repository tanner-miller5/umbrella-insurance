package com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SentEmail;

import java.util.List;
import java.util.Optional;

public interface SentEmailService {
    SentEmail saveSentEmail(SentEmail sentEmail);
    List<SentEmail> getSentEmails();
    SentEmail updateSentEmail(SentEmail sentEmail);
    void deleteSentEmail(Long sentEmailId);
    Optional<SentEmail> getSentEmailById(Long sentEmailId);
    Optional<SentEmail> getSentEmailByEmailSubject(String emailSubject);
    void deleteSentEmailByEmailSubject(String emailSubject);
}
