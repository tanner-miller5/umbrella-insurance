package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "items", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "items_unique", columnNames = {"item_name"})
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "item_name", nullable = false, length = Integer.MAX_VALUE)
    private String itemName;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "item_image")
    private byte[] itemImage;

    @NotNull
    @Column(name = "upc", nullable = false, length = Integer.MAX_VALUE)
    private String upc;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "unit_id")
    private Unit unit;

}