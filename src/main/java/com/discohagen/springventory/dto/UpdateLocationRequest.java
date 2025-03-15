package com.discohagen.springventory.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * DTO for requesting an update for a location.
 */
@Getter
@Setter
public class UpdateLocationRequest {
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Long parentLocationId;
}
