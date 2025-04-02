package com.discohagen.springventory.dto.location;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The request format for updating fields of a location.
 */
@Getter
@Setter
public class PutLocationDTO {
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Long parentLocationId;
    @Nullable
    private Long mainImageId;
    @Nullable
    private List<Long> imageIds;
}
