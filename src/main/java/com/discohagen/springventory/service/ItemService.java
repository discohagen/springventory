package com.discohagen.springventory.service;

import com.discohagen.springventory.dto.ItemRequest;
import com.discohagen.springventory.dto.UpdateItemRequest;
import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.model.Location;
import com.discohagen.springventory.repository.ItemRepository;
import com.discohagen.springventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing items.
 * Provides methods to retrieve and manipulate items.
 */
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;

    /**
     * Constructs an ItemService.
     *
     * @param itemRepository     the repository used for item operations.
     * @param locationRepository the repository used for operations regarding locations.
     */
    @Autowired
    public ItemService(ItemRepository itemRepository, LocationRepository locationRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * Saves a new item to the repo.
     *
     * @param itemRequest the request body to save as an item.
     * @return the saved item.
     */
    public Item saveItem(ItemRequest itemRequest) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        if (itemRequest.getDescription() != null) {
            item.setDescription(itemRequest.getDescription());
        }
        item.setQuantity(itemRequest.getQuantity());
        if (itemRequest.getLocationId() != null) {
            Location location = locationRepository.findById(itemRequest.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + itemRequest.getLocationId()));
            item.setLocation(location);
        }
        return itemRepository.save(item);
    }

    /**
     * Retrieves all items from the repo.
     *
     * @return a list of all items.
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Retrieves an item by its id.
     *
     * @param id the id of the item to retrieve.
     * @return the item if exists and else empty.
     */
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * Deletes an Item by its id.
     *
     * @param id the id of the item to delete.
     */
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    /**
     * Updates an existing item.
     *
     * @param id                the id of the item to update
     * @param updateItemRequest the request body containing values to update the item with.
     * @return the updated item.
     * @throws IllegalArgumentException if no item with the id exists or if no location for a given location id exists.
     */
    public Item updateItem(Long id, UpdateItemRequest updateItemRequest) {
        return itemRepository.findById(id)
                .map(item -> {
                    if (updateItemRequest.getName() != null) {
                        item.setName(updateItemRequest.getName());
                    }
                    if (updateItemRequest.getDescription() != null) {
                        item.setDescription(updateItemRequest.getDescription());
                    }
                    if (updateItemRequest.getQuantity() != null) {
                        item.setQuantity(updateItemRequest.getQuantity());
                    }
                    if (updateItemRequest.getLocationId() != null) {
                        Location location = locationRepository.findById(updateItemRequest.getLocationId())
                                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + updateItemRequest.getName()));
                        item.setLocation(location);
                    }
                    return itemRepository.save(item);
                }).orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));
    }
}
