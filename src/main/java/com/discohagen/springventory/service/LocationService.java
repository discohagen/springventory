package com.discohagen.springventory.service;

import com.discohagen.springventory.dto.LocationRequest;
import com.discohagen.springventory.dto.UpdateLocationRequest;
import com.discohagen.springventory.model.Location;
import com.discohagen.springventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing locations. Provides methods to retrieve and manipulate locations.
 */
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    /**
     * Constructs a LocationService.
     *
     * @param locationRepository the repo used for location operations.
     */
    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Saves a new location.
     *
     * @param locationRequest the request to save as a location.
     * @return the saved location.
     */
    public Location saveLocation(LocationRequest locationRequest) {
        Location location = new Location();
        location.setName(locationRequest.getName());
        if (locationRequest.getDescription() != null) {
            location.setDescription(locationRequest.getDescription());
        }
        if (locationRequest.getParentLocationId() != null) {
            Location parentLocation = locationRepository.findById(locationRequest.getParentLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationRequest.getParentLocationId()));
            location.setParentLocation(parentLocation);
        }
        return locationRepository.save(location);
    }

    /**
     * Retrieves all locations.
     *
     * @return a list of all locations.
     */
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    /**
     * Retrieves a location by its id.
     *
     * @param id the id of the location to retrieve.
     * @return the location if exists and else empty.
     */
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    /**
     * Retrieves all locations inside a location.
     *
     * @param parentLocationId the parent location to retrieve all child locations from.
     * @return a list of all locations inside the parent location.
     * @throws IllegalArgumentException if paren location does not exist.
     */
    public List<Location> getLocationsByParentLocationId(Long parentLocationId) {
        Location parentLocation = locationRepository.findById(parentLocationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + parentLocationId));
        return locationRepository.findByParentLocationId(parentLocation.getId());
    }

    /**
     * Deletes a location.
     *
     * @param id the id of the location to delete.
     */
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    /**
     * Updates an existing location.
     *
     * @param id                    the id of the location to update.
     * @param updateLocationRequest the request containing values to update the location with.
     * @return the updated location.
     * @throws IllegalArgumentException if no location with the id exists or if no location for a given parentLocation exists.
     */
    public Location updateLocation(Long id, UpdateLocationRequest updateLocationRequest) {
        return locationRepository.findById(id)
                .map(location -> {
                    if (updateLocationRequest.getName() != null) {
                        location.setName(updateLocationRequest.getName());
                    }
                    if (updateLocationRequest.getDescription() != null) {
                        location.setDescription(updateLocationRequest.getDescription());
                    }
                    if (updateLocationRequest.getParentLocationId() != null) {
                        Location parentLocation = locationRepository.findById(updateLocationRequest.getParentLocationId())
                                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + updateLocationRequest.getParentLocationId()));
                        location.setParentLocation(parentLocation);
                    }
                    return locationRepository.save(location);
                }).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));
    }

}
