package com.discohagen.springventory.repository;

import com.discohagen.springventory.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface between {@link com.discohagen.springventory.service.LocationService} and {@link Location}. Reads and manipulates data on the database.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByParentLocationId(Long parentLocationId);
}
