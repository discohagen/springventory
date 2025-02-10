package com.discohagen.springventory.integration.controller;

import com.discohagen.springventory.model.ItemModel;
import com.discohagen.springventory.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    public void testCreateItem() {
        ItemModel item = new ItemModel();
        item.setName("Test Item");

        ResponseEntity<ItemModel> response = restTemplate.postForEntity("/api/items", item, ItemModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Item");
    }

    @Test
    public void testGetAllItems() {
        ItemModel item1 = new ItemModel();
        item1.setName("Test Item 1");
        itemRepository.save(item1);

        ItemModel item2 = new ItemModel();
        item2.setName("Test Item 2");
        itemRepository.save(item2);

        ResponseEntity<ItemModel[]> response = restTemplate.getForEntity("/api/items", ItemModel[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    public void testGetItemById() {
        ItemModel item = new ItemModel();
        item.setName("Test Item");
        item = itemRepository.save(item);

        ResponseEntity<ItemModel> response = restTemplate.getForEntity("/api/items/" + item.getId(), ItemModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Item");
    }

    @Test
    public void testUpdateItem() {
        ItemModel item = new ItemModel();
        item.setName("Test item");
        item = itemRepository.save(item);

        item.setName("Updated item");
        HttpEntity<ItemModel> requestUpdate = new HttpEntity<>(item);
        ResponseEntity<ItemModel> response = restTemplate.exchange("/api/items/" + item.getId(), HttpMethod.PUT, requestUpdate, ItemModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Updated item");
    }

    @Test
    public void testDeleteItem() {
        ItemModel item = new ItemModel();
        item.setName("Test item");
        item = itemRepository.save(item);

        ResponseEntity<Void> response = restTemplate.exchange("/api/items/" + item.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(itemRepository.findById(item.getId())).isEmpty();
    }
}
