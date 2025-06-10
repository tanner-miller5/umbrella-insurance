package com.umbrella.insurance.core.models.items.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByItemName(String itemName);
    void deleteItemByItemName(String itemName);
}
