package com.discohagen.springventory.model;

import com.discohagen.springventory.dto.item.GetItemDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an Item from the view of the database. An Item can be anything but should be a physical object not being a location but storeable in a location.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Location location;

    /**
     * map an item model to the exposing format of the item.
     *
     * @return {@link GetItemDTO}
     */
    public GetItemDTO toGetItemDTO() {
        GetItemDTO getItemDTO = new GetItemDTO();

        getItemDTO.setId(this.getId());
        getItemDTO.setName(this.getName());
        getItemDTO.setDescription(this.getDescription());
        getItemDTO.setQuantity(this.getQuantity());
        Location location = this.getLocation();
        if (location != null) {
            getItemDTO.setLocationId(this.getLocation().getId());
        }

        return getItemDTO;
    }
}
