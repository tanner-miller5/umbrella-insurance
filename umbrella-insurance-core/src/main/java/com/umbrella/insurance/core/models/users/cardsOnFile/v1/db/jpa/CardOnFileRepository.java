package com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.umbrella.insurance.core.models.entities.CardOnFile;

import java.util.Optional;

@Repository
public interface CardOnFileRepository extends JpaRepository<CardOnFile, Long> {
    Optional<CardOnFile> findCardOnFileByCardNumber(String cardNumber);
    void deleteCardOnFileByCardNumber(String cardNumber);
}
