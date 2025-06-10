package com.umbrella.insurance.core.models.items.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item saveItem(Item item);
    List<Item> getItems();
    Item updateItem(Item item);
    void deleteItem(Long itemId);
    Optional<Item> getItemById(Long itemId);
    Optional<Item> getItemByItemName(String itemName);
    void deleteItemByItemName(String itemName);
}
