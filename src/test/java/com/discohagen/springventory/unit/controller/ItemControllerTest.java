package com.discohagen.springventory.unit.controller;

import com.discohagen.springventory.controller.ItemController;
import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.service.ItemService;
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

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateItem() throws Exception {
        ItemModel item = new ItemModel();
        item.setId(1L);
        item.setName("Test Item");

        when(itemService.saveItem(any(ItemModel.class))).thenReturn(item);

        mockMvc.perform(post("/api/items").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Test Item\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    public void testGetAllItems() throws Exception {
        ItemModel item1 = new ItemModel();
        item1.setId(1L);
        item1.setName("Test Item 1");

        ItemModel item2 = new ItemModel();
        item2.setId(2L);
        item2.setName("Test Item 2");

        when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Item 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Test Item 2"));
    }

    @Test
    public void testGetItemById() throws Exception {
        ItemModel item = new ItemModel();
        item.setId(1L);
        item.setName("Test Item");

        when(itemService.getItemById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    public void testUpdateItem() throws Exception {
        ItemModel item = new ItemModel();
        item.setId(1L);
        item.setName("Updated Item");

        when(itemService.getItemById(anyLong())).thenReturn(Optional.of(item));
        when(itemService.saveItem(any(ItemModel.class))).thenReturn(item);

        mockMvc.perform(put("/api/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Item\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Item"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isNoContent());
    }
}
