package com.discohagen.springventory.controller;

import com.discohagen.springventory.dto.LocationRequest;
import com.discohagen.springventory.dto.UpdateLocationRequest;
import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.model.Location;
import com.discohagen.springventory.service.ItemService;
import com.discohagen.springventory.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller to expose endpoints regarding resource locations.
 */
@RestController
@RequestMapping("api/locations")
public class LocationController {

    private final LocationService locationService;
    private final ItemService itemService;

    /**
     * Constructs a LocationController.
     *
     * @param locationService the service used for location operations.
     * @param itemService     the service used for item operations.
     */
    @Autowired
    public LocationController(LocationService locationService, ItemService itemService) {
        this.locationService = locationService;
        this.itemService = itemService;
    }

    /**
     * Creates a new location.
     *
     * @param locationRequest the request containing location details.
     * @return the uri to the created location and 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Void> createLocation(@RequestBody LocationRequest locationRequest) {
        Location savedLocation = locationService.saveLocation(locationRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLocation.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Retrieves all locations.
     *
     * @return a list of all locations and 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    /**
     * Retrieves a location.
     *
     * @param id the id of the location to retrieve.
     * @return the location and 200 (OK) if exists, otherwise 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return location
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing location.
     *
     * @param id                    the id of the location to update.
     * @param updateLocationRequest the request containing updates for the location.
     * @return the updated location and 200 (OK) if exists, otherwise 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody UpdateLocationRequest updateLocationRequest) {
        try {
            Location savedLocation = locationService.updateLocation(id, updateLocationRequest);
            return new ResponseEntity<>(savedLocation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a location.
     *
     * @param id the id of the location to delete.
     * @return 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves all items in a location.
     *
     * @param locationId the id of the location to retrieve the items from.
     * @return a list of items in the location and 200 (OK) if the location exists,
     * otherwise 404 (Not Found).
     */
    @GetMapping("/{locationId}/items")
    public ResponseEntity<List<Item>> getItemsByLocationId(@PathVariable Long locationId) {
        try {
            List<Item> items = itemService.getItemsByLocationId(locationId);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all child locations inside a parent location.
     * @param parentLocationId the id of the parent location to retrieve the child locations from.
     * @return a list of child locations inside the parent location and 200 (OK) if the parent location exists,
     * otherwise 404 (Not Found).
     */
    @GetMapping("/{parentLocationId}/locations")
    public ResponseEntity<List<Location>> getLocationsByParentLocationId(@PathVariable Long parentLocationId) {
        try {
            List<Location> locations = locationService.getLocationsByParentLocationId(parentLocationId);
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
