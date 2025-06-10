package com.umbrella.insurance.core.models.users.transfers.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferRepository transferRepository;

    @Override
    public Transfer saveTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getTransfers() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer updateTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void deleteTransfer(Long transferId) {
        transferRepository.deleteById(transferId);
    }

    @Override
    public Optional<Transfer> getTransferByTransferName(String transferName) {
        return transferRepository.findByTransferName(transferName);
    }

    @Override
    public Optional<Transfer> getTransferByTransferId(Long transferId) {
        return transferRepository.findById(transferId);
    }

    @Override
    public void deleteCardTransferByTransferName(String transferName) {
        transferRepository.deleteCardTransferByTransferName(transferName);
    }
}