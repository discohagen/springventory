package com.discohagen.springventory.unit.controller;

import com.discohagen.springventory.controller.LocationController;
import com.discohagen.springventory.model.LocationModel;
import com.discohagen.springventory.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLocation() throws Exception {
        LocationModel location = new LocationModel();
        location.setId(1L);
        location.setName("Test Location");

        when(locationService.saveLocation(any(LocationModel.class))).thenReturn(location);

        mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Location\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Location"));

    }

    @Test
    public void testGetAllLocations() throws Exception {
        LocationModel location1 = new LocationModel();
        location1.setId(1L);
        location1.setName("Test Location 1");

        LocationModel location2 = new LocationModel();
        location2.setId(2L);
        location2.setName("Test Location 2");

        when(locationService.getAllLocations()).thenReturn(Arrays.asList(location1, location2));

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Location 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Test Location 2"));
    }

    @Test
    public void testGetLocationById() throws Exception {
        LocationModel location = new LocationModel();
        location.setId(1L);
        location.setName("Test Location");

        when(locationService.getLocationById(1L)).thenReturn(Optional.of(location));

        mockMvc.perform(get("/api/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Location"));
    }

    @Test
    public void testUpdateLocation() throws Exception {
        LocationModel location = new LocationModel();
        location.setId(1L);
        location.setName("Updated Location");

        when(locationService.getLocationById(anyLong())).thenReturn(Optional.of(location));
        when(locationService.saveLocation(any(LocationModel.class))).thenReturn(location);

        mockMvc.perform(put("/api/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Location\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Location"));
    }

    @Test
    public void testDeleteLocation() throws Exception {
        mockMvc.perform(delete("/api/locations/1"))
                .andExpect(status().isNoContent());
    }
}
