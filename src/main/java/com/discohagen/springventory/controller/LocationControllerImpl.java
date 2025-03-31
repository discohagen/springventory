package com.discohagen.springventory.controller;

import com.discohagen.springventory.dto.location.*;
import com.discohagen.springventory.service.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Implements {@link LocationController}.
 */
@RestController
@RequestMapping("api/locations")
public class LocationControllerImpl implements LocationController {

    private final LocationServiceImpl locationServiceImpl;

    /**
     * Constructs a location service.
     *
     * @param locationServiceImpl the service used for location operations.
     */
    @Autowired
    public LocationControllerImpl(LocationServiceImpl locationServiceImpl) {
        this.locationServiceImpl = locationServiceImpl;
    }

    /**
     * {@inheritDoc}
     *
     * @param postLocationDTO {@inheritDoc}
     * @return 201 (Created) and the uri to the created location.
     */
    @PostMapping
    public ResponseEntity<Void> createLocation(@RequestBody PostLocationDTO postLocationDTO) {
        Long id = locationServiceImpl.saveLocation(postLocationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * {@inheritDoc}
     *
     * @return 200 (OK) and a list of all locations.
     */
    @GetMapping
    public ResponseEntity<List<GetLocationDTO>> getAllLocations() {
        List<GetLocationDTO> getLocationDTOList = locationServiceImpl.getAllLocations();
        return new ResponseEntity<>(getLocationDTOList, HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return 200 (OK) and the item if exists, otherwise 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetLocationDTO> getLocationById(@PathVariable Long id) {
        Optional<GetLocationDTO> getLocationDTO = locationServiceImpl.getLocationById(id);
        return getLocationDTO
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     *
     * @param id             {@inheritDoc}
     * @param putLocationDTO {@inheritDoc}
     * @return 200 (OK) and the updated location if exists, otherwise 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetLocationDTO> updateLocation(@PathVariable Long id, @RequestBody PutLocationDTO putLocationDTO) {
        try {
            GetLocationDTO getLocationDTO = locationServiceImpl.updateLocation(id, putLocationDTO);
            return new ResponseEntity<>(getLocationDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id,
                                               @RequestParam(defaultValue = "true") boolean safeDelete) {
        locationServiceImpl.deleteLocation(id, safeDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
