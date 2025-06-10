package com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SentText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SentTextServiceImpl implements SentTextService {
    @Autowired
    SentTextRepository sentTextRepository;

    @Override
    public SentText saveSentText(SentText sentText) {
        return sentTextRepository.save(sentText);
    }

    @Override
    public List<SentText> getSentTexts() {
        return sentTextRepository.findAll();
    }

    @Override
    public SentText updateSentText(SentText sentText) {
        return sentTextRepository.save(sentText);
    }

    @Override
    public void deleteSentText(Long sentTextId) {
        sentTextRepository.deleteById(sentTextId);
    }

    @Override
    public Optional<SentText> getSentTextById(Long sentTextId) {
        return sentTextRepository.getSentTextById(sentTextId);
    }

    @Override
    public Optional<SentText> getSentTextByTextMessage(String textMessage) {
        return sentTextRepository.getSentTextByTextMessage(textMessage);
    }

    @Override
    public void deleteByTextMessage(String textMessage) {
        sentTextRepository.deleteByTextMessage(textMessage);
    }
}
