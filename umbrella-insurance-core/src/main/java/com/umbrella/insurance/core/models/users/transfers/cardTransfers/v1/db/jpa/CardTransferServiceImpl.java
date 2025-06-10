package com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.CardTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardTransferServiceImpl implements CardTransferService {

    @Autowired
    CardTransferRepository cardTransferRepository;

    @Override
    public CardTransfer saveCardTransfer(CardTransfer cardTransfer) {
        return cardTransferRepository.save(cardTransfer);
    }

    @Override
    public List<CardTransfer> getCardTransfers() {
        return cardTransferRepository.findAll();
    }

    @Override
    public CardTransfer updateCardTransfer(CardTransfer cardTransfer) {
        return cardTransferRepository.save(cardTransfer);
    }

    @Override
    public void deleteCardTransfer(Long cardTransferId) {
        cardTransferRepository.deleteById(cardTransferId);
    }

    @Override
    public Optional<CardTransfer> getCardTransferByTransferId(Long transferId) {
        return cardTransferRepository.findCardTransferByTransferId(transferId);
    }

    @Override
    public Optional<CardTransfer> getCardTransferByCardTransferId(Long cardTransferId) {
        return cardTransferRepository.findById(cardTransferId);
    }

    @Override
    public void deleteCardTransferByTransferId(Long transferId) {
        cardTransferRepository.deleteCardTransferByTransferId(transferId);
    }
}