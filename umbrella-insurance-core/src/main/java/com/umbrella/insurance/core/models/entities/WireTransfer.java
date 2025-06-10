package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "wire_transfers", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "wire_transfers_unique", columnNames = {"transfer_id"})
})
public class WireTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wire_transfer_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

}