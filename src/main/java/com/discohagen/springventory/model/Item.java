package com.discohagen.springventory.model;

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
    private Location location;
}
