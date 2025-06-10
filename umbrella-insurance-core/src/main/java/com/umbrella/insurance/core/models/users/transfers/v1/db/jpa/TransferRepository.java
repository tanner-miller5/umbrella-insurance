package com.umbrella.insurance.core.models.users.transfers.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Transfer;

import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findByTransferName(String transferName);
    void deleteCardTransferByTransferName(String transferName);
}
