package com.discohagen.springventory.dto.item;

import com.discohagen.springventory.dto.location.LocationSummaryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * The exposable format of an item to retrieve.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetItemDTO {
    private Long id;
    private String name;
    @Nullable
    private String description;
    private Integer quantity;
    @Nullable
    private LocationSummaryDTO locationSummary;
}
