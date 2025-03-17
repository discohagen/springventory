package com.discohagen.springventory.dto.location;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * The request format for updating fields of a location.
 */
@Getter
@Setter
public class PatchLocationDTO {
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Long parentLocationId;
}
