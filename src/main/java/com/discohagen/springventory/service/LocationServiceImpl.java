package com.discohagen.springventory.service;

import com.discohagen.springventory.dto.location.*;
import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.model.Location;
import com.discohagen.springventory.repository.ItemRepository;
import com.discohagen.springventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements {@link LocationService}.
 */
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final ItemRepository itemRepository;

    /**
     * Constructs the location service.
     *
     * @param locationRepository the repository to use for location operations.
     * @param itemRepository     the repository to use for item operations.
     */
    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ItemRepository itemRepository) {
        this.locationRepository = locationRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param postLocationDTO {@inheritDoc}
     * @return {@inheritDoc}
     */
    public Long saveLocation(PostLocationDTO postLocationDTO) {
        Location parentLocation = null;
        if (postLocationDTO.getParentLocationId() != null) {
            parentLocation = locationRepository.findById(postLocationDTO.getParentLocationId()).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + postLocationDTO.getParentLocationId()));
        }
        Location location = postLocationDTO.toLocation(parentLocation);
        Location savedLocation = locationRepository.save(location);
        return savedLocation.getId();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public List<GetLocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(Location::toGetLocationDTO).toList();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    public Optional<GetLocationDTO> getLocationById(Long id) {
        return locationRepository.findById(id).map(Location::toGetLocationDTO);
    }


    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     */
    public void deleteLocation(Long id, boolean isSafeDeleteAndNotCascading) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));

        if (isSafeDeleteAndNotCascading) {
            List<Item> items = itemRepository.findByLocationId(location.getId());
            for (Item item : items) {
                item.setLocation(null);
                itemRepository.save(item);
            }

            List<Location> childLocations = locationRepository.findByParentLocationId(location.getId());
            for (Location childLocation : childLocations) {
                childLocation.setParentLocation(null);
                locationRepository.save(childLocation);
            }

        }
        locationRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param id             {@inheritDoc}
     * @param putLocationDTO {@inheritDoc}
     * @return {@inheritDoc}
     */
    public GetLocationDTO updateLocation(Long id, PutLocationDTO putLocationDTO) {
        return locationRepository.findById(id).map(location -> {
            location.setName(putLocationDTO.getName());
            location.setDescription(putLocationDTO.getDescription());
            if (putLocationDTO.getParentLocationId() != null) {
                Location parentLocation = locationRepository.findById(putLocationDTO.getParentLocationId()).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + putLocationDTO.getParentLocationId()));
                location.setParentLocation(parentLocation);
            } else {
                location.setParentLocation(null);
            }
            return locationRepository.save(location).toGetLocationDTO();
        }).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));
    }

}
