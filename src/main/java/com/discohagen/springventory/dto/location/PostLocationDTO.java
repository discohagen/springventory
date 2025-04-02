package com.discohagen.springventory.dto.location;

import com.discohagen.springventory.model.Image;
import com.discohagen.springventory.model.Location;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The request format for creating a location.
 */
@Getter
@Setter
public class PostLocationDTO {
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Long parentLocationId;
    @Nullable
    private Long mainImageId;
    @Nullable
    private List<Long> imageIds;

    /**
     * map the request to the location model of the database.
     *
     * @param parentLocation the parentLocation the location should have if exists.
     * @return {@link Location}
     */
    public Location toLocation(Location parentLocation, Image mainImage, List<Image> images) {
        Location location = new Location();

        location.setName(this.getName());
        location.setDescription(this.getDescription());
        location.setParentLocation(parentLocation);
        location.setMainImage(mainImage);
        location.setImages(images);

        return location;
    }
}
