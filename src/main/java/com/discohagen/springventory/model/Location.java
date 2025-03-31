package com.discohagen.springventory.model;

import com.discohagen.springventory.dto.location.GetLocationDTO;
import com.discohagen.springventory.dto.location.LocationSummaryDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @Nullable
    private String description;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent_location_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
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
        Location parentLocation = this.getParentLocation();
        if (parentLocation != null) {
            LocationSummaryDTO parentLocationSummary = new LocationSummaryDTO();
            parentLocationSummary.setId(parentLocation.getId());
            parentLocationSummary.setName(parentLocation.getName());
            dto.setParentLocationSummary(parentLocationSummary);
        }

        return dto;
    }
}
