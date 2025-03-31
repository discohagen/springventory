package com.discohagen.springventory.service;

import com.discohagen.springventory.dto.item.GetItemDTO;
import com.discohagen.springventory.dto.item.PutItemDTO;
import com.discohagen.springventory.dto.item.PostItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface between {@link com.discohagen.springventory.repository.ItemRepository} and {@link com.discohagen.springventory.controller.ItemController}.
 * Reads and manipulates data in the repository. Implements business logic inclusive simple CRUD-operations.
 */
public interface ItemService {

    /**
     * Creates an item in the repository.
     *
     * @param postItemDTO data to create the item from.
     * @return the id the item can be found by.
     */
    Long saveItem(PostItemDTO postItemDTO);

    // TODO: pagination and entry limit for read methods
    // TODO: sorting for read methods

    /**
     * Gets an item.
     *
     * @param id the id of the item to get.
     * @return the item in format {@link GetItemDTO} if exists, otherwise empty.
     */
    Optional<GetItemDTO> getItemById(Long id);

    /**
     * Gets all items.
     *
     * @return a list of all items in single format {@link GetItemDTO}.
     */
    List<GetItemDTO> getAllItems();

    /**
     * Gets all items in a location.
     *
     * @param locationId the id of the location to get the items from.
     * @return a list of all items in the location in single format {@link GetItemDTO}.
     */
    List<GetItemDTO> getItemsByLocationId(Long locationId);

    /**
     * Updates an item.
     *
     * @param id           the id of the item to update.
     * @param putItemDTO the request body containing information about which fields of the item to update which what values.
     * @return the item in format {@link GetItemDTO}.
     */
    GetItemDTO updateItem(Long id, PutItemDTO putItemDTO);

    /**
     * Deletes an item.
     *
     * @param id the id of the item to delete.
     */
    void deleteItem(Long id);

    // TODO: delete items in a location for cascading delete
}
