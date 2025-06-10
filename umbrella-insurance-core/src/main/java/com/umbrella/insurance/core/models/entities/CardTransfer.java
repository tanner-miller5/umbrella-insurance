package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "card_transfers", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "card_transfers_unique", columnNames = {"transfer_id"})
})
public class CardTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_transfer_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "card_on_file_id")
    private CardOnFile cardOnFile;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

}