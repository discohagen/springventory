package com.discohagen.springventory.repository;

import com.discohagen.springventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface between {@link com.discohagen.springventory.service.ItemService} and {@link Item}. Reads and manipulates data on the database.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByLocationId(Long locationId);
}
