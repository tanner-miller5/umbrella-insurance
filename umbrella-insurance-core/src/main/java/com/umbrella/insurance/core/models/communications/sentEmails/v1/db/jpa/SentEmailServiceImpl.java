package com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SentEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SentEmailServiceImpl implements SentEmailService {

    @Autowired
    SentEmailRepository sentEmailRepository;

    @Override
    public SentEmail saveSentEmail(SentEmail sentEmail) {
        return sentEmailRepository.save(sentEmail);
    }

    @Override
    public List<SentEmail> getSentEmails() {
        return sentEmailRepository.findAll();
    }

    @Override
    public SentEmail updateSentEmail(SentEmail sentEmail) {
        return sentEmailRepository.save(sentEmail);
    }

    @Override
    public void deleteSentEmail(Long sentEmailId) {
        sentEmailRepository.deleteById(sentEmailId);
    }

    @Override
    public Optional<SentEmail> getSentEmailById(Long sentEmailId) {
        return sentEmailRepository.getSentEmailById(sentEmailId);
    }

    @Override
    public Optional<SentEmail> getSentEmailByEmailSubject(String emailSubject) {
        return sentEmailRepository.getSentEmailByEmailSubject(emailSubject);
    }

    @Override
    public void deleteSentEmailByEmailSubject(String emailSubject) {
        sentEmailRepository.deleteSentEmailByEmailSubject(emailSubject);
    }
}
