package com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.CardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardTransferRepository extends JpaRepository<CardTransfer, Long> {
    Optional<CardTransfer> findCardTransferByTransferId(Long transferId);
    void deleteCardTransferByTransferId(Long transferId);
}
