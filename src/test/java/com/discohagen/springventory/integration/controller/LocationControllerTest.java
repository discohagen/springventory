package com.discohagen.springventory.integration.controller;

import com.discohagen.springventory.model.LocationModel;
import com.discohagen.springventory.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    public void setUp() {
        locationRepository.deleteAll();
    }

    @Test
    public void testCreateLocation() {
        LocationModel location = new LocationModel();
        location.setName("Test Location");

        ResponseEntity<LocationModel> response = restTemplate.postForEntity("/api/locations", location, LocationModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Location");
    }

    @Test
    public void testGetAllLocations() {
        LocationModel location1 = new LocationModel();
        location1.setName("Test Location 1");
        locationRepository.save(location1);

        LocationModel location2 = new LocationModel();
        location2.setName("Test Location 2");
        locationRepository.save(location2);

        ResponseEntity<LocationModel[]> response = restTemplate.getForEntity("/api/locations", LocationModel[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    public void testGetLocationById() {
        LocationModel location = new LocationModel();
        location.setName("Test Location");
        location = locationRepository.save(location);

        ResponseEntity<LocationModel> response = restTemplate.getForEntity("/api/locations/" + location.getId(), LocationModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Location");
    }

    @Test
    public void testUpdateLocation() {
        LocationModel location = new LocationModel();
        location.setName("Test Location");
        location = locationRepository.save(location);

        location.setName("Updated Location");
        HttpEntity<LocationModel> requestUpdate = new HttpEntity<>(location);
        ResponseEntity<LocationModel> response = restTemplate.exchange("/api/locations/" + location.getId(), HttpMethod.PUT, requestUpdate, LocationModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Updated Location");
    }

    @Test
    public void testDeleteLocation() {
        LocationModel location = new LocationModel();
        location.setName("Test Location");
        location = locationRepository.save(location);

        ResponseEntity<Void> response = restTemplate.exchange("/api/locations/" + location.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(locationRepository.findById(location.getId())).isEmpty();
    }
}
