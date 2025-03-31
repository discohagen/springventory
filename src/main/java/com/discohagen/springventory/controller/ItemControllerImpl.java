package com.discohagen.springventory.controller;

import com.discohagen.springventory.dto.item.GetItemDTO;
import com.discohagen.springventory.dto.item.PutItemDTO;
import com.discohagen.springventory.dto.item.PostItemDTO;
import com.discohagen.springventory.service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Implements {@link ItemController}.
 */
@RestController
@RequestMapping("/api/items")
public class ItemControllerImpl implements ItemController {

    private final ItemServiceImpl itemServiceImpl;

    /**
     * Constructs an ItemController.
     *
     * @param itemServiceImpl the service used for item operations.
     */
    @Autowired
    public ItemControllerImpl(ItemServiceImpl itemServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
    }

    /**
     * {@inheritDoc}
     *
     * @param postItemDTO {@inheritDoc}
     * @return 201 (Created) and the uri to the created item.
     */
    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody PostItemDTO postItemDTO) {
        Long id = itemServiceImpl.saveItem(postItemDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * {@inheritDoc}
     *
     * @return 200 (OK) and a list of all item.
     */
    @GetMapping
    public ResponseEntity<List<GetItemDTO>> getAllItems() {
        List<GetItemDTO> getItemDTOList = itemServiceImpl.getAllItems();
        return new ResponseEntity<>(getItemDTOList, HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return 200 (OK) and the item if exists, otherwise 404 (Not found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetItemDTO> getItemById(@PathVariable Long id) {
        Optional<GetItemDTO> getItemDTO = itemServiceImpl.getItemById(id);
        return getItemDTO
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     *
     * @param id         {@inheritDoc}
     * @param putItemDTO {@inheritDoc}
     * @return 200 (OK) and the updated item if exists, otherwise 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetItemDTO> updateItem(@PathVariable Long id, @RequestBody PutItemDTO putItemDTO) {
        try {
            GetItemDTO getItemDTO = itemServiceImpl.updateItem(id, putItemDTO);
            return new ResponseEntity<>(getItemDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemServiceImpl.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
