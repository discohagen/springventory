package com.discohagen.springventory.repository;

import com.discohagen.springventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByLocationId(Long locationId);
}
