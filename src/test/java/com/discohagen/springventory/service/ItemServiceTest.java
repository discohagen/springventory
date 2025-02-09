package com.discohagen.springventory.service;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.repository.ItemRepository;
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

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveItem() {
        ItemModel item = new ItemModel();
        when(itemRepository.save(item)).thenReturn(item);

        ItemModel savedItem = itemService.saveItem(item);

        assertNotNull(savedItem);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testGetAllItems() {
        ItemModel item1 = new ItemModel();
        ItemModel item2 = new ItemModel();
        List<ItemModel> items = Arrays.asList(item1, item2);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemModel> result = itemService.getAllItems();

        assertEquals(2, result.size());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testGetItemById() {
        Long id = 1L;
        ItemModel item = new ItemModel();
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Optional<ItemModel> result = itemService.getItemById(id);

        assertTrue(result.isPresent());
        assertEquals(item, result.get());
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteItem() {
        Long id = 1L;
        doNothing().when(itemRepository).deleteById(id);

        itemService.deleteItem(id);

        verify(itemRepository, times(1)).deleteById(id);
    }
}
