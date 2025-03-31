package com.discohagen.springventory.controller;

import com.discohagen.springventory.dto.location.GetLocationDTO;
import com.discohagen.springventory.dto.location.PutLocationDTO;
import com.discohagen.springventory.dto.location.PostLocationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Controller interface for managing locations. Defines the endpoints for operations on locations.
 */
public interface LocationController {
    /**
     * Creates a location.
     *
     * @param postLocationDTO the request body to create the location from.
     * @return a response with status code.
     */
    ResponseEntity<Void> createLocation(PostLocationDTO postLocationDTO);

    /**
     * Get a location.
     *
     * @param id the id of the location to get.
     * @return a response with status code and the location.
     */
    ResponseEntity<GetLocationDTO> getLocationById(Long id);

    /**
     * Gets all locations.
     *
     * @return a response and a list of all locations.
     */
    ResponseEntity<List<GetLocationDTO>> getAllLocations();

    /**
     * Updates a location.
     *
     * @param id               the id of the location to update.
     * @param putLocationDTO the request body to update the locations by.
     * @return a response with status code and the updated location.
     */
    ResponseEntity<GetLocationDTO> updateLocation(Long id, PutLocationDTO putLocationDTO);

    /**
     * Deletes a location.
     *
     * @param id the id of the location to delete.
     * @return a response with status code.
     */
    ResponseEntity<Void> deleteLocation(Long id, boolean safeDelete);
}
