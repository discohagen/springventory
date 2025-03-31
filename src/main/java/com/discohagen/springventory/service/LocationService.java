package com.discohagen.springventory.service;

import com.discohagen.springventory.dto.location.GetLocationDTO;
import com.discohagen.springventory.dto.location.PutLocationDTO;
import com.discohagen.springventory.dto.location.PostLocationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing items. Interface between {@link com.discohagen.springventory.controller.LocationController}
 * and {@link com.discohagen.springventory.repository.LocationRepository}. Implements business logic including CRUD-operations.
 */
public interface LocationService {
    /**
     * Creates a location.
     *
     * @param postLocationDTO the request body to create the location from.
     * @return the id the location can be found by.
     */
    Long saveLocation(PostLocationDTO postLocationDTO);

    // TODO: pagination and entry limit for read methods
    // TODO: sorting for read methods
    // TODO: depth for nested locations and options for including items
    // TODO: locations between a set min and max depth and option for including items

    /**
     * Get a location.
     *
     * @param id the id of the location to get.
     * @return the location if exists, otherwise empty.
     */
    Optional<GetLocationDTO> getLocationById(Long id);

    /**
     * Gets all locations.
     *
     * @return a list of all locations.
     */
    List<GetLocationDTO> getAllLocations();

    // TODO: get locations in a parent location

    /**
     * Updates a location.
     *
     * @param id               the id of the location to update.
     * @param putLocationDTO the request body to take information from to update the location.
     * @return the updated location.
     */
    GetLocationDTO updateLocation(Long id, PutLocationDTO putLocationDTO);

    /**
     * Deletes a location.
     *
     * @param id the id of the location to delete.
     */
    void deleteLocation(Long id, boolean isSafeDeleteAndNotCascading);

}
