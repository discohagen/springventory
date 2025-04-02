package com.discohagen.springventory.model;

import com.discohagen.springventory.dto.item.GetItemDTO;
import com.discohagen.springventory.dto.location.LocationSummaryDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Item from the view of the database. An Item can be anything but should be a physical object not being a location but able to be stored in a location.
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

    @Nullable
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Location location;

    @Nullable
    @OneToOne
    @JoinColumn(name = "main_image_id")
    private Image mainImage;

    @Nullable
    @ManyToMany
    @JoinTable(
            name = "item_images",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images = new ArrayList<>();

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
            LocationSummaryDTO locationSummaryDTO = new LocationSummaryDTO();
            locationSummaryDTO.setId(location.getId());
            locationSummaryDTO.setName(location.getName());
            getItemDTO.setLocationSummary(locationSummaryDTO);
        }

        return getItemDTO;
    }
}
