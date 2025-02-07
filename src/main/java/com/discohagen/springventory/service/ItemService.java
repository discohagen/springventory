package com.discohagen.springventory.service;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemModel saveItem(ItemModel item) {
        return itemRepository.save(item);
    }

    public List<ItemModel> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<ItemModel> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
