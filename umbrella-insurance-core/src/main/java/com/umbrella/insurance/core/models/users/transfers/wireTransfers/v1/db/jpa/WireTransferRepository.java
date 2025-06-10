package com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.WireTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WireTransferRepository extends JpaRepository<WireTransfer, Long> {
    Optional<WireTransfer> findWireTransferByTransferId(Long transferId);
    void deleteWireTransferByTransferId(Long transferId);
}
