package com.discohagen.springventory.controller;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.model.LocationModel;
import com.discohagen.springventory.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationModel> createLocation(@RequestBody LocationModel location) {
        LocationModel savedLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationModel>> getAllLocations() {
        List<LocationModel> locations = locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationModel> getLocationById(@PathVariable Long id) {
        Optional<LocationModel> location = locationService.getLocationById(id);
        return location
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemModel>> getItemsInLocation(@PathVariable Long id) {
        List<ItemModel> items = locationService.getItemsInLocation(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationModel> updateLocation(@PathVariable Long id, @RequestBody LocationModel updatedLocation) {
        Optional<LocationModel> existingLocation = locationService.getLocationById(id);
        if (existingLocation.isPresent()) {
            updatedLocation.setId(id);
            LocationModel savedLocation = locationService.saveLocation(updatedLocation);
            return new ResponseEntity<>(savedLocation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
