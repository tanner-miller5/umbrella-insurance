package com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.CardTransfer;

import java.util.List;
import java.util.Optional;

public interface CardTransferService {
    CardTransfer saveCardTransfer(CardTransfer cardTransfer);
    List<CardTransfer> getCardTransfers();
    CardTransfer updateCardTransfer(CardTransfer cardTransfer);
    void deleteCardTransfer(Long cardTransferId);
    Optional<CardTransfer> getCardTransferByTransferId(Long transferId);
    Optional<CardTransfer> getCardTransferByCardTransferId(Long cardTransferId);
    void deleteCardTransferByTransferId(Long transferId);
}
