package com.umbrella.insurance.core.models.items.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public Optional<Item> getItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public Optional<Item> getItemByItemName(String itemName) {
        return itemRepository.findItemByItemName(itemName);
    }

    @Override
    public void deleteItemByItemName(String itemName) {
        itemRepository.deleteItemByItemName(itemName);
    }
}
