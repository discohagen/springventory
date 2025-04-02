package com.discohagen.springventory.dto.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The response format for retrieving a location.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLocationDTO {
    private Long id;
    private String name;
    @Nullable
    private String description;
    @Nullable
    private LocationSummaryDTO parentLocationSummary;
    @Nullable
    private Long mainImageId;
    @Nullable
    private List<Long> imageIds;
}
