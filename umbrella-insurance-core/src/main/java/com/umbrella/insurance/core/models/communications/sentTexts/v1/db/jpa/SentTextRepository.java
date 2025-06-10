package com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SentText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SentTextRepository extends JpaRepository<SentText, Long> {
    Optional<SentText> getSentTextById(Long id);
    Optional<SentText> getSentTextByTextMessage(String textMessage);
    void deleteByTextMessage(String textMessage);
}
