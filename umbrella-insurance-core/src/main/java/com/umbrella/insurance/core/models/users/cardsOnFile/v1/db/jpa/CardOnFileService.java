package com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.CardOnFile;

public interface CardOnFileService {
    CardOnFile saveCardOnFile(CardOnFile cardOnFile);
    List<CardOnFile> getCardOnFiles();
    CardOnFile updateCardOnFile(CardOnFile cardOnFile);
    void deleteCardOnFile(Long cardOnFileId);
    Optional<CardOnFile> getCardOnFileByCardNumber(String cardNumber);
    void deleteCardOnFileByCardNumber(String cardNumber);
    Optional<CardOnFile> getCardOnFileByCardOnFileId(Long cardOnFileId);
}
