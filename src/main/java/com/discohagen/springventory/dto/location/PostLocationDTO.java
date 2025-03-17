package com.discohagen.springventory.dto.location;

import com.discohagen.springventory.model.Location;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * The request format for creating a location.
 */
@Getter
@Setter
public class PostLocationDTO {
    private String name;
    private String description;
    @Nullable
    private Long parentLocationId;

    /**
     * map the request to the location model of the database.
     *
     * @param parentLocation the parentLocation the location should have if exists.
     * @return {@link Location}
     */
    public Location toLocation(Location parentLocation) {
        Location location = new Location();

        location.setName(this.getName());
        location.setDescription(this.getDescription());
        location.setParentLocation(parentLocation);

        return location;
    }
}
