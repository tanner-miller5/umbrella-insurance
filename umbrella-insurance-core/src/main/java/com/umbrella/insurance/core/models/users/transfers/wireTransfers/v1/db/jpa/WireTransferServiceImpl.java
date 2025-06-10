package com.umbrella.insurance.core.models.users.transfers.wireTransfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.WireTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WireTransferServiceImpl implements WireTransferService {

    @Autowired
    WireTransferRepository wireTransferRepository;

    @Override
    public WireTransfer saveWireTransfer(WireTransfer wireTransfer) {
        return wireTransferRepository.save(wireTransfer);
    }

    @Override
    public List<WireTransfer> getWireTransfers() {
        return wireTransferRepository.findAll();
    }

    @Override
    public WireTransfer updateWireTransfer(WireTransfer wireTransfer) {
        return wireTransferRepository.save(wireTransfer);
    }

    @Override
    public void deleteWireTransfer(Long wireTransferId) {
        wireTransferRepository.deleteById(wireTransferId);
    }

    @Override
    public Optional<WireTransfer> getWireTransferByTransferId(Long wireTransferId) {
        return wireTransferRepository.findWireTransferByTransferId(wireTransferId);
    }

    @Override
    public Optional<WireTransfer> getWireTransferByWireTransferId(Long wireTransferId) {
        return wireTransferRepository.findById(wireTransferId);
    }

    @Override
    public void deleteWireTransferByTransferId(Long transferId) {
        wireTransferRepository.deleteWireTransferByTransferId(transferId);
    }
}