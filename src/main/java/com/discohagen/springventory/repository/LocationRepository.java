package com.discohagen.springventory.repository;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationModel, Long> {

    @Query("select i from ItemModel i where i.location.id = :locationId")
    List<ItemModel> findItemsByLocationId(Long locationId);
}
