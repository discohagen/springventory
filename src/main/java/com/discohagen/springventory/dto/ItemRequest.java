package com.discohagen.springventory.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * General Data Transfer Object for requests through the controller for an item in the repo.
 */
@Getter
@Setter
public class ItemRequest {
    private String name;
    @Nullable
    private String description;
    private Integer quantity;
    @Nullable
    private Long locationId;
}
