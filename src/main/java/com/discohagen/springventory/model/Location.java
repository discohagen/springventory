package com.discohagen.springventory.model;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "parent_location_id")
    private Location parentLocation;

    @OneToMany(mappedBy = "parentLocation")
    private List<Location> childLocations;

    @OneToMany(mappedBy = "location")
    private List<Item> items;

}
