package com.discohagen.springventory.controller;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void testCreateItem() throws Exception {
        ItemModel item = new ItemModel(1L, "Test item", "A test item", 5);

        when(itemRepository.save(item)).thenReturn(item);

        mockMvc.perform(post("/api/items"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test item"))
                .andExpect(jsonPath("$.description").value("A test item"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void testGetAllItems() throws Exception {
        ItemModel item = new ItemModel(1L, "Test item", "A test item", 12);

        when(itemRepository.findAll()).thenReturn(List.of(item));

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test item"))
                .andExpect(jsonPath("$[0].description").value("A test item"))
                .andExpect(jsonPath("$[0].quantity").value(12));
    }
}
