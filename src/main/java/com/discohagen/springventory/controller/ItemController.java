package com.discohagen.springventory.controller;

import com.discohagen.springventory.dto.ItemRequest;
import com.discohagen.springventory.dto.UpdateItemRequest;
import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller to expose endpoint regarding the resource items.
 */
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * Constructs an ItemController.
     *
     * @param itemService the service used for item operations.
     */
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Creates a new item.
     *
     * @param itemRequest the request containing item details.
     * @return the uri to the created item and 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody ItemRequest itemRequest) {
        Item savedItem = itemService.saveItem(itemRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedItem.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Retrieves all item.
     *
     * @return a list of all items and 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Retrieves an item.
     *
     * @param id the id of the item to retrieve.
     * @return the item if found and 200 (OK), otherwise 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        return item
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing item.
     *
     * @param id                the id of the item to update.
     * @param updateItemRequest the request containing updated item details.
     * @return the updated item and 200 (OK) if exists, otherwise 404 (Not Found).
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody UpdateItemRequest updateItemRequest) {
        try {
            Item savedItem = itemService.updateItem(id, updateItemRequest);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an item.
     *
     * @param id the id of the item to delete.
     * @return 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
