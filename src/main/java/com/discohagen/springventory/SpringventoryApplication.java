package com.discohagen.springventory;

import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.model.Location;
import com.discohagen.springventory.repository.ItemRepository;
import com.discohagen.springventory.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringventoryApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ItemRepository itemRepository, LocationRepository locationRepository) {
        return (args) -> {
            Location warehouse = new Location(null, "Warehouse", "Main warehouse", null, null, null, null, null);
            Location store = new Location(null, "Store", "A retail store", null, null, null, null, null);
            locationRepository.save(warehouse);
            locationRepository.save(store);

            Location sectionA = new Location(null, "Section A", "Section A of the warehouse", warehouse, null, null, null, null);
            locationRepository.save(sectionA);

            Item smartphone = new Item(null, "Smartphone", "A smart phone", 10, warehouse, null, null);
            Item mouse = new Item(null, "Mouse", "A computer mouse", 55, warehouse, null, null);
            Item keyboard = new Item(null, "Keyboard", "A computer keyboard", 13, warehouse, null, null);
            Item desktop = new Item(null, "Desktop", "A desktop pc", 20, store, null, null);
            Item cable = new Item(null, "Cable", "A power cable", 44, store, null, null);
            Item laptop = new Item(null, "Laptop", "A Laptop", 30, sectionA, null, null);
            Item powerBrick = new Item(null, "Power Brick", "A power brick", 12, sectionA, null, null);
            itemRepository.save(smartphone);
            itemRepository.save(mouse);
            itemRepository.save(keyboard);
            itemRepository.save(desktop);
            itemRepository.save(cable);
            itemRepository.save(laptop);
            itemRepository.save(powerBrick);
        };
    }

}
