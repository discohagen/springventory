package com.discohagen.springventory.service;

import com.discohagen.springventory.dto.item.GetItemDTO;
import com.discohagen.springventory.dto.item.PutItemDTO;
import com.discohagen.springventory.dto.item.PostItemDTO;
import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.model.Location;
import com.discohagen.springventory.repository.ItemRepository;
import com.discohagen.springventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements the {@link ItemService}.
 */
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;

    /**
     * Constructs the Item service.
     *
     * @param itemRepository     the repository to use for item operations.
     * @param locationRepository the repository to use for location operations.
     */
    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, LocationRepository locationRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param postItemDTO {@inheritDoc}
     * @return {@inheritDoc}
     */
    public Long saveItem(PostItemDTO postItemDTO) {
        Location location = null;
        if (postItemDTO.getLocationId() != null) {
            location = locationRepository.findById(postItemDTO.getLocationId()).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + postItemDTO.getLocationId()));
        }
        Item item = postItemDTO.toItem(location);
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public List<GetItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(Item::toGetItemDTO).toList();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    public Optional<GetItemDTO> getItemById(Long id) {
        return itemRepository.findById(id).map(Item::toGetItemDTO);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     */
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param id         {@inheritDoc}
     * @param putItemDTO {@inheritDoc}
     * @return {@inheritDoc}
     */
    public GetItemDTO updateItem(Long id, PutItemDTO putItemDTO) {
        return itemRepository.findById(id).map(item -> {
            item.setName(putItemDTO.getName());
            item.setDescription(putItemDTO.getDescription());
            item.setQuantity(putItemDTO.getQuantity());
            if (putItemDTO.getLocationId() != null) {
                Location location = locationRepository.findById(putItemDTO.getLocationId()).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + putItemDTO.getLocationId()));
                item.setLocation(location);
            } else {
                item.setLocation(null);
            }
            return itemRepository.save(item).toGetItemDTO();
        }).orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));
    }

    /**
     * {@inheritDoc}
     *
     * @param locationId {@inheritDoc}
     * @return {@inheritDoc}
     */
    public List<GetItemDTO> getItemsByLocationId(Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));
        List<Item> items = itemRepository.findByLocationId(location.getId());
        return items.stream().map(Item::toGetItemDTO).toList();
    }
}
