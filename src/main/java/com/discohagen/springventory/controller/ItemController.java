package com.discohagen.springventory.controller;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemModel> createItem(@RequestBody ItemModel item) {
        ItemModel savedItem = itemService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemModel>> getAllItems() {
        List<ItemModel> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemModel> getItemById(@PathVariable Long id) {
        Optional<ItemModel> item = itemService.getItemById(id);
        return item
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemModel> updateItem(@PathVariable Long id, @RequestBody ItemModel updatedItem) {
        Optional<ItemModel> existingItem = itemService.getItemById(id);
        if (existingItem.isPresent()) {
            updatedItem.setId(id);
            ItemModel savedItem = itemService.saveItem(updatedItem);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
