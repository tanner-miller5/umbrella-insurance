package com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.WireTransfer;

import java.util.List;
import java.util.Optional;

public interface WireTransferService {
    WireTransfer saveWireTransfer(WireTransfer wireTransfer);
    List<WireTransfer> getWireTransfers();
    WireTransfer updateWireTransfer(WireTransfer wireTransfer);
    void deleteWireTransfer(Long wireTransferId);
    Optional<WireTransfer> getWireTransferByTransferId(Long wireTransferId);
    Optional<WireTransfer> getWireTransferByWireTransferId(Long wireTransferId);
    void deleteWireTransferByTransferId(Long transferId);
}
