package com.umbrella.insurance.core.models.users.transfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Transfer;

import java.util.List;
import java.util.Optional;

public interface TransferService {
    Transfer saveTransfer(Transfer transfer);
    List<Transfer> getTransfers();
    Transfer updateTransfer(Transfer transfer);
    void deleteTransfer(Long transferId);
    Optional<Transfer> getTransferByTransferName(String transferName);
    Optional<Transfer> getTransferByTransferId(Long transferId);
    void deleteCardTransferByTransferName(String transferName);
}
