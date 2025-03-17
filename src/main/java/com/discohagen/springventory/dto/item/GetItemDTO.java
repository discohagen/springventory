package com.discohagen.springventory.dto.item;

import lombok.Getter;
import lombok.Setter;

/**
 * The exposable format of an item to retrieve.
 */
@Getter
@Setter
public class GetItemDTO {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Long locationId;
}
