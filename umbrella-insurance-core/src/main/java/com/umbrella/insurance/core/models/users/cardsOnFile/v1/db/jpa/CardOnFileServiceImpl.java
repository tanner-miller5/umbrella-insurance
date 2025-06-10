package com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.CardOnFile;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardOnFileServiceImpl implements CardOnFileService {
    @Autowired
    CardOnFileRepository cardOnFileRepository;

    @Override
    public CardOnFile saveCardOnFile(CardOnFile cardOnFile) {
        return cardOnFileRepository.save(cardOnFile);
    }

    @Override
    public List<CardOnFile> getCardOnFiles() {
        return cardOnFileRepository.findAll();
    }

    @Override
    public CardOnFile updateCardOnFile(CardOnFile cardOnFile) {
        return cardOnFileRepository.save(cardOnFile);
    }

    @Override
    public void deleteCardOnFile(Long cardOnFileId) {
        cardOnFileRepository.deleteById(cardOnFileId);
    }

    @Override
    public Optional<CardOnFile> getCardOnFileByCardNumber(String cardNumber) {
        return cardOnFileRepository.findCardOnFileByCardNumber(cardNumber);
    }

    @Override
    public void deleteCardOnFileByCardNumber(String cardNumber) {
        cardOnFileRepository.deleteCardOnFileByCardNumber(cardNumber);
    }

    @Override
    public Optional<CardOnFile> getCardOnFileByCardOnFileId(Long cardOnFileId) {
        return cardOnFileRepository.findById(cardOnFileId);
    }
}
