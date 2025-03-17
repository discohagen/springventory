package com.discohagen.springventory.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * General Data Transfer Object for requests through the controller for a location in the repo.
 */
@Getter
@Setter
public class LocationRequest {
    private Long id;
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Long parentLocationId;
}
