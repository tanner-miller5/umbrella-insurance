package com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SentText;
import java.util.List;
import java.util.Optional;

public interface SentTextService {
    SentText saveSentText(SentText sentText);
    List<SentText> getSentTexts();
    SentText updateSentText(SentText sentText);
    void deleteSentText(Long sentTextId);
    Optional<SentText> getSentTextById(Long sentTextId);
    Optional<SentText> getSentTextByTextMessage(String textMessage);
    void deleteByTextMessage(String textMessage);

}
