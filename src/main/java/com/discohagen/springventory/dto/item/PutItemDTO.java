package com.discohagen.springventory.dto.item;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The format of a request to update fields of an item.
 */
@Getter
@Setter
public class PutItemDTO {
    private String name;
    @Nullable
    private String description;
    private Integer quantity;
    @Nullable
    private Long locationId;
    @Nullable
    private Long mainImageId;
    @Nullable
    private List<Long> imageIds;
}
