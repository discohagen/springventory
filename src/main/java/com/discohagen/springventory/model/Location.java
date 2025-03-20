package com.discohagen.springventory.model;

import com.discohagen.springventory.dto.location.GetLocationDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents an abstract location.
 * Generally speaking the location should be a building, a room or a container.
 * Any given location can have a parent location and multiple child locations,
 * like any one room can be inside a building and be housing to multiple containers.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent_location_id")
    @JsonBackReference
    private Location parentLocation;

    @OneToMany(mappedBy = "parentLocation")
    @JsonManagedReference
    private List<Location> childLocations;

    @OneToMany(mappedBy = "location")
    @JsonManagedReference
    private List<Item> items;

    /**
     * map a location to its exposeable format.
     *
     * @return {@link GetLocationDTO}
     */
    public GetLocationDTO toGetLocationDTO() {
        GetLocationDTO dto = new GetLocationDTO();

        dto.setId(this.getId());
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        if (this.getParentLocation() != null) {
            dto.setParentLocationId(this.getParentLocation().getId());
        }

        return dto;
    }
}
