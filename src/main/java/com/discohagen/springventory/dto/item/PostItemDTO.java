package com.discohagen.springventory.dto.item;

import com.discohagen.springventory.model.Image;
import com.discohagen.springventory.model.Item;
import com.discohagen.springventory.model.Location;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The format of a request to create an item.
 */
@Getter
@Setter
public class PostItemDTO {
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

    /**
     * map a post request to the item model of the database.
     *
     * @param location the location the item should be in if exists.
     * @return {@link Item}
     */
    public Item toItem(Location location, Image mainImage, List<Image> images) {
        Item item = new Item();

        item.setName(this.getName());
        item.setDescription(this.getDescription());
        item.setQuantity(this.getQuantity());
        item.setLocation(location);
        item.setMainImage(mainImage);
        item.setImages(images);

        return item;
    }
}
