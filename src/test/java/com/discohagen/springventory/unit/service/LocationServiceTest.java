package com.discohagen.springventory.unit.service;

import com.discohagen.springventory.model.LocationModel;
import com.discohagen.springventory.repository.LocationRepository;
import com.discohagen.springventory.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveLocation() {
        LocationModel location = new LocationModel();
        when(locationRepository.save(location)).thenReturn(location);

        LocationModel savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testGetAllLocations() {
        LocationModel location1 = new LocationModel();
        LocationModel location2 = new LocationModel();
        List<LocationModel> locations = Arrays.asList(location1, location2);
        when(locationRepository.findAll()).thenReturn(locations);

        List<LocationModel> result = locationService.getAllLocations();

        assertEquals(2, result.size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void testGetLocationById() {
        Long id = 1L;
        LocationModel location = new LocationModel();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        Optional<LocationModel> result = locationService.getLocationById(id);

        assertTrue(result.isPresent());
        assertEquals(location, result.get());
        verify(locationRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteLocation() {
        Long id = 1L;
        doNothing().when(locationRepository).deleteById(id);

        locationService.deleteLocation(id);

        verify(locationRepository, times(1)).deleteById(id);
    }


}
