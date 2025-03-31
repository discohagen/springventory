package com.discohagen.springventory.controller;

import com.discohagen.springventory.dto.item.GetItemDTO;
import com.discohagen.springventory.dto.item.PutItemDTO;
import com.discohagen.springventory.dto.item.PostItemDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Controller interface for managing items.
 * Defines the endpoints for operations on items.
 */
public interface ItemController {
    /**
     * Creates an item.
     *
     * @param postItemDTO the request body to create the item from.
     * @return a response with status code.
     */
    ResponseEntity<Void> createItem(PostItemDTO postItemDTO);

    /**
     * Gets an item.
     *
     * @param id the id of the item to get.
     * @return a response with status code and the item.
     */
    ResponseEntity<GetItemDTO> getItemById(Long id);

    /**
     * Gets all items.
     *
     * @return a response with status code and a list of all items.
     */
    ResponseEntity<List<GetItemDTO>> getAllItems();

    /**
     * Updates an item.
     *
     * @param id           the id of the item to update.
     * @param putItemDTO the request body to update the item by.
     * @return a response with status code and the updated item.
     */
    ResponseEntity<GetItemDTO> updateItem(Long id, PutItemDTO putItemDTO);

    /**
     * Deletes an item.
     *
     * @param id the id of the item to delete.
     * @return a response with status code.
     */
    ResponseEntity<Void> deleteItem(Long id);
}
