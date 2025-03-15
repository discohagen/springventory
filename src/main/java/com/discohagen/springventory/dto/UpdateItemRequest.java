package com.discohagen.springventory.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * Data Transfer Object for requesting an Update for an Item.
 */
@Getter
@Setter
public class UpdateItemRequest {
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Integer quantity;
    @Nullable
    private Long locationId;
}
