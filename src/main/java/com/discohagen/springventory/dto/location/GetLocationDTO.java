package com.discohagen.springventory.dto.location;

import lombok.Getter;
import lombok.Setter;

/**
 * The response format for retrieving a location.
 */
@Getter
@Setter
public class GetLocationDTO {
    private Long id;
    private String name;
    private String description;
    private Long parentLocationId;
}
