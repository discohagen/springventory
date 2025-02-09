package com.discohagen.springventory.service;

import com.discohagen.springventory.model.LocationModel;
import com.discohagen.springventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationModel saveLocation(LocationModel location) {
        return locationRepository.save(location);
    }

    public List<LocationModel> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<LocationModel> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
